package crafttweaker.mc1120.commands;

import mezz.jei.api.*;

/**
 * @author BloodWorkXGaming
 */
@JEIPlugin
public class CrTJEIPlugin implements IModPlugin {
    
    static IJeiRuntime JEI_RUNTIME;
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        System.out.println("JEI Plugin loaded");
        JEI_RUNTIME = jeiRuntime;
    }
}
