package crafttweaker.mc1120.commands;

import crafttweaker.CraftTweakerAPI;
import mezz.jei.api.*;

/**
 * @author BloodWorkXGaming
 */
@JEIPlugin
public class CrTJEIPlugin implements IModPlugin {
    
    static IJeiRuntime JEI_RUNTIME;
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        CraftTweakerAPI.logInfo("JEI Plugin loaded");
        JEI_RUNTIME = jeiRuntime;
    }
}
