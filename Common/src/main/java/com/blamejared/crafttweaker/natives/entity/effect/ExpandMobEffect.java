package com.blamejared.crafttweaker.natives.entity.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.AttributeModifierTemplate;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/entity/effect/MobEffect")
@NativeTypeRegistration(value = MobEffect.class, zenCodeName = "crafttweaker.api.entity.effect.MobEffect")
@TaggableElement("minecraft:mob_effect")
public class ExpandMobEffect {
    //TODO 1.20.2 check if == works on this
    @ZenCodeType.Method
    public static void applyEffectTick(MobEffect internal, LivingEntity entity, int amplifier) {
        
        internal.applyEffectTick(entity, amplifier);
    }
    
    @ZenCodeType.Method
    public static void applyInstantenousEffect(MobEffect internal, @Nullable Entity source, @Nullable Entity indirectSource, LivingEntity target, int amplifier, double effectiveness) {
        
        internal.applyInstantenousEffect(source, indirectSource, target, amplifier, effectiveness);
    }
    
    @ZenCodeType.Method
    public static boolean shouldApplyEffectTickThisTick(MobEffect internal, int duration, int amplifier) {
        
        return internal.shouldApplyEffectTickThisTick(duration, amplifier);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("instantenous")
    public static boolean isInstantenous(MobEffect internal) {
        
        return internal.isInstantenous();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("descriptionId")
    public static String getDescriptionId(MobEffect internal) {
        
        return internal.getDescriptionId();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(MobEffect internal) {
        
        return internal.getDisplayName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("category")
    public static MobEffectCategory getCategory(MobEffect internal) {
        
        return internal.getCategory();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("color")
    public static int getColor(MobEffect internal) {
        
        return internal.getColor();
    }
    
    @ZenCodeType.Method
    public static MobEffect addAttributeModifier(MobEffect internal, Attribute attribute, String name, double value, AttributeModifier.Operation operation) {
        
        return internal.addAttributeModifier(attribute, name, value, operation);
    }
    
    @ZenCodeType.Method
    public static Map<Attribute, AttributeModifierTemplate> getAttributeModifiers(MobEffect internal) {
        
        return internal.getAttributeModifiers();
    }
    
    //TODO when we have attributemap
    
    //    @ZenCodeType.Method
    //    public static void removeAttributeModifiers(MobEffect internal, LivingEntity entity, AttributeMap attributes, int amplifier) {
    //
    //        internal.removeAttributeModifiers(entity, attributes, amplifier);
    //    }
    //
    //    @ZenCodeType.Method
    //    public static void addAttributeModifiers(MobEffect internal, LivingEntity entity, AttributeMap map, int amplifier) {
    //
    //        internal.addAttributeModifiers(entity, map, amplifier);
    //    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("beneficial")
    public static boolean isBeneficial(MobEffect internal) {
        
        return internal.isBeneficial();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(MobEffect internal) {
        
        return BuiltInRegistries.MOB_EFFECT.getKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(MobEffect internal) {
        
        return "<mobeffect:" + BuiltInRegistries.MOB_EFFECT.getKey(internal) + ">";
    }
    
}
