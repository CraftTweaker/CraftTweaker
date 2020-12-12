package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeExpansion(EntityType.class)
public class MCEntityType {
    
    @ZenCodeType.Getter("classification")
    public static EntityClassification getClassification(EntityType<?> internal) {
        return internal.getClassification();
    }
    
    
    @ZenCodeType.Getter("serializable")
    public static boolean isSerializable(EntityType<?> internal) {
        return internal.isSerializable();
    }
    
    @ZenCodeType.Getter("summonable")
    public static boolean isSummonable(EntityType<?> internal) {
        return internal.isSummonable();
    }
    
    @ZenCodeType.Getter("immuneToFire")
    public static boolean isImmuneToFire(EntityType<?> internal) {
        return internal.isImmuneToFire();
    }
    
    @ZenCodeType.Getter("translationKey")
    public static String getTranslationKey(EntityType<?> internal) {
        return internal.getTranslationKey();
    }
    
    @ZenCodeType.Getter("name")
    public static String getName(EntityType<?> internal) {
        return internal.getName().getString();
    }
    
    @ZenCodeType.Getter("lootTable")
    public static String getLootTable(EntityType<?> internal) {
        return internal.getLootTable().toString();
    }
    
    @ZenCodeType.Getter("width")
    public static float getWidth(EntityType<?> internal) {
        return internal.getWidth();
    }
    
    @ZenCodeType.Getter("height")
    public static float getHeight(EntityType<?> internal) {
        return internal.getHeight();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EntityType<?> internal) {
        return "<entityType:" + internal.getRegistryName() + ">";
    }
}
