package com.blamejared.crafttweaker.api.event;

public interface ICancelableEvent {
    
    void cancel();
    
    boolean canceled();
    
}
