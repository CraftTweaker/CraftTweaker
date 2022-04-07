package com.blamejared.crafttweaker.natives.entity.type.projectile.fireball;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/Fireball")
@NativeTypeRegistration(value = Fireball.class, zenCodeName = "crafttweaker.api.entity.type.projectile.Fireball")
public class ExpandFireball {
    
    /**
     * Sets the render itemstack of this fireball.
     *
     * @param stack The render itemstack of this fireball.
     *
     * @docParam stack <item:minecraft:diamond>
     */
    public static void setItem(Fireball internal, ItemStack stack) {
        
        internal.setItem(stack);
    }
    
}
