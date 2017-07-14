package crafttweaker.mc1120.commands;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JEIPlugin;

/**
 * @author BloodWorkXGaming
 */
@JEIPlugin
public class CrTJEIPlugin implements IModPlugin {

    public static IJeiRuntime JEI_RUNTIME;

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        System.out.println("JEI Plugin loaded");
        JEI_RUNTIME = jeiRuntime;
    }
}
