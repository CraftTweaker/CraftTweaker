package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import net.minecraft.resources.ResourceLocation;

record DecoratedCraftTweakerPlugin(ResourceLocation id, ICraftTweakerPlugin plugin) implements ICraftTweakerPlugin {
    
    @Override
    public void registerLoaders(final ILoaderRegistrationHandler handler) {
        
        this.plugin.registerLoaders(handler);
    }
    
    @Override
    public void registerBracketParsers(final IBracketParserRegistrationHandler handler) {
        
        this.plugin.registerBracketParsers(handler);
    }
    
    @Override
    public void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.plugin.manageJavaNativeIntegration(handler);
    }
    
    @Override
    public void registerListeners(final IListenerRegistrationHandler handler) {
        
        this.plugin.registerListeners(handler);
    }
    
    @Override
    public String toString() {
        
        return this.id.toString();
    }
    
}
