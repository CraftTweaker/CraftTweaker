package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

final class ListenerRegistrationHandler implements IListenerRegistrationHandler {
    
    private final List<Runnable> endListeners;
    private final List<Runnable> zenListeners;
    
    private ListenerRegistrationHandler() {
        
        this.endListeners = new ArrayList<>();
        this.zenListeners = new ArrayList<>();
    }
    
    static ListenerRegistrationHandler of(final Consumer<IListenerRegistrationHandler> consumer) {
        
        final ListenerRegistrationHandler handler = new ListenerRegistrationHandler();
        consumer.accept(handler);
        return handler;
    }
    
    @Override
    public void onZenDataRegistrationCompletion(final Runnable runnable) {
        
        this.zenListeners.add(runnable);
    }
    
    @Override
    public void onCraftTweakerLoadCompletion(final Runnable runnable) {
        
        this.endListeners.add(runnable);
    }
    
    List<Runnable> endListeners() {
        
        return this.endListeners;
    }
    
    List<Runnable> zenListeners() {
        
        return this.zenListeners;
    }
    
}
