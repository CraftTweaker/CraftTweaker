package com.blamejared.crafttweaker.natives.item.type.projectileweapon;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.BowItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/projectileweapon/BowItem")
@NativeTypeRegistration(value = BowItem.class, zenCodeName = "crafttweaker.api.item.type.projectileweapon.BowItem")
public class ExpandBowItem {
    
    /**
     * Converts the given time to a power value between 0 and 1
     *
     * @param charge The charge to convert
     *
     * @return the given time as a power value between 0 and 1
     *
     * @docParam charge 5
     */
    @ZenCodeType.StaticExpansionMethod
    public static float getPowerForTime(int charge) {
        
        return BowItem.getPowerForTime(charge);
    }
    
}
