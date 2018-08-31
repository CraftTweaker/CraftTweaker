package com.blamejared.ctgui.api;

import crafttweaker.api.data.IData;
import crafttweaker.mc1120.data.NBTConverter;
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
    public ItemStack onTake(EntityPlayer playerIn, ItemStack stack) {
        this.propertyMap.clear();
        this.properties.clear();
        return super.onTake(playerIn, stack);
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
        if(getStack().isEmpty())
            return "null";
        String stackName = Item.REGISTRY.getNameForObject(getStack().getItem()).toString();
        StringBuilder builder = new StringBuilder("<");
        if(oreDict) {
            int[] ids = OreDictionary.getOreIDs(getStack());
            if(ids.length != 0) {
                stackName = "ore:" + OreDictionary.getOreName(ids[0]);
            } else
                oreDict = false;
        }
        if(matchAny) {
            stackName = "*";
        }
        builder.append(stackName);
        if(!oreDict && !matchAny && (metaWildcard || getStack().getItemDamage() != 0))
            builder.append(':').append(metaWildcard || getStack().getItemDamage() == OreDictionary.WILDCARD_VALUE ? "*" : getStack().getItemDamage());
        builder.append('>');
        if(getStack().getCount() > 1)
            builder.append(" * ").append(getStack().getCount());
        if(getPropertyFromMap("anyDamage"))
            builder.append(".anyDamage()");
        if(getPropertyFromMap("onlyDamage"))
            builder.append(".onlyDamaged()");
        if(getPropertyFromMap("gted"))
            builder.append(".onlyDamageAtLeast(").append(getProperty("gted")).append(')');
        if(getPropertyFromMap("ltd"))
            builder.append(".onlyDamageAtMost(").append(getProperty("ltd")).append(')');
        if(getPropertyFromMap("betweenDamage")) {
            builder.append(".onlyDamageBetween(").append(getProperty("betweenDamageX")).append(", ").append(getProperty("betweenDamageY")).append(')');
        }
        if(getPropertyFromMap("noreturn"))
            builder.append(".noReturn()");
        if(getPropertyFromMap("nbt")) {
            final IData data = NBTConverter.from(getStack().getTagCompound(), false);
            if(data != null) {
                builder.append(String.format(".withTag(%s)", data.toString()));
                if(!(this instanceof SlotRecipeOutput))
                    builder.append(String.format(".onlyWithTag(%s)", data.toString()));
            }
        }
        return builder.toString();
    }
    
    boolean getPropertyFromMap(String key) {
        if(propertyMap.containsKey(key)) {
            return propertyMap.get(key);
        }
        return false;
    }
    
    int getProperty(String key) {
        if(properties.containsKey(key)) {
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
