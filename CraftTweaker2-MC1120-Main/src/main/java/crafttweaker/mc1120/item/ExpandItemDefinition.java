package crafttweaker.mc1120.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemFood;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.item.IItemDefinition")
@ZenRegister
public class ExpandItemDefinition {
    private static Item getInternal(IItemDefinition expanded) {
        return CraftTweakerMC.getItem(expanded);
    }

    @ZenGetter
    public boolean isArrow(IItemDefinition item) {
        return item instanceof ItemArrow;
    }

    @ZenMethod
    public void setAlwaysEdible(IItemDefinition item) {
        Item food = getInternal(item);
        if (food instanceof ItemFood)
            ((ItemFood)food).setAlwaysEdible();
    }
}