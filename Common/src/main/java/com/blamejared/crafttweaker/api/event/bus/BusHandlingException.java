package com.blamejared.crafttweaker.api.event.bus;

public final class BusHandlingException extends RuntimeException {
    
    private final Throwable original;
    
    BusHandlingException(final Throwable t) {
        
        super("An error occurred while trying to dispatch an event onto the bus", t);
        this.original = t instanceof BusHandlingException e? e.original() : t;
    }
    
    public Throwable original() {
        
        return this.original;
    }
    
}
