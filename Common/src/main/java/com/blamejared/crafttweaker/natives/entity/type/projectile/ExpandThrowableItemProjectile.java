package com.blamejared.crafttweaker.natives.entity.type.projectile;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/ThrowableItemProjectile")
@NativeTypeRegistration(value = ThrowableItemProjectile.class, zenCodeName = "crafttweaker.api.entity.type.projectile.ThrowableItemProjectile")
public class ExpandThrowableItemProjectile {
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("item")
    public static void setItem(ThrowableItemProjectile internal, ItemStack stack) {
        
        internal.setItem(stack);
    }
    
}
