package crafttweaker.mods.jei;

import crafttweaker.*;
import crafttweaker.annotations.*;
import crafttweaker.api.item.*;
import crafttweaker.mc1120.recipes.MCRecipeManager;
import crafttweaker.mods.jei.Classes.*;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.*;

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
    
    public static List<IAction> LATE_ACTIONS = new LinkedList<>();
    public static List<IAction> DESCRIPTIONS = new LinkedList<>();
    
    @ZenMethod
    public static void hide(IItemStack stack) {
        LATE_ACTIONS.add(new Hide(stack));
    }
    
    @ZenMethod
    public static void removeAndHide(IIngredient output, @Optional boolean nbtMatch) {
        recipesToRemove.add(new MCRecipeManager.ActionRemoveRecipesNoIngredients(output, nbtMatch));
        for(IItemStack stack : output.getItems()) {
            LATE_ACTIONS.add(new Hide(stack));
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
    
    @ZenMethod
    public static void addItem(IItemStack stack){
    	LATE_ACTIONS.add(new AddItem(stack));
    }
    
    
}
