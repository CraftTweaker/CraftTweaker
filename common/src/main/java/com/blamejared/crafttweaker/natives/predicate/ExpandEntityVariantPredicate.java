package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.EntityVariantPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenRegister
@Document("vanilla/api/predicate/EntityVariantPredicate")
@NativeTypeRegistration(value = EntityVariantPredicate.class, zenCodeName = "crafttweaker.api.predicate.EntityVariantPredicate")
public final class ExpandEntityVariantPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntitySubPredicate catVariant(final ResourceLocation variant) {
        
        return EntitySubPredicate.variant(Objects.requireNonNull(BuiltInRegistries.CAT_VARIANT.get(variant)));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntitySubPredicate catVariant(final String variant) {
        
        return catVariant(new ResourceLocation(variant));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntitySubPredicate frogVariant(final ResourceLocation variant) {
        
        return EntitySubPredicate.variant(Objects.requireNonNull(BuiltInRegistries.FROG_VARIANT.get(variant)));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntitySubPredicate frogVariant(final String variant) {
        
        return frogVariant(new ResourceLocation(variant));
    }
    
}
