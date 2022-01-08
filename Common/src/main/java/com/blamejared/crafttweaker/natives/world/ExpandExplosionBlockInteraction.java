package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Explosion;

@ZenRegister
@Document("vanilla/api/world/ExplosionBlockInteraction")
@NativeTypeRegistration(value = Explosion.BlockInteraction.class, zenCodeName = "crafttweaker.api.world.ExplosionBlockInteraction")
@BracketEnum("minecraft:explosion/blockinteraction")
public class ExpandExplosionBlockInteraction {

}
