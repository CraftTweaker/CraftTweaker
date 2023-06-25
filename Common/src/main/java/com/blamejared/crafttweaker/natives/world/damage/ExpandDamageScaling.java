package com.blamejared.crafttweaker.natives.world.damage;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageScaling;

@ZenRegister
@Document("vanilla/api/world/damage/DamageScaling")
@NativeTypeRegistration(value = DamageScaling.class, zenCodeName = "crafttweaker.api.world.damage.DamageScaling")
@BracketEnum("minecraft:damage/damage_scaling")
public class ExpandDamageScaling {
    

}
