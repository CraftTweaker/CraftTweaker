package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;

@CraftTweakerPlugin(CraftTweakerConstants.MOD_ID + ":builtin")
@SuppressWarnings("unused") // Autowired
public final class BuiltinCraftTweakerPlugin implements ICraftTweakerPlugin {
    
    @Override
    public void registerLoaders(final ILoaderRegistrationHandler handler) {
        
        handler.registerLoader(CraftTweakerConstants.INIT_LOADER_NAME);
        handler.registerLoader(CraftTweakerConstants.DEFAULT_LOADER_NAME);
    }
    
}
