package com.blamejared.crafttweaker.api.plugin;

public interface IListenerRegistrationHandler {
    
    void onZenDataRegistrationCompletion(final Runnable runnable);
    
    void onCraftTweakerLoadCompletion(final Runnable runnable);
    
}
