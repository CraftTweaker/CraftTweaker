package crafttweaker.mc1120.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemDefinition;
import net.minecraft.item.ItemArrow;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenExpansion("crafttweaker.item.IItemDefinition")
@ZenRegister
public class ExpandItemDefinition {

    @ZenGetter
    public boolean isArrow(IItemDefinition item) {
        return item instanceof ItemArrow;
    }
}