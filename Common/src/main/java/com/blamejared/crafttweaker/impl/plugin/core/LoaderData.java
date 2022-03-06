package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

record LoaderData(String name, Collection<IScriptLoader> inheritedLoaders) implements IScriptLoader {
    
    @Override
    public String toString() {
        
        final Collection<String> flattened = new ArrayList<>();
        final Queue<IScriptLoader> workingQueue = new ArrayDeque<>();
        this.inheritedLoaders().forEach(workingQueue::offer);
        
        while(!workingQueue.isEmpty()) {
            final IScriptLoader loader = workingQueue.poll();
            flattened.add(loader.name());
            loader.inheritedLoaders().forEach(workingQueue::offer);
        }
        
        return "%s <- %s".formatted(this.name(), flattened);
    }
    
}
