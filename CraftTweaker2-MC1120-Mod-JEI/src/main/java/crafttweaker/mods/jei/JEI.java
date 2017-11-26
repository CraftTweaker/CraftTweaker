package crafttweaker.mods.jei;

import crafttweaker.*;
import crafttweaker.annotations.*;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.recipes.MCRecipeManager;
import crafttweaker.mods.jei.actions.*;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.*;

import java.util.*;

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
        LATE_ACTIONS.add(new HideAction(stack));
    }


    @ZenMethod
    public static void removeAndHide(IIngredient output, @Optional boolean nbtMatch) {
        MCRecipeManager.actionRemoveRecipesNoIngredients.addOutput(output, nbtMatch);
        for(IItemStack stack : output.getItems()) {
            LATE_ACTIONS.add(new HideAction(stack));
        }

    }

    @ZenMethod
    public static void addDescription(IItemStack stack, String... description) {
        DESCRIPTIONS.add(new DescribeAction(Collections.singletonList(stack), description, stack.toString()));
    }


    @ZenMethod
    public static void addDescription(IItemStack[] stack, String... description) {
        DESCRIPTIONS.add(new DescribeAction(Arrays.asList(stack), description, "IItemStack[]"));
    }


    @ZenMethod
    public static void addDescription(IOreDictEntry dict, String... description) {
        DESCRIPTIONS.add(new DescribeAction(dict.getItems(), description, dict.toString()));
    }


    @ZenMethod
    public static void addDescription(ILiquidStack stack, String... description) {
        DESCRIPTIONS.add(new DescribeAction(Collections.singletonList(stack.withAmount(Fluid.BUCKET_VOLUME)), description, stack.toString()));
    }


    @ZenMethod
    public static void addItem(IItemStack stack) {
        LATE_ACTIONS.add(new AddItemAction(stack));
    }

}
