package com.blamejared.crafttweaker.gametest;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IListenerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.gametest.framework.TestRunner;
import net.minecraft.resources.ResourceLocation;

@CraftTweakerPlugin(CraftTweakerConstants.MOD_ID + ":gametest")
@SuppressWarnings("unused") // Autowired
public class GameTestPlugin implements ICraftTweakerPlugin {
    
    public static final ResourceLocation GAMETEST_SOURCE_ID = CraftTweakerConstants.rl("gametest");
    
    @Override
    public void registerLoadSource(IScriptLoadSourceRegistrationHandler handler) {
        
        handler.registerLoadSource(GAMETEST_SOURCE_ID);
    }
    
    @Override
    public void registerListeners(IListenerRegistrationHandler handler) {
        
        handler.onExecuteRun(scriptRunConfiguration -> {
            // No thank you recursion
            if(scriptRunConfiguration.runKind() == ScriptRunConfiguration.RunKind.GAME_TEST) {
                return;
            }
            new TestRunner(scriptRunConfiguration.loader().name()).runTests();
        });
    }
    
}
