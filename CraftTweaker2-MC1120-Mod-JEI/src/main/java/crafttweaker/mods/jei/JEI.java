package crafttweaker.mods.jei;

import crafttweaker.*;
import crafttweaker.annotations.*;
import crafttweaker.api.item.*;
import crafttweaker.mc1120.recipes.MCRecipeManager;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.*;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;
import static crafttweaker.mc1120.recipes.MCRecipeManager.recipesToRemove;


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
@ZenRegister
@ModOnly("jei")
public class JEI {
    
    public static List<IAction> LATE_HIDING = new LinkedList<>();
    public static List<IAction> DESCRIPTIONS = new LinkedList<>();
    
    @ZenMethod
    public static void hide(IItemStack stack) {
        LATE_HIDING.add(new Hide(stack));
    }
    
    @ZenMethod
    public static void removeAndHide(IIngredient output, @Optional boolean nbtMatch) {
        recipesToRemove.add(new MCRecipeManager.ActionRemoveRecipesNoIngredients(output, nbtMatch));
        for(IItemStack stack : output.getItems()) {
            LATE_HIDING.add(new Hide(stack));
        }
        
    }
    
    @ZenMethod
    public static void addDescription(IItemStack stack, String description){
    	DESCRIPTIONS.add(new Describe(stack, new String[]{description}));
    }
    
    @ZenMethod
    public static void addDescription(IItemStack stack, String[] description){
    	DESCRIPTIONS.add(new Describe(stack, description));
    }
    
    private static class Hide implements IAction {
        
        private IItemStack stack;
        
        public Hide(IItemStack stack) {
            this.stack = stack;
        }
        
        @Override
        public void apply() {
            if(stack == null){
                CraftTweakerAPI.logError("Cannot hide null item!");
                return;
            }
            JEIAddonPlugin.itemRegistry.removeIngredientsAtRuntime(ItemStack.class, Collections.singletonList(getItemStack(stack)));
        }
        
        @Override
        public String describe() {
            return "Hiding item in JEI: " + stack;
        }
        
    }
    
    private static class Describe implements IAction{
    	private IItemStack stack;
    	private String[] description;
    	
    	public Describe(IItemStack stack, String[] description){
    		this.stack = stack;
    		this.description = description;
    	}

		@Override
		public void apply() {
			if (stack == null){
				CraftTweakerAPI.logError("Cannot describe a null item!");
				return;
			}
			JEIAddonPlugin.modRegistry.addIngredientInfo(getItemStack(stack), ItemStack.class, description);
		}

		@Override
		public String describe() {
			return "Adding description in JEI for: " + stack;
		}
    }
    
}
