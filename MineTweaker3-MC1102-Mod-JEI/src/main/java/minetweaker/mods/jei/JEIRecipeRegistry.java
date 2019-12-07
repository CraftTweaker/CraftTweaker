package minetweaker.mods.jei;

import mezz.jei.IngredientInformation;
import mezz.jei.api.*;
import mezz.jei.api.recipe.*;
import mezz.jei.gui.ItemListOverlay;
import mezz.jei.plugins.vanilla.furnace.FuelRecipe;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.compat.IJEIRecipeRegistry;
import minetweaker.api.item.IIngredient;
import minetweaker.mc1102.util.MineTweakerHacks;
import minetweaker.util.IEventHandler;
import net.minecraft.item.ItemStack;

import java.util.*;

public class JEIRecipeRegistry implements IJEIRecipeRegistry {
    
    private IRecipeRegistry recipeRegistry;
    private IJeiHelpers jeiHelpers;
    private boolean shouldReloadItemList = false;

    private static Map<Object, String> TOOLTIP_CACHE;

    public JEIRecipeRegistry(IRecipeRegistry recipeRegistry, IJeiHelpers jeiHelpers) {
        this.recipeRegistry = recipeRegistry;
        this.jeiHelpers = jeiHelpers;
        MineTweakerImplementationAPI.onPostReload(new ReloadHandler());
    }
    
    @Override
    public void addRecipe(Object object) {
        recipeRegistry.addRecipe(object);
    }
    
    @Deprecated
    @Override
    public void addRecipe(Object object, String category) {
        recipeRegistry.addRecipe(object);
    }
    
    @Override
    public void removeRecipe(Object object) {
        recipeRegistry.removeRecipe(object);
    }
    
    @Deprecated
    @Override
    public void removeRecipe(Object output, String category) {
        recipeRegistry.removeRecipe(output);
    }
    
    @Override
    public void addFurnace(List<Object> input, Object output) {
        List<ItemStack> inputs = new ArrayList<>();
        input.forEach(in -> {
            inputs.add((ItemStack) in);
        });
        recipeRegistry.addSmeltingRecipe(inputs, (ItemStack) output);
    }
    
    
    @Override
    public void removeFurnace(Object object) {
        IFocus<ItemStack> focus = recipeRegistry.createFocus(IFocus.Mode.INPUT, (ItemStack) object);
        List<IRecipeCategory> categories = recipeRegistry.getRecipeCategories(focus);
        for(IRecipeCategory category : categories) {
            if(category.getUid().equals(VanillaRecipeCategoryUid.SMELTING)) {
                List<IRecipeWrapper> wrappers = recipeRegistry.getRecipeWrappers(category, focus);
                for(IRecipeWrapper wrapper : wrappers) {
                    recipeRegistry.removeRecipe(wrapper);
                }
            }
        }
    }

    public void addFuel(Collection<Object> input, int burnTime){
        List<ItemStack> inputs = new ArrayList<>();
        input.forEach(in -> {
            inputs.add((ItemStack) in);
        });
        recipeRegistry.addRecipe(new FuelRecipe(jeiHelpers.getGuiHelper(), inputs, burnTime));
    }
    
    public void removeFuel(Object object){
        IFocus<ItemStack> focus = recipeRegistry.createFocus(IFocus.Mode.INPUT, (ItemStack) object);
        List<IRecipeCategory> categories = recipeRegistry.getRecipeCategories(focus);
        for(IRecipeCategory category : categories) {
            if(category.getUid().equals(VanillaRecipeCategoryUid.FUEL)) {
                List<IRecipeWrapper> wrappers = recipeRegistry.getRecipeWrappers(category, focus);
                for(IRecipeWrapper wrapper : wrappers) {
                    recipeRegistry.removeRecipe(wrapper);
                }
            }
        }
    }

    @Override
    public void reloadItemList() {
        shouldReloadItemList = true;
    }

    //Unused in 1.10.2
    @Override
    public void removeItem(Object stack) {}

    //Unused in 1.10.2
    @Override
    public void addItem(Object stack) {}

    @Override
    public void invalidateTooltips(IIngredient unused) {
        if (TOOLTIP_CACHE == null)
            TOOLTIP_CACHE = MineTweakerHacks.getPrivateStaticObject(IngredientInformation.class, "tooltipCache");

        if (TOOLTIP_CACHE != null) {
            TOOLTIP_CACHE.clear();
            reloadItemList();
        }
    }

    private class ReloadHandler implements IEventHandler<MineTweakerImplementationAPI.ReloadEvent> {

        @Override
        public void handle(MineTweakerImplementationAPI.ReloadEvent event) {
            if (shouldReloadItemList) {
                shouldReloadItemList = false;
                ((ItemListOverlay) JEIAddonPlugin.itemListOverlay).rebuildItemFilter();
            }
        }
    }
}
