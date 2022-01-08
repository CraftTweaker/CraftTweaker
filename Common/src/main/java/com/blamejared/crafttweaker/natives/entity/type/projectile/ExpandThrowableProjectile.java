package com.blamejared.crafttweaker.natives.entity.type.projectile;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.ThrowableProjectile;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/ThrowableProjectile")
@NativeTypeRegistration(value = ThrowableProjectile.class, zenCodeName = "crafttweaker.api.entity.type.projectile.ThrowableProjectile")
public class ExpandThrowableProjectile {
    
}
