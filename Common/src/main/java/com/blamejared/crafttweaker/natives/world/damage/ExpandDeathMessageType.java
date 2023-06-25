package com.blamejared.crafttweaker.natives.world.damage;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DeathMessageType;

@ZenRegister
@Document("vanilla/api/world/damage/DeathMessageType")
@NativeTypeRegistration(value = DeathMessageType.class, zenCodeName = "crafttweaker.api.world.damage.DeathMessageType")
@BracketEnum("minecraft:damage/death_message_type")
public class ExpandDeathMessageType {
    

}
