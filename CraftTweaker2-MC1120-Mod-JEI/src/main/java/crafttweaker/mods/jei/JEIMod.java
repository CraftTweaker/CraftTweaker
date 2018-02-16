package crafttweaker.mods.jei;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.*;
import crafttweaker.mods.jei.commands.ConflictCommand;
import crafttweaker.mods.jei.recipeWrappers.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;
import static crafttweaker.mods.jei.JEI.*;

@Mod(modid = "crafttweakerjei", name = "CraftTweaker JEI Support", version = "2.0.0", dependencies = "after:jei;", acceptedMinecraftVersions = "[1.12, 1.13)")
public class JEIMod {
    
    @Mod.EventHandler
    public void onFMLLoadComplete(FMLLoadCompleteEvent event) {
        if(Loader.isModLoaded("jei")) {
            try {
                LATE_ACTIONS.forEach(CraftTweakerAPI::apply);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        
        
        //region >>> conflict Command
        if(Loader.isModLoaded("jei")) {
            CTChatCommand.registerCommand(new ConflictCommand());
        } else {
            CTChatCommand.registerCommand(new CraftTweakerCommand("conflict") {
                @Override
                protected void init() {
                    setDescription(getClickableCommandText("\u00A72/ct conflict", "/ct conflict", true), getNormalMessage(" \u00A73Lists all conflicting crafting recipes in the game"), getNormalMessage(" \u00A73Might take a bit of time depending on the size of the pack"), getNormalMessage(" \u00A73This needs to be run on a client and with JEI installed"));
                }
                
                @Override
                public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                    sender.sendMessage(getNormalMessage("\u00A74This Command needs to be run with JEI installed"));
                }
            });
        }
        //endregion
    }
    
    public static void onRegistered() {
        try {
            DESCRIPTIONS.forEach(CraftTweakerAPI::apply);
        } catch(Exception e) {
            e.printStackTrace();
        }
        BrewingRecipeCWrapper.registerBrewingRecipe();
        CraftingRecipeWrapperShapeless.registerCraftingRecipes();
        CraftingRecipeWrapperShaped.registerCraftingRecipes();
    }
}
