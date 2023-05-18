package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.util.GenericUtil;

final class ArrayBackedDispatcher<T> {
    
    private static final class ArrayHandlerToken<T> implements IHandlerToken<T> {
        private final int index;
        private boolean valid;
        
        private ArrayHandlerToken(final int index) {
            this.index = index;
            this.valid = true;
        }
        
        static <T> ArrayHandlerToken<T> of(final int index) {
            return new ArrayHandlerToken<>(index);
        }
        
        int index() {
            this.valid();
            return this.index;
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
    
    private record CatchingDispatcher<T>(IEventDispatcher<T> dispatcher) implements IEventDispatcher<T> {
        
        @Override
        public void dispatch(final T event) {
            try {
                this.dispatcher().dispatch(event);
            } catch (final Exception e) {
                throw new BusHandlingException(e);
            }
        }
        
    }
    
    private static final IEventDispatcher<Object> UNREGISTERED = it -> {};
    
    private IEventDispatcher<T>[] dispatchers;
    
    ArrayBackedDispatcher() {
        
        this.dispatchers = GenericUtil.uncheck(new IEventDispatcher<?>[0]);
    }
    
    public IHandlerToken<T> register(final IEventDispatcher<T> dispatcher) {
        
        final IHandlerToken<T> token = this.doRegister(dispatcher, false);
        if(token != null) {
            return token;
        }
        
        // Not enough space: bigger!
        this.grow();
        return this.doRegister(dispatcher, true);
    }
    
    public void unregister(final IHandlerToken<T> token) {
        
        if (!(token instanceof ArrayBackedDispatcher.ArrayHandlerToken<T> arrayToken)) {
            throw new IllegalArgumentException("Invalid token object for dispatcher");
        }
        
        final int next = arrayToken.index() + 1;
        final IEventDispatcher<T> newConsumer = next == this.dispatchers.length || this.dispatchers[next] == null? null : GenericUtil.uncheck(UNREGISTERED);
        this.dispatchers[arrayToken.index()] = newConsumer;
        
        arrayToken.invalidate();
    }
    
    public void dispatch(final T event) {
        
        for(final IEventDispatcher<T> dispatcher : this.dispatchers) {
            if(dispatcher == null) {
                break;
            }
            dispatcher.dispatch(event);
        }
    }
    
    private IHandlerToken<T> doRegister(final IEventDispatcher<T> dispatcher, final boolean exception) {
        
        for (int i = 0, s = this.dispatchers.length; i < s; ++i) {
            if (this.dispatchers[i] == null || this.dispatchers[i] == UNREGISTERED) {
                this.dispatchers[i] = new CatchingDispatcher<>(dispatcher);
                return ArrayHandlerToken.of(i);
            }
        }
        
        if (exception) {
            throw new IllegalStateException("Unable to register handler");
        }
        
        return null;
    }
    
    private void grow() {
        
        final int length = this.dispatchers.length;
        
        final IEventDispatcher<T>[] replacement = GenericUtil.uncheck(new IEventDispatcher<?>[(length * 3 / 2) + 1]);
        System.arraycopy(this.dispatchers, 0, replacement, 0, length);
        
        this.dispatchers = replacement;
    }
    
}
