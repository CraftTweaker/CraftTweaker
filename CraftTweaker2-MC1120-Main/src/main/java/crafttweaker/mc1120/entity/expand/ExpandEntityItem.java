package crafttweaker.mc1120.entity.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenExpansion("crafttweaker.entity.IEntityItem")
@ZenRegister
public class ExpandEntityItem {

    @ZenSetter("item")
    @ZenMethod
    public void setItem(IEntityItem itemEntity, IItemStack stack) {
        CraftTweakerMC.getEntityItem(itemEntity).setItem(CraftTweakerMC.getItemStack(stack));
    }
}
