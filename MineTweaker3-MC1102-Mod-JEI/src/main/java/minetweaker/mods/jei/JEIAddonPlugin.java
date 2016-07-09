package minetweaker.mods.jei;

import mezz.jei.api.*;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.util.IEventHandler;
import sun.util.resources.cldr.zh.TimeZoneNames_zh;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {
    private static boolean eventAdded = false;
    public static IJeiHelpers jeiHelpers;
    public static IItemRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;


    @Override
    public void register(IModRegistry registry) {
        this.jeiHelpers = registry.getJeiHelpers();
        this.itemRegistry = registry.getItemRegistry();

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
