package com.blamejared.ctgui.api;

import minetweaker.mc1102.data.NBTConverter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jared.
 */
public class SlotRecipe extends Slot {

    //key is the operation, value is the values, for more than 1 value, seperate using §
    private Map<String, Boolean> propertyMap = new HashMap<>();
    private Map<String, Integer> properties = new HashMap<>();


    public SlotRecipe(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
        super.onPickupFromSlot(playerIn, stack);
        this.propertyMap.clear();
        this.properties.clear();
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return true;
    }

    public String getItemString() {
        boolean matchAny = getPropertyFromMap("matchNotEmpty");
        boolean metaWildcard = getPropertyFromMap("anyMetadata");
        boolean oreDict = getPropertyFromMap("oreDictionary");
        if (getStack() == null) return "null";
        String stackName = Item.REGISTRY.getNameForObject(getStack().getItem()).toString();
        StringBuilder builder = new StringBuilder("<");
        if (oreDict) {
            int[] ids = OreDictionary.getOreIDs(getStack());
            if (ids.length != 0) {
                stackName = "ore:" + OreDictionary.getOreName(ids[0]);
            } else oreDict = false;
        }
        if (matchAny) {
            stackName = "*";
        }
        builder.append(stackName);
        if (!oreDict && !matchAny && (metaWildcard || getStack().getItemDamage() != 0))
            builder.append(':').append(metaWildcard || getStack().getItemDamage() == OreDictionary.WILDCARD_VALUE ? "*" : getStack().getItemDamage());
        builder.append('>');
        if (getStack().stackSize > 1) builder.append(" * ").append(getStack().stackSize);
        if (getPropertyFromMap("anyDamage")) builder.append(".anyDamage()");
        if (getPropertyFromMap("onlyDamage")) builder.append(".onlyDamaged()");
        if (getPropertyFromMap("gted"))
            builder.append(".onlyDamageAtLeast(").append("").append(getProperty("gted")).append(')');
        if (getPropertyFromMap("ltd"))
            builder.append(".onlyDamageAtMost(").append("").append(getProperty("ltd")).append(')');
        if (getPropertyFromMap("betweenDamage")) {
            builder.append(".onlyDamageBetween(").append("").append(getProperty("betweenDamageX")).append(", ").append("").append(getProperty("betweenDamageY")).append(')');
        }
        if (getPropertyFromMap("reuse")) builder.append(".reuse()");
        if (getPropertyFromMap("noreturn")) builder.append(".noReturn()");
        if (getPropertyFromMap("nbt")) {
            if (this instanceof SlotRecipeOutput)
                builder.append(String.format(".withTag(%s)", NBTConverter.from(getStack().getTagCompound(), false).toString()));
            else
                builder.append(String.format(".onlyWithTag(%s)", NBTConverter.from(getStack().getTagCompound(), false).toString()));
        }
        return builder.toString();
    }

    boolean getPropertyFromMap(String key) {
        if (propertyMap.containsKey(key)) {
            return propertyMap.get(key);
        }
        return false;
    }

    int getProperty(String key) {
        if (properties.containsKey(key)) {
            return properties.get(key);
        }
        return 0;
    }

    public Map<String, Boolean> getPropertyMap() {
        return propertyMap;
    }

    public Map<String, Integer> getProperties() {
        return properties;
    }
}
