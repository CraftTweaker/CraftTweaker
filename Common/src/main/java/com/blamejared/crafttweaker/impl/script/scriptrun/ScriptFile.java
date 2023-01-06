package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IMutableScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptFile;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class ScriptFile implements IScriptFile {
    
    private record PreprocessedData(List<String> contents, boolean allowLoading) {}
    
    private final String fileName;
    private final Map<IPreprocessor, List<IPreprocessor.Match>> matches; // Not using multimap due to no order guarantees
    private final RunInfo info;
    private final List<String> fileContents;
    private final Supplier<PreprocessedData> preprocessedFile;
    
    private ScriptFile(
            final String fileName,
            final Map<IPreprocessor, List<IPreprocessor.Match>> matches,
            final List<String> contents,
            final RunInfo info
    ) {
        
        this.fileName = fileName;
        this.matches = new HashMap<>(matches);
        this.fileContents = ImmutableList.copyOf(contents);
        this.info = info;
        this.preprocessedFile = Suppliers.memoize(this::preprocess);
    }
    
    static ScriptFile of(final String name, final Stream<String> lines, final RunInfo info, final Collection<IPreprocessor> preprocessors) {
        
        final Map<String, IPreprocessor> fastPreprocessorLookupMap = buildFastLookupMap(preprocessors);
        final Pair<List<String>, Map<IPreprocessor, List<IPreprocessor.Match>>> data = read(lines, fastPreprocessorLookupMap);
        preprocessors.forEach(pp -> data.getSecond().computeIfAbsent(
                pp,
                it -> pp.defaultValue() != null ? List.of(new IPreprocessor.Match(pp, -1, pp.defaultValue())) : null
        ));
        return new ScriptFile(name, data.getSecond(), data.getFirst(), info);
    }
    
    static ScriptFile of(final Logger logger, final Path baseDirectory, final Path file, final RunInfo info, final Collection<IPreprocessor> preprocessors) {
        
        if(!verifyChild(baseDirectory, file)) {
            throw new IllegalArgumentException("File " + file + " is not contained within " + baseDirectory);
        }
        final String name = baseDirectory.toAbsolutePath().relativize(file.toAbsolutePath()).toString();
        try(final Stream<String> lines = lines(logger, file)) {
            return of(name, lines, info, preprocessors);
        }
    }
    
    private static boolean verifyChild(final Path parent, final Path file) {
        
        Path current = file;
        while(current != null) {
            if(current.equals(parent)) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }
    
    private static Stream<String> lines(final Logger logger, final Path file) {
        
        try {
            return Files.lines(file, StandardCharsets.UTF_8);
        } catch(final IOException e) {
            logger.error("Could not load file {}", file, e);
            return Stream.of();
        }
    }
    
    private static Map<String, IPreprocessor> buildFastLookupMap(final Collection<IPreprocessor> preprocessors) {
        
        return preprocessors.stream().collect(Collectors.toMap(IPreprocessor::name, Function.identity()));
    }
    
    private static Pair<List<String>, Map<IPreprocessor, List<IPreprocessor.Match>>> read(
            final Stream<String> lines,
            final Map<String, IPreprocessor> preprocessors
    ) {
        
        final List<String> contents = new ArrayList<>();
        final Map<IPreprocessor, List<IPreprocessor.Match>> matches = new HashMap<>();
        final AtomicInteger lineCounter = new AtomicInteger();
        lines.forEachOrdered(it -> {
            contents.add(it);
            tryReadingPreprocessors(it, lineCounter.incrementAndGet(), matches, preprocessors);
        });
        return Pair.of(contents, matches);
    }
    
    private static void tryReadingPreprocessors(
            final String line,
            final int lineNumber,
            final Map<IPreprocessor, List<IPreprocessor.Match>> matches,
            final Map<String, IPreprocessor> preprocessors
    ) {
        
        final Matcher matcher = IPreprocessor.PREPROCESSOR_PATTERN.matcher(line);
        if(!matcher.find()) {
            return;
        }
        
        final String name = matcher.group().substring(1).trim().toLowerCase(Locale.ENGLISH);
        final IPreprocessor preprocessor = preprocessors.get(name);
        if(preprocessor == null) {
            return;
        }
        
        final IPreprocessor.Match match = new IPreprocessor.Match(preprocessor, lineNumber, line.substring(matcher.end()));
        matches.computeIfAbsent(preprocessor, it -> new ArrayList<>(1)).add(match);
    }
    
    @Override
    public String name() {
        
        return this.fileName;
    }
    
    @Override
    public List<String> fileContents() {
        
        return this.fileContents;
    }
    
    @Override
    public List<String> preprocessedContents() {
        
        return this.preprocessedFile.get().contents();
    }
    
    @Override
    public Optional<SourceFile> toSourceFile() {
        
        return this.preprocessedFile.get().allowLoading() ? Optional.of(this.toFile()) : Optional.empty();
    }
    
    @Override
    public List<IPreprocessor.Match> matchesFor(final IPreprocessor preprocessor) {
        
        return Optional.ofNullable(this.matches.get(preprocessor))
                .map(Collections::unmodifiableList)
                .orElseGet(Collections::emptyList);
    }
    
    private PreprocessedData preprocess() {
        
        final ToIntFunction<Map.Entry<IPreprocessor, ?>> intExtractor = it -> it.getKey().priority();
        final MutableRunInfo mutableInfo = new MutableRunInfo(this.info);
        return this.matches.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(intExtractor).reversed())
                .reduce(
                        new PreprocessedData(new ArrayList<>(this.fileContents), true),
                        (data, entry) -> this.preprocess(data, entry, mutableInfo),
                        this::combine
                );
    }
    
    private PreprocessedData preprocess(final PreprocessedData data, final Map.Entry<IPreprocessor, List<IPreprocessor.Match>> entry, final IMutableScriptRunInfo info) {
        
        if(!data.allowLoading()) {
            return data;
        }
        
        final List<String> newContents = new ArrayList<>(data.contents());
        final boolean load = entry.getKey().apply(this, newContents, info, entry.getValue());
        return new PreprocessedData(newContents, load);
    }
    
    private PreprocessedData combine(final PreprocessedData a, final PreprocessedData b) {
        
        return new PreprocessedData(this.intersect(a.contents(), b.contents()), a.allowLoading() && b.allowLoading());
    }
    
    private List<String> intersect(final List<String> a, final List<String> b) {
        
        return Util.make(new ArrayList<>(a), it -> it.retainAll(b));
    }
    
    private SourceFile toFile() {
        
        return new PreprocessedSourceFile(this.fileName, this.preprocessedContents(), this.matches);
    }
    
}
