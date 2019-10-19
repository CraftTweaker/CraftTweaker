package minetweaker.mods.ic2.machines;

import ic2.api.recipe.Recipes;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.mods.ic2.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("mods.ic2.MatterAmplifier")
@ModOnly("IC2")
public class MatterAmplifier {
    
    private MatterAmplifier() {
    }
    
    @ZenMethod
    public static void setAmplifier(IIngredient item, int amplifier) {
        NBTTagCompound data = new NBTTagCompound();
        data.setInteger("amplification", amplifier);
        MineTweakerAPI.apply(new MachineAddRecipeAction("matter amplifier", Recipes.matterAmplifier, new ItemStack[0], data, new IC2RecipeInput(item)));
    }
}
