package com.blamejared.crafttweaker.natives.world.damage;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/damage/DamageType")
@NativeTypeRegistration(value = DamageType.class, zenCodeName = "crafttweaker.api.world.damage.DamageType",
        constructors = {
                @NativeConstructor(value = {
                        @NativeConstructor.ConstructorParameter(name = "msgId", type = String.class, description = "The unlocalized name of this type", examples = "inIce"),
                        @NativeConstructor.ConstructorParameter(name = "scaling", type = DamageScaling.class, description = "The damage scaling used by this type", examples = "<constant:minecraft:damage/damage_scaling:always>"),
                        @NativeConstructor.ConstructorParameter(name = "exhaustion", type = float.class, description = "How much exhaustion does this type cause", examples = "0.1"),
                        @NativeConstructor.ConstructorParameter(name = "effects", type = DamageEffects.class, description = "The effect of this type", examples = "<constant:minecraft:damage/damage_effects:freezing>"),
                        @NativeConstructor.ConstructorParameter(name = "deathMessageType", type = DeathMessageType.class, description = "The type of death message to send", examples = "<constant:minecraft:damage/death_message_type:default>"),
                }, description = "Creates a new DamageType using the provided values"),
                @NativeConstructor(value = {
                        @NativeConstructor.ConstructorParameter(name = "msgId", type = String.class, description = "The unlocalized name of this type", examples = "inIce"),
                        @NativeConstructor.ConstructorParameter(name = "scaling", type = DamageScaling.class, description = "The damage scaling used by this type", examples = "<constant:minecraft:damage/damage_scaling:always>"),
                        @NativeConstructor.ConstructorParameter(name = "exhaustion", type = float.class, description = "How much exhaustion does this type cause", examples = "0.1"),
                }, description = "Creates a new DamageType using the provided values"),
                @NativeConstructor(value = {
                        @NativeConstructor.ConstructorParameter(name = "msgId", type = String.class, description = "The unlocalized name of this type", examples = "inIce"),
                        @NativeConstructor.ConstructorParameter(name = "scaling", type = DamageScaling.class, description = "The damage scaling used by this type", examples = "<constant:minecraft:damage/damage_scaling:always>"),
                        @NativeConstructor.ConstructorParameter(name = "exhaustion", type = float.class, description = "How much exhaustion does this type cause", examples = "0.1"),
                        @NativeConstructor.ConstructorParameter(name = "effects", type = DamageEffects.class, description = "The effect of this type", examples = "<constant:minecraft:damage/damage_effects:freezing>"),
                }, description = "Creates a new DamageType using the provided values"),
                @NativeConstructor(value = {
                        @NativeConstructor.ConstructorParameter(name = "msgId", type = String.class, description = "The unlocalized name of this type", examples = "inIce"),
                        @NativeConstructor.ConstructorParameter(name = "exhaustion", type = float.class, description = "How much exhaustion does this type cause", examples = "0.1"),
                        @NativeConstructor.ConstructorParameter(name = "effects", type = DamageEffects.class, description = "The effect of this type", examples = "<constant:minecraft:damage/damage_effects:freezing>"),
                }, description = "Creates a new DamageType using the provided values"),
                @NativeConstructor(value = {
                        @NativeConstructor.ConstructorParameter(name = "msgId", type = String.class, description = "The unlocalized name of this type", examples = "inIce"),
                        @NativeConstructor.ConstructorParameter(name = "exhaustion", type = float.class, description = "How much exhaustion does this type cause", examples = "0.1"),
                }, description = "Creates a new DamageType using the provided values"),
        })
@TaggableElement("minecraft:damage_type")
public class ExpandDamageType {
    
    @ZenCodeType.Getter("msgId")
    public static String msgId(DamageType internal) {
        
        return internal.msgId();
    }
    
    @ZenCodeType.Getter("scaling")
    public static DamageScaling scaling(DamageType internal) {
        
        return internal.scaling();
    }
    
    @ZenCodeType.Getter("exhaustion")
    public static float exhaustion(DamageType internal) {
        
        return internal.exhaustion();
    }
    
    @ZenCodeType.Getter("effects")
    public static DamageEffects effects(DamageType internal) {
        
        return internal.effects();
    }
    
    @ZenCodeType.Getter("deathMessageType")
    public static DeathMessageType deathMessageType(DamageType internal) {
        
        return internal.deathMessageType();
    }
    
}
