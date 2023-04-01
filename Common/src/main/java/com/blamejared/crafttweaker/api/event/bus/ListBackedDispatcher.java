package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEvent;
import com.blamejared.crafttweaker.api.event.Phase;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class ListBackedDispatcher<T extends IEvent<T>> implements BusDispatcher<T> {
    
    private final boolean accountForCancellation;
    private final Multimap<Phase, Consumer<T>> consumers;
    private final Map<HandlerToken<T>, Consumer<T>> tokens;
    
    ListBackedDispatcher(final boolean accountForCancellation) {
        
        this.accountForCancellation = accountForCancellation;
        this.consumers = Multimaps.newListMultimap(new EnumMap<>(Phase.class), ArrayList::new);
        this.tokens = new HashMap<>();
    }
    
    @Override
    public HandlerToken<T> register(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer) {
        
        @SuppressWarnings("unchecked") final Consumer<T> wrapped = new Dispatcher<>(this.accountForCancellation ? (Consumer<T>) BusDispatcher.wrap(listenToCanceled, consumer) : consumer);
        final HandlerToken<T> token = new HandlerToken<>();
        
        this.consumers.put(phase, wrapped);
        this.tokens.put(token, wrapped);
        
        return token;
    }
    
    @Override
    public void unregister(final HandlerToken<T> token) {
        
        final Consumer<T> instance = this.tokens.get(token);
        if(instance == null) {
            throw new IllegalArgumentException("Invalid token for dispatcher");
        }
        
        this.consumers.values().remove(instance);
    }
    
    @Override
    public T dispatch(final T event) {
        
        this.consumers.values().forEach(it -> it.accept(event));
        return event;
    }
    
}
