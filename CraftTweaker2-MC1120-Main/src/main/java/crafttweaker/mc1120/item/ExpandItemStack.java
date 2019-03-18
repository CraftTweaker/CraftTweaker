package crafttweaker.mc1120.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemBlock;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.item.IItemStack")
@ZenRegister
public class ExpandItemStack {
    
    @ZenMethod
    @ZenGetter("isBlock")
    public static boolean isBlock(IItemStack value) {
        return CraftTweakerMC.getItemStack(value).getItem() instanceof ItemBlock;
    }
}

