package minetweaker.mods.jei;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import minetweaker.MineTweakerAPI;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@mezz.jei.api.JEIPlugin
public class JEIAddonPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelpers;
    public static IItemRegistry itemRegistry;
    public static IRecipeRegistry recipeRegistry;

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    @Override
    public void register(IModRegistry registry) {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void command(CommandEvent e) {
        if (e.command.getCommandAliases().contains("mt") && e.parameters[0].equals("reload")) {
            jeiHelpers.reload();
        }
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {
        this.recipeRegistry = recipeRegistry;
    }

}
