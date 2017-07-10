package crafttweaker.mc1120.commands;

import mezz.jei.api.*;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.runtime.JeiRuntime;

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
