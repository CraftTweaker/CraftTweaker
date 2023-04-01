package com.blamejared.crafttweaker.api.event.bus;

final class BusHandlingException extends RuntimeException {
    
    BusHandlingException(final Throwable t) {
        
        super("An error occurred while trying to dispatch an event onto the bus", t);
    }
    
}
