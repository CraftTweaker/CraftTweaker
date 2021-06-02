package crafttweaker.mods.jei;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.recipes.MCRecipeManager;
import crafttweaker.mods.jei.actions.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;
import java.util.stream.Collectors;

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
    
    public static List<IAction> LATE_ACTIONS_PRE = new LinkedList<>();
    public static List<IAction> LATE_ACTIONS_POST = new LinkedList<>();
    
    public static List<ItemStack> HIDDEN_ITEMS = new LinkedList<>();
    public static Set<String> HIDDEN_CATEGORIES = new HashSet<>();
    public static List<FluidStack> HIDDEN_LIQUIDS = new LinkedList<>();
    
    public static List<IAction> DESCRIPTIONS = new LinkedList<>();
    
    
    @ZenMethod
    public static void hideCategory(String name) {
        if(name == null || name.isEmpty()) {
            CraftTweakerAPI.logError("Unable to hide null or empty name! " + name);
            return;
        }
        LATE_ACTIONS_PRE.add(new HideCategoryAction(name));
    }
    
    
    @ZenMethod
    public static void hide(IItemStack stack) {
        if(stack == null) {
            CraftTweakerAPI.logError("Unable to hide null itemstack! " + stack);
            return;
        }
        LATE_ACTIONS_PRE.add(new HideAction(stack));
    }
    
    @ZenMethod
    public static void hide(ILiquidStack stack) {
        if(stack == null) {
            CraftTweakerAPI.logError("Unable to hide null fluidstack! " + stack);
            return;
        }
        LATE_ACTIONS_PRE.add(new HideFluidAction(stack));
    }
    
    @ZenMethod
    public static void removeAndHide(IIngredient output, @Optional boolean nbtMatch) {
        MCRecipeManager.actionRemoveRecipesNoIngredients.addOutput(output, nbtMatch);
        for(IItemStack stack : output.getItems()) {
            if(stack == null) {
                CraftTweakerAPI.logError("Unable to hide null itemstack! " + stack + ", from:" + output);
                continue;
            }
            LATE_ACTIONS_PRE.add(new HideAction(stack));
        }
        
        
    }
    
    @Deprecated
    public static void addDescription(IItemStack stack, String... description) {
        addDescription(((IIngredient) stack), description);
    }
    
    
    @Deprecated
    public static void addDescription(IItemStack[] stack, String... description) {
        addDescription(((IIngredient[]) stack), description);
    }
    
    
    @Deprecated
    public static void addDescription(IOreDictEntry dict, String... description) {
        addDescription(((IIngredient) dict), description);
    }
    
    
    @Deprecated
    public static void addDescription(ILiquidStack stack, String... description) {
        addDescription(((IIngredient) stack), description);
    }

    @ZenMethod
    public static void addDescription(IIngredient ingredient, String... description) {
        DESCRIPTIONS.add(new DescribeAction(Collections.singletonList(ingredient), description, ingredient.toCommandString()));
    }

    @ZenMethod
    public static void addDescription(IIngredient[] ingredients, String... description) {
        DESCRIPTIONS.add(new DescribeAction(Arrays.asList(ingredients), description, Arrays.stream(ingredients).map(IIngredient::toCommandString).collect(Collectors.joining(", ", "[", "]"))));
    }
    
    
    @ZenMethod
    public static void addItem(IItemStack stack) {
        LATE_ACTIONS_POST.add(new AddItemAction(stack));
    }
    
}
