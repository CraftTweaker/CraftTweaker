package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.function.Consumer;

final class ArrayBackedDispatcher<T> implements BusDispatcher<T> {
    
    static final class ArrayHandlerToken<T> implements HandlerToken<T> {
        private final long positions;
        private boolean valid;
        
        private ArrayHandlerToken(final long positions) {
            this.positions = positions;
            this.valid = true;
        }
        
        static <T> ArrayHandlerToken<T> of(final Phase phase, final int index) {
            return new ArrayHandlerToken<>(((long) phase.ordinal()) << 32L | ((long) index));
        }
        
        int phase() {
            this.valid();
            return (int) (this.positions >>> 32L);
        }
        
        int index() {
            this.valid();
            return (int) this.positions;
        }
        
        void invalidate() {
            this.valid = false;
        }
        
        private void valid() {
            if (!this.valid) {
                throw new IllegalStateException("Token has been invalidated");
            }
        }
    }
    
    private static final Consumer<Object> UNREGISTERED = it -> {};
    
    private final boolean accountForCancellation;
    private final Consumer<T>[][] consumers;
    
    ArrayBackedDispatcher(final boolean accountForCancellation) {
        
        this.accountForCancellation = accountForCancellation;
        this.consumers = GenericUtil.uncheck(new Consumer<?>[Phase.values().length][0]);
    }
    
    @Override
    public HandlerToken<T> register(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer) {
        
        final HandlerToken<T> token = this.doRegister(listenToCanceled, phase, consumer, false);
        if(token != null) {
            return token;
        }
        
        // Not enough space: bigger!
        this.grow(phase);
        return this.doRegister(listenToCanceled, phase, consumer, true);
    }
    
    @Override
    public void unregister(final HandlerToken<T> token) {
        
        if (!(token instanceof ArrayBackedDispatcher.ArrayHandlerToken<T> arrayToken)) {
            throw new IllegalArgumentException("Invalid token object for dispatcher");
        }
        
        final Consumer<T>[] consumers = this.consumers[arrayToken.phase()];
        final int next = arrayToken.index() + 1;
        final Consumer<T> newConsumer = next == consumers.length || consumers[next] == null? null : GenericUtil.uncheck(UNREGISTERED);
        consumers[arrayToken.index()] = newConsumer;
        
        arrayToken.invalidate();
    }
    
    @Override
    public T dispatch(final T event) {
        
        final Consumer<T>[][] consumers = this.consumers;
        for(final Consumer<T>[] phasedConsumers : consumers) {
            for(final Consumer<T> phasedConsumer : phasedConsumers) {
                final Consumer<T> consumer = GenericUtil.uncheck(phasedConsumer);
                if(consumer == null) {
                    break;
                }
                consumer.accept(event);
            }
        }
        return event;
    }
    
    private HandlerToken<T> doRegister(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer, final boolean exception) {
        
        final Consumer<T>[] candidates = this.consumers[phase.ordinal()];
        for (int i = 0, s = candidates.length; i < s; ++i) {
            if (candidates[i] == null || candidates[i] == UNREGISTERED) {
                candidates[i] = new Dispatcher<>(this.accountForCancellation, listenToCanceled, consumer);
                return ArrayHandlerToken.of(phase, i);
            }
        }
        
        if (exception) {
            throw new IllegalStateException("Unable to registert handler");
        }
        
        return null;
    }
    
    private void grow(final Phase phase) {
        
        final int target = phase.ordinal();
        final Consumer<T>[] original = this.consumers[target];
        final int length = original.length;
        
        final Consumer<T>[] replacement = GenericUtil.uncheck(new Consumer<?>[(length * 3 / 2) + 1]);
        System.arraycopy(original, 0, replacement, 0, length);
        
        this.consumers[target] = replacement;
    }
    
}
