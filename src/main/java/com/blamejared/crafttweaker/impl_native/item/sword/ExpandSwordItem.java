package com.blamejared.crafttweaker.impl_native.item.sword;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.SwordItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/sword/SwordItem")
@NativeTypeRegistration(value = SwordItem.class, zenCodeName = "crafttweaker.api.item.sword.SwordItem")
public class ExpandSwordItem {
    
    /**
     * Gets the attack damage of this sword.
     *
     * @return the attack damage of this sword.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("attackDamage")
    public static float getAttackDamage(SwordItem internal) {
        
        return internal.getAttackDamage();
    }
    
}
