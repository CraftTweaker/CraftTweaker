package com.blamejared.crafttweaker.natives.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/fluid/Fluid")
@NativeTypeRegistration(value = Fluid.class, zenCodeName = "crafttweaker.api.fluid.Fluid")
@TaggableElement("minecraft:fluid")
public class ExpandFluid {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("bucket")
    public static Item getBucket(Fluid internal) {
        
        return internal.getBucket();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public static boolean isSame(Fluid internal, Fluid other) {
        
        return internal.isSame(other);
    }
    
    @ZenCodeType.Method
    public static boolean isIn(Fluid internal, KnownTag<Fluid> tag) {

        return internal.is(tag.getTagKey());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Fluid internal) {
        
        return BuiltInRegistries.FLUID.getKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Fluid internal) {
        
        return "<fluid:" + BuiltInRegistries.FLUID.getKey(internal) + ">.definition";
    }
    
}
