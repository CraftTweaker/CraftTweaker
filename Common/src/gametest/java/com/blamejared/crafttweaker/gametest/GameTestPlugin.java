package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import net.minecraft.resources.ResourceLocation;

@CraftTweakerPlugin(CraftTweakerConstants.MOD_ID + ":gametest")
@SuppressWarnings("unused") // Autowired
public class GameTestPlugin implements ICraftTweakerPlugin {
    
    public static final ResourceLocation GAMETEST_SOURCE_ID = CraftTweakerConstants.rl("gametest");
    
    @Override
    public void registerLoadSource(IScriptLoadSourceRegistrationHandler handler) {
        
        handler.registerLoadSource(GAMETEST_SOURCE_ID);
    }
    
}
