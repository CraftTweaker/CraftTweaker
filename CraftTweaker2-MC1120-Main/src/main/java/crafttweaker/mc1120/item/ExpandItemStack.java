package crafttweaker.mc1120.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.entity.attribute.IEntityAttributeModifier;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.item.IItemStack")
@ZenRegister
public class ExpandItemStack {
    private static ItemStack getInternal(IItemStack expanded) {
        return CraftTweakerMC.getItemStack(expanded);
    }

    @ZenMethod
    @ZenGetter("isBlock")
    public static boolean isBlock(IItemStack value) {
        return getInternal(value).getItem() instanceof ItemBlock;
    }

    @ZenMethod
    public static void addAttributeModifier(IItemStack stack, String attributeName, IEntityAttributeModifier modifier, IEntityEquipmentSlot equipmentSlot) {
        getInternal(stack).addAttributeModifier(attributeName, CraftTweakerMC.getAttributeModifier(modifier), CraftTweakerMC.getEntityEquipmentSlot(equipmentSlot));
    }

    @ZenGetter("maxItemUseDuration")
    @ZenMethod
    public static int getMaxItemUseDuration(IItemStack stack) {
        return getInternal(stack).getMaxItemUseDuration();
    }
}