package com.blamejared.crafttweaker.impl_native.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Explosion;

@ZenRegister
@Document("vanilla/api/world/ExplosionMode")
@NativeTypeRegistration(value = Explosion.Mode.class, zenCodeName = "crafttweaker.api.world.ExplosionMode")
public class ExpandExplosionMode {

}
