package minetweaker.mods.jei;

import mezz.jei.gui.overlay.ItemListOverlay;
import minetweaker.*;
import minetweaker.api.item.*;
import minetweaker.mc1112.recipes.MCRecipeManager;
import minetweaker.util.IEventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.*;

import static minetweaker.api.minecraft.MineTweakerMC.*;
import static minetweaker.mc1112.recipes.MCRecipeManager.recipes;

/**
 * MineTweaker JEI support.
 * <p>
 * Enables hiding JEI items as well as adding new item stacks. These item stacks
 * can then show a custom message or contain NBT data. Can be used to show a
 * custom message or lore with certain items, or to provide spawnable items with
 * specific NBT tags.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.jei.JEI")
public class JEI {

    private static boolean shouldReload = false;

    @ZenMethod
    public static void hide(IItemStack stack) {
        MineTweakerAPI.apply(new Hide(getItemStack(stack)));
    }
    
    
    @ZenMethod
    public static void removeAndHide(IIngredient output, @Optional boolean nbtMatch) {
        List<IRecipe> toRemove = new ArrayList<>();
        List<Integer> removeIndex = new ArrayList<>();
        for(int i = 0; i < recipes.size(); i++) {
            IRecipe recipe = recipes.get(i);
            if(!recipe.getRecipeOutput().isEmpty()) {
                if(nbtMatch ? output.matchesExact(getIItemStack(recipe.getRecipeOutput())) : output.matches(getIItemStack(recipe.getRecipeOutput()))) {
                    toRemove.add(recipe);
                    removeIndex.add(i);
                }
            }
        }
        
        MineTweakerAPI.apply(new MCRecipeManager.ActionRemoveRecipes(toRemove, removeIndex));
        MineTweakerAPI.apply(new Hide(getItemStack(output)));
    }
    private static class Hide implements IUndoableAction {
        
        private ItemStack stack;
        
        public Hide(ItemStack stack) {
            this.stack = stack;
        }
        
        @Override
        public void apply() {
            if(!JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().isIngredientBlacklisted(stack)) {
                JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(stack);
                shouldReload = true;
            }
        }
        
        @Override
        public boolean canUndo() {
            return true;
        }
        
        @Override
        public void undo() {
            if(JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().isIngredientBlacklisted(stack)) {
                JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().removeIngredientFromBlacklist(stack);
                shouldReload = true;
            }
        }
        
        @Override
        public String describe() {
            return "Hiding item in JEI: " + stack;
        }
        
        @Override
        public String describeUndo() {
            return "Displaying item in JEI: " + stack;
        }
        
        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    protected static class ReloadHandler implements IEventHandler<MineTweakerImplementationAPI.ReloadEvent> {

        @Override
        public void handle(MineTweakerImplementationAPI.ReloadEvent event) {
            if (shouldReload) {
                shouldReload = false;
                ((ItemListOverlay) JEIAddonPlugin.itemListOverlay).rebuildItemFilter();
            }
        }
    }
}
