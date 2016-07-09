package minetweaker.mods.jei;

import mezz.jei.api.*;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.util.IEventHandler;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {
    private static boolean eventAdded = false;
    public static IJeiHelpers jeiHelpers;
    public static IItemRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;


    @Override
    public void onJeiHelpersAvailable(IJeiHelpers iJeiHelpers) {
        this.jeiHelpers = iJeiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry iItemRegistry) {
        this.itemRegistry = iItemRegistry;
    }

    @Override
    public void register(IModRegistry registry) {

    }

    @Override
    public void onRecipeRegistryAvailable(@Nonnull IRecipeRegistry iRecipeRegistry) {

    }


    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime iJeiRuntime) {
        this.recipeRegistry = iJeiRuntime.getRecipeRegistry();
        if(!eventAdded){
            MineTweakerImplementationAPI.onPostReload(new IEventHandler<MineTweakerImplementationAPI.ReloadEvent>() {
                @Override
                public void handle(MineTweakerImplementationAPI.ReloadEvent event) {
                    jeiHelpers.reload();
                }
            });
            eventAdded = true;
        }

    }


}
