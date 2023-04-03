package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

final class SelectiveBackingDispatcher<T> implements BusDispatcher<T> {
    
    private record Command<T>(boolean listenToCanceled, Phase phase, Consumer<T> consumer, HandlerToken<T> token) {
        
        void execute(final BusDispatcher<T> dispatcher) {
            
            if(this.token() != null) {
                dispatcher.unregister(this.token());
            } else {
                dispatcher.register(this.listenToCanceled(), this.phase(), this.consumer());
            }
        }
        
    }
    
    private static final int THRESHOLD = 10;
    
    private final boolean allowCancellation;
    
    private BusDispatcher<T> dispatcher;
    private boolean hasSwitched;
    private int additions;
    private List<Command<T>> commands;
    
    SelectiveBackingDispatcher(final boolean allowCancellation) {
        
        this.dispatcher = new ArrayBackedDispatcher<>(this.allowCancellation = allowCancellation);
        this.hasSwitched = false;
        this.additions = 0;
        this.commands = new ArrayList<>();
    }
    
    @Override
    public HandlerToken<T> register(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer) {
        
        HandlerToken<T> token = this.dispatcher.register(listenToCanceled, phase, consumer);
        
        if(!this.hasSwitched) {
            ++this.additions;
            
            if(this.additions == THRESHOLD) {
                this.dispatcher = new ListBackedDispatcher<>(this.allowCancellation);
                this.commands.forEach(it -> it.execute(this.dispatcher));
                this.commands = null;
                this.hasSwitched = true;
                
                token = this.dispatcher.register(listenToCanceled, phase, consumer);
            }
        }
        
        return token;
    }
    
    @Override
    public void unregister(final HandlerToken<T> token) {
        
        this.dispatcher.unregister(token);
        if(!this.hasSwitched) {
            --this.additions;
            
            if(this.additions == 0) {
                this.commands.clear();
            }
        }
    }
    
    @Override
    public T dispatch(final T event) {
        
        return this.dispatcher.dispatch(event);
    }
    
}
