package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/LocationPredicateBuilder")
@NativeTypeRegistration(value = LocationPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.LocationPredicateBuilder")
public final class ExpandLocationPredicateBuilder {
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder x(final LocationPredicate.Builder internal, final MinMaxBounds.Doubles x) {
        
        return internal.setX(x);
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder y(final LocationPredicate.Builder internal, final MinMaxBounds.Doubles y) {
        
        return internal.setY(y);
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder z(final LocationPredicate.Builder internal, final MinMaxBounds.Doubles z) {
        
        return internal.setZ(z);
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder biome(final LocationPredicate.Builder internal, final ResourceLocation biome) {
        
        return internal.setBiome(ResourceKey.create(Registries.BIOME, biome));
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder biome(final LocationPredicate.Builder internal, final String biome) {
        
        return biome(internal, new ResourceLocation(biome));
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder structure(final LocationPredicate.Builder internal, final ResourceLocation structure) {
        
        return internal.setStructure(ResourceKey.create(Registries.STRUCTURE, structure));
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder structure(final LocationPredicate.Builder internal, final String structure) {
        
        return structure(internal, new ResourceLocation(structure));
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder dimension(final LocationPredicate.Builder internal, final ResourceLocation dimension) {
        
        return internal.setDimension(ResourceKey.create(Registries.DIMENSION, dimension));
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder dimension(final LocationPredicate.Builder internal, final String dimension) {
        
        return dimension(internal, new ResourceLocation(dimension));
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder light(final LocationPredicate.Builder internal, final LightPredicate predicate) {
        
        return internal.setLight(predicate);
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder light(final LocationPredicate.Builder internal, final LightPredicate.Builder predicate) {
        
        return light(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder block(final LocationPredicate.Builder internal, final BlockPredicate predicate) {
        
        return internal.setBlock(predicate);
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder block(final LocationPredicate.Builder internal, final BlockPredicate.Builder predicate) {
        
        return block(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder fluid(final LocationPredicate.Builder internal, final FluidPredicate predicate) {
        
        return internal.setFluid(predicate);
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder fluid(final LocationPredicate.Builder internal, final FluidPredicate.Builder predicate) {
        
        return fluid(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static LocationPredicate.Builder smokey(final LocationPredicate.Builder internal, @ZenCodeType.OptionalBoolean(true) final Boolean smokey) {
        
        return internal.setSmokey(smokey);
    }
    
    @ZenCodeType.Method
    public static LocationPredicate build(final LocationPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
