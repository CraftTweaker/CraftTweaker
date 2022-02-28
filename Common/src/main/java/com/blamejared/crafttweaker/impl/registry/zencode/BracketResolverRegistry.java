package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.mojang.datafixers.util.Pair;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains all info on Bracket resolvers, validators and dumpers
 */
public final class BracketResolverRegistry {
    
    private record BracketData(Map<String, BracketHandle> brackets) {
        
        BracketData() {
            
            this(new HashMap<>());
        }
        
    }
    
    private record BracketHandle(IBracketParserRegistrationHandler.Creator creator, IBracketDumperInfo dumperInfo) {}
    
    private final Map<IScriptLoader, BracketData> brackets = new HashMap<>();
    
    public void registerBracket(
            final IScriptLoader loader,
            final String name,
            final IBracketParserRegistrationHandler.Creator bracketCreator,
            final IBracketParserRegistrationHandler.DumperData dumperData
    ) {
        
        final BracketData data = this.brackets.computeIfAbsent(loader, it -> new BracketData());
        final Map<String, BracketHandle> brackets = data.brackets();
        if(brackets.containsKey(name)) {
            throw new IllegalArgumentException("A bracket handler with the name '" + name + "' is already registered in loader '" + loader + "'");
        }
        final BracketDumperInfo info = dumperData == null ? null : new BracketDumperInfo(name, dumperData);
        brackets.put(name, new BracketHandle(bracketCreator, info));
    }
    
    public void applyInheritanceRules() {
        
        List.copyOf(this.brackets.keySet()).forEach(it -> {
            try {
                this.applyInheritanceRules(it);
            } catch(final Exception e) {
                throw new IllegalStateException("Unable to apply inheritance rules for " + it.name());
            }
        });
    }
    
    public Map<String, IBracketDumperInfo> getBracketDumpers(final IScriptLoader loader) {
        
        return this.brackets.getOrDefault(loader, new BracketData())
                .brackets()
                .entrySet()
                .stream()
                .filter(it -> it.getValue().dumperInfo() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().dumperInfo()));
    }
    
    public List<Pair<String, BracketExpressionParser>> getBracketResolvers(final IScriptLoader loader, final String rootPackage, final ScriptingEngine engine, final JavaNativeModule module) {
        
        // TODO("rootPackage is unused: are you actually needed?")
        return this.brackets.getOrDefault(loader, new BracketData())
                .brackets()
                .entrySet()
                .stream()
                .map(it -> Pair.of(it.getKey(), it.getValue().creator().createParser(engine, module)))
                .toList();
    }
    
    private void applyInheritanceRules(final IScriptLoader loader) {
        
        final Map<String, BracketHandle> loaderData = this.brackets.get(loader).brackets();
        loader.inheritedLoaders().forEach(it -> {
            try {
                this.tryMerge(loaderData, this.brackets.getOrDefault(it, new BracketData()).brackets());
            } catch(final Exception e) {
                throw new IllegalStateException("Unable to inherit from " + it.name());
            }
        });
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
