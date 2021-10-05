package com.blamejared.crafttweaker.impl_native.item.tiered;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.IItemTier;
import net.minecraft.item.TieredItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/tiered/TieredItem")
@NativeTypeRegistration(value = TieredItem.class, zenCodeName = "crafttweaker.api.item.tiered.TieredItem")
public class ExpandTieredItem {
    
    /**
     * Gets the tier of this item.
     *
     * @return The IItemTier of this item.
     */
    @ZenCodeType.Method
    public static IItemTier getTier(TieredItem internal) {
        
        return internal.getTier();
    }
    
}
