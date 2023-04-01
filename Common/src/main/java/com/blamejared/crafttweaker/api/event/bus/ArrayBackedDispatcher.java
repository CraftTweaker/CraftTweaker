package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEvent;
import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.function.Consumer;

final class ArrayBackedDispatcher<T extends IEvent<T>> implements BusDispatcher<T> {
    
    private static final int DEFAULT_PER_PHASE_QUANTITY = 5;
    private static final Consumer<Object> UNREGISTERED = it -> {};
    
    private final boolean accountForCancellation;
    
    private int perPhaseQuantity;
    private Consumer<?>[] consumers;
    private HandlerToken<?>[] tokens;
    
    ArrayBackedDispatcher(final boolean accountForCancellation) {
        
        this.accountForCancellation = accountForCancellation;
        this.perPhaseQuantity = DEFAULT_PER_PHASE_QUANTITY;
        this.consumers = new Consumer<?>[Phase.values().length * this.perPhaseQuantity];
        this.tokens = new HandlerToken<?>[this.consumers.length];
    }
    
    @Override
    public HandlerToken<T> register(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer) {
        
        final HandlerToken<T> token = this.doRegister(listenToCanceled, phase, consumer, false);
        if(token != null) {
            return token;
        }
        
        // Not enough space: bigger!
        this.grow();
        return this.doRegister(listenToCanceled, phase, consumer, true);
    }
    
    @Override
    public void unregister(final HandlerToken<T> token) {
        
        for(int i = 0, s = this.tokens.length; i < s; ++i) {
            
            if(this.tokens[i] == token) {
                final int next = i + 1;
                final Consumer<T> consumer = next == s || this.consumers[next] == null ? null : GenericUtil.uncheck(UNREGISTERED);
                this.consumers[i] = consumer;
                return;
            }
        }
        
        throw new IllegalArgumentException("Unknown token");
    }
    
    @Override
    public T dispatch(final T event) {
        
        final Consumer<?>[] consumers = this.consumers;
        for(int i = 0, s = consumers.length; i < s; ++i) {
            
            @SuppressWarnings("unchecked") final Consumer<T> consumer = (Consumer<T>) consumers[i];
            
            if(consumer == null) {
                i = ((i / this.perPhaseQuantity) + 1) * this.perPhaseQuantity - 1;
            } else {
                consumer.accept(event);
            }
        }
        
        return event;
    }
    
    @SuppressWarnings("unchecked")
    private HandlerToken<T> doRegister(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer, final boolean exception) {
        
        final HandlerToken<T> token = new HandlerToken<>();
        
        final int firstIndex = phase.ordinal() * 10;
        for(int i = firstIndex, s = firstIndex + DEFAULT_PER_PHASE_QUANTITY; i < s; ++i) {
            if(this.consumers[i] == null || this.consumers[i] == UNREGISTERED) {
                this.consumers[i] = new Dispatcher<>(this.accountForCancellation ? (Consumer<T>) BusDispatcher.wrap(listenToCanceled, consumer) : consumer);
                this.tokens[i] = token;
                return token;
            }
        }
        
        if(exception) {
            throw new IllegalStateException();
        }
        
        return null;
    }
    
    private void grow() {
        
        final int oldSize = this.perPhaseQuantity;
        final int newSize = oldSize * 3 / 2;
        final Phase[] phases = Phase.values();
        
        final Consumer<?>[] newConsumers = new Consumer<?>[newSize * phases.length];
        final HandlerToken<?>[] newTokens = new HandlerToken<?>[newConsumers.length];
        
        for(int p = 0, l = phases.length; p < l; ++p) {
            final int newP = p * newSize;
            final int oldP = p * oldSize;
            
            for(int i = 0; i < oldSize; ++i) {
                final int newI = newP + i;
                final int oldI = oldP + i;
                
                newConsumers[newI] = this.consumers[oldI];
                newTokens[newI] = this.tokens[oldI];
            }
        }
        
        this.perPhaseQuantity = newSize;
        this.consumers = newConsumers;
        this.tokens = newTokens;
    }
    
}
