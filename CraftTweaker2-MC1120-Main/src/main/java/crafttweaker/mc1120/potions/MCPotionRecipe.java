package crafttweaker.mc1120.potions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionType;
import net.minecraft.potion.PotionHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.recipes.IPotionRecipe")
@ZenRegister
public class MCPotionRecipe {

    @ZenMethod
    public static void add(IPotionType input, IIngredient ingredient, IPotionType output) {
        PotionHelper.addMix(CraftTweakerMC.getPotionType(input), CraftTweakerMC.getIngredient(ingredient), CraftTweakerMC.getPotionType(output));
    }
}