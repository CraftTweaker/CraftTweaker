package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class LoaderRegistrationHandler implements ILoaderRegistrationHandler {
    
    private record LoaderRequest(String name, Set<String> inheritedLoaders) {}
    
    private final Map<String, LoaderRequest> requests;
    
    private LoaderRegistrationHandler() {
        
        this.requests = new HashMap<>();
    }
    
    static Map<String, IScriptLoader> gather(final Consumer<ILoaderRegistrationHandler> populatingConsumer) {
        
        final LoaderRegistrationHandler handler = new LoaderRegistrationHandler();
        handler.requests.put("*", new LoaderRequest("*", Collections.emptySet()));
        populatingConsumer.accept(handler);
        return handler.build();
    }
    
    @Override
    public void registerLoader(final String name, final String... inheritedLoaders) {
        
        if(this.requests.containsKey(name)) {
            
            throw new IllegalArgumentException("Loader '" + name + "' was already registered");
        }
        
        final Stream<String> loaders = Stream.concat(Stream.of("*"), Arrays.stream(inheritedLoaders));
        this.requests.put(name, new LoaderRequest(name, loaders.collect(Collectors.toUnmodifiableSet())));
    }
    
    Map<String, IScriptLoader> build() {
        
        final Map<String, LoaderRequest> unsatisfiedRequests = new HashMap<>(this.requests);
        final Map<String, IScriptLoader> loaders = new HashMap<>();
        
        while(!unsatisfiedRequests.isEmpty()) {
            final Map<String, LoaderRequest> satisfiableRequests = this.findValidRequests(unsatisfiedRequests, loaders.keySet());
            
            if(satisfiableRequests.isEmpty()) {
                throw new IllegalStateException("Unable to create all loaders due to unsatisfied dependency linkage: " + unsatisfiedRequests);
            }
            
            this.satisfyRequests(satisfiableRequests, loaders);
            satisfiableRequests.keySet().forEach(unsatisfiedRequests::remove);
        }
        
        return ImmutableMap.copyOf(loaders);
    }
    
    private Map<String, LoaderRequest> findValidRequests(final Map<String, LoaderRequest> requests, final Set<String> availableIds) {
        
        return requests.entrySet()
                .stream()
                .filter(it -> availableIds.containsAll(it.getValue().inheritedLoaders()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    private void satisfyRequests(final Map<String, LoaderRequest> requests, final Map<String, IScriptLoader> output) {
        
        requests.forEach((id, request) -> {
            final Collection<IScriptLoader> inherited = request.inheritedLoaders().stream().map(output::get).toList();
            final IScriptLoader loader = new LoaderData(id, inherited);
            output.put(id, loader);
        });
    }
    
}
