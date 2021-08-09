package crafttweaker.mc1120.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.dispenser.IDispenserBehavior;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.actions.ActionAddDispenserBehavior;
import crafttweaker.mc1120.actions.ActionRemoveDispenserBehavior;
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
    @ZenMethod
    public static boolean isArrow(IItemDefinition item) {
        return item instanceof ItemArrow;
    }

    @ZenMethod
    public static void setAlwaysEdible(IItemDefinition item) {
        Item food = getInternal(item);
        if (food instanceof ItemFood)
            ((ItemFood)food).setAlwaysEdible();
    }

    @ZenMethod
    public static void removeDispenserBehavior(IItemDefinition item) {
        CraftTweaker.LATE_ACTIONS.add(new ActionRemoveDispenserBehavior(item));
    }

    @ZenMethod
    public static void addDispenserBehavior(IItemDefinition item, IDispenserBehavior behavior) {
        CraftTweaker.LATE_ACTIONS.add(new ActionAddDispenserBehavior(item, behavior));
    }
}