package com.blamejared.crafttweaker.natives.entity.type.projectile;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/ThrownEnderpearl")
@NativeTypeRegistration(value = ThrownEnderpearl.class, zenCodeName = "crafttweaker.api.entity.type.projectile.ThrownEnderpearl")
public class ExpandThrownEnderpearl {
}
