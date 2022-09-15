package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IReplacerComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptRunModuleConfiguratorRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IVillagerTradeRegistrationHandler;
import net.minecraft.resources.ResourceLocation;

record DecoratedCraftTweakerPlugin(ResourceLocation id, ICraftTweakerPlugin plugin) implements ICraftTweakerPlugin {
    
    @Override
    public void initialize() {
        
        this.plugin().initialize();
    }
    
    @Override
    public void registerLoaders(final ILoaderRegistrationHandler handler) {
        
        this.plugin().registerLoaders(handler);
    }
    
    @Override
    public void registerLoadSource(final IScriptLoadSourceRegistrationHandler handler) {
        
        this.plugin().registerLoadSource(handler);
    }
    
    @Override
    public void registerModuleConfigurators(final IScriptRunModuleConfiguratorRegistrationHandler handler) {
        
        this.plugin().registerModuleConfigurators(handler);
    }
    
    @Override
    public void registerBracketParsers(final IBracketParserRegistrationHandler handler) {
        
        this.plugin().registerBracketParsers(handler);
    }
    
    @Override
    public void registerRecipeComponents(final IRecipeComponentRegistrationHandler handler) {
        
        this.plugin().registerRecipeComponents(handler);
    }
    
    @Override
    public void registerRecipeHandlers(final IRecipeHandlerRegistrationHandler handler) {
        
        this.plugin().registerRecipeHandlers(handler);
    }
    
    @Override
    public void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {
        
        this.plugin().manageJavaNativeIntegration(handler);
    }
    
    @Override
    public void registerListeners(final IListenerRegistrationHandler handler) {
        
        this.plugin().registerListeners(handler);
    }
    
    @Override
    public void registerVillagerTradeConverters(final IVillagerTradeRegistrationHandler handler) {
        
        this.plugin().registerVillagerTradeConverters(handler);
    }
    
    @Override
    public void registerCommands(final ICommandRegistrationHandler handler) {
        
        this.plugin().registerCommands(handler);
    }
    
    @Override
    public void registerTaggableElements(final ITaggableElementRegistrationHandler handler) {
        
        this.plugin.registerTaggableElements(handler);
    }
    
    @Override
    public void registerReplacerComponents(final IReplacerComponentRegistrationHandler handler) {
        
        this.plugin().registerReplacerComponents(handler);
    }
    
    @Override
    public String toString() {
        
        return this.id().toString();
    }
    
}
