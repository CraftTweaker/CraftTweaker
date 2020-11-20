package crafttweaker.mc1120.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.entity.attribute.IEntityAttributeModifier;
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

    @ZenMethod
    public void addAttributeModifier(IItemStack stack, String attributeName, IEntityAttributeModifier modifier, IEntityEquipmentSlot equipmentSlot) {
        CraftTweakerMC.getItemStack(stack).addAttributeModifier(attributeName, CraftTweakerMC.getAttributeModifier(modifier), CraftTweakerMC.getEntityEquipmentSlot(equipmentSlot));
    }

    @ZenGetter("maxItemUseDuration")
    public int getMaxItemUseDuration(IItemStack stack) {
        return CraftTweakerMC.getItemStack(stack).getMaxItemUseDuration();
    }
}