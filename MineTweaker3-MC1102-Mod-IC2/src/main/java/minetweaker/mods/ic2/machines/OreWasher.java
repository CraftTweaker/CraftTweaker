package minetweaker.mods.ic2.machines;

import ic2.api.recipe.*;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.*;
import minetweaker.mods.ic2.*;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.*;

import static minetweaker.api.minecraft.MineTweakerMC.*;

/**
 * @author Stan Hebben
 */
@ZenClass("mods.ic2.OreWasher")
@ModOnly("IC2")
public class OreWasher {

    @ZenMethod
    public static void addRecipe(IItemStack[] output, IIngredient input, int millibuckets) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("amount", millibuckets);
        MineTweakerAPI.apply(new MachineAddRecipeAction("ore washer", Recipes.oreWashing, getItemStacks(output), nbt, new IC2RecipeInput(input)));
    }

    @ZenMethod
    public static IItemStack[] getOutput(IItemStack input) {
        RecipeOutput output = Recipes.oreWashing.getOutputFor(getItemStack(input), false);
        if(output == null || output.items.isEmpty())
            return null;
        return getIItemStacks(output.items);
    }
}
