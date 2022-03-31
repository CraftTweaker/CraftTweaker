package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains all info on Bracket resolvers, validators and dumpers
 */
public final class BracketResolverRegistry {
    
    private record BracketData(Map<String, BracketHandle> brackets) {
        
        BracketData() {
            
            this(new HashMap<>());
        }
        
    }
    
    private record BracketHandle(BracketExpressionParser parser, IBracketDumperInfo dumperInfo) {}
    
    private final Map<IScriptLoader, BracketData> brackets = new HashMap<>();
    
    public void registerBracket(
            final IScriptLoader loader,
            final String name,
            final BracketExpressionParser bracketParser,
            final IBracketParserRegistrationHandler.DumperData dumperData
    ) {
        
        final BracketData data = this.brackets.computeIfAbsent(loader, it -> {
            throw new IllegalStateException("No data for loader " + loader + ": this cannot happen");
        });
        final Map<String, BracketHandle> loaderBrackets = data.brackets();
        if(loaderBrackets.containsKey(name)) {
            throw new IllegalArgumentException("A bracket handler with the name '" + name + "' is already registered in loader '" + loader + "'");
        }
        final BracketDumperInfo info = dumperData == null ? null : new BracketDumperInfo(name, dumperData);
        loaderBrackets.put(name, new BracketHandle(bracketParser, info));
    }
    
    public void fillLoaderData(final Collection<IScriptLoader> loader) {
        
        loader.forEach(it -> this.brackets.put(it, new BracketData()));
    }
    
    public void applyInheritanceRules() {
        
        final Map<IScriptLoader, BracketData> snapshot = this.createSnapshot();
        final Map<IScriptLoader, BracketData> inherited = this.applyInheritanceRules(snapshot);
        this.brackets.clear();
        this.brackets.putAll(inherited);
    }
    
    public Map<String, IBracketDumperInfo> getBracketDumpers(final IScriptLoader loader) {
        
        return this.brackets.getOrDefault(loader, new BracketData())
                .brackets()
                .entrySet()
                .stream()
                .filter(it -> it.getValue().dumperInfo() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().dumperInfo()));
    }
    
    public Map<String, BracketExpressionParser> getBracketResolvers(final IScriptLoader loader) {
        
        return this.brackets.getOrDefault(loader, new BracketData())
                .brackets()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().parser()));
    }
    
    private Map<IScriptLoader, BracketData> createSnapshot() {
        
        return this.brackets.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                it -> new BracketData(new HashMap<>(it.getValue().brackets()))
                        )
                );
    }
    
    private Map<IScriptLoader, BracketData> applyInheritanceRules(final Map<IScriptLoader, BracketData> snapshot) {
        
        return snapshot.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, it -> this.applyInheritanceRules(it.getKey(), snapshot)));
    }
    
    private BracketData applyInheritanceRules(final IScriptLoader loader, final Map<IScriptLoader, BracketData> snapshot) {
        
        final Map<String, BracketHandle> inheritedData = new HashMap<>();
        final Collection<Map<String, BracketHandle>> inheritanceData = this.computeInheritanceData(loader, snapshot);
        inheritanceData.forEach(it -> {
            try {
                this.tryMerge(inheritedData, it);
            } catch(final Exception e) {
                throw new IllegalStateException("Unable to apply inheritance rules for " + loader.name(), e);
            }
        });
        return new BracketData(inheritedData);
    }
    
    private Collection<Map<String, BracketHandle>> computeInheritanceData(final IScriptLoader loader, final Map<IScriptLoader, BracketData> snapshot) {
        
        return Stream.concat(loader.allInheritedLoaders().stream(), Stream.of(loader))
                .map(snapshot::get)
                .filter(Objects::nonNull)
                .map(BracketData::brackets)
                .toList();
    }
    
    private void tryMerge(final Map<String, BracketHandle> loaderData, final Map<String, BracketHandle> inheritedData) {
        
        inheritedData.forEach((name, handle) -> {
            if(loaderData.containsKey(name)) {
                throw new IllegalStateException("A bracket handler with the name " + name + " is already registered");
            }
            loaderData.put(name, handle);
        });
    }
    
}
