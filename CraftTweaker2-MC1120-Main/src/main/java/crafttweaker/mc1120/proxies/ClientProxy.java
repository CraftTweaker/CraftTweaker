package crafttweaker.mc1120.proxies;

import crafttweaker.*;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.events.ClientEventHandler;
import crafttweaker.mc1120.game.MCGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.client.util.SearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.resource.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerEvents() {
        super.registerEvents();
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
    
    @Override
    public void registerReloadListener() {
        super.registerReloadListener();
        IReloadableResourceManager manager = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
        manager.registerReloadListener((ISelectiveResourceReloadListener) (resourceManager, resourcePredicate) -> {
            if(resourcePredicate.test(VanillaResourceType.LANGUAGES)) {
                for(IAction action : MCGame.TRANSLATION_ACTIONS) {
                    CraftTweakerAPI.apply(action);
                }
            }
        });
    }

    @Override
    public void fixRecipeBook() {
        if (Loader.isModLoaded("norecipebook"))
            return;
        final Minecraft minecraft = Minecraft.getMinecraft();
        if(!CraftTweaker.alreadyChangedThePlayer) {
            CraftTweaker.alreadyChangedThePlayer = true;
            RecipeBookClient.rebuildTable();
            if(CraftTweakerAPI.ENABLE_SEARCH_TREE_RECALCULATION) {
                try {
                    minecraft.populateSearchTreeManager();
                    ((SearchTree<ItemStack>) minecraft.getSearchTreeManager().get(SearchTreeManager.ITEMS)).recalculate();
                    ((SearchTree<RecipeList>) minecraft.getSearchTreeManager().get(SearchTreeManager.RECIPES)).recalculate();
                } catch (Exception ex) {
                    CraftTweakerAPI.logError("Error repopulating the SearchTree Managers. If this problem occurs more often you can disable it with '#disable_search_tree' in any CrT script.", ex);
                }
            }
            CraftTweakerAPI.logInfo("Fixed the RecipeBook");
        }
    }
}
