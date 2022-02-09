package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinCraftTweakerPlugin implements ICraftTweakerPlugin {
    
    private static final ResourceLocation ID = CraftTweakerConstants.rl("builtin");
    
    @Override
    public ResourceLocation id() {
        
        return ID;
    }
    
    @Override
    public void registerLoaders(final ILoaderRegistrationHandler handler) {
        
        handler.registerLoader(CraftTweakerConstants.DEFAULT_LOADER_NAME);
        handler.registerLoader(CraftTweakerConstants.INIT_LOADER_NAME); // TODO("depend on CT loader?")
    }
    
}
