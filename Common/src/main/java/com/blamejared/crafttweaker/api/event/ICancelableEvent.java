package com.blamejared.crafttweaker.api.event;

public interface ICancelableEvent<T extends ICancelableEvent<T>> extends IEvent<T> {
    
    void cancel();
    
    boolean canceled();
    
}
