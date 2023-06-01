package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/LocationPredicate")
@NativeTypeRegistration(value = LocationPredicate.class, zenCodeName = "crafttweaker.api.predicate.LocationPredicate")
public final class ExpandLocationPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate any() {
        
        return LocationPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate.Builder create() {
        
        return LocationPredicate.Builder.location();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate at(final MinMaxBounds.Doubles x, final MinMaxBounds.Doubles y, final MinMaxBounds.Doubles z) {
        
        return create().setX(x).setY(y).setZ(z).build();
    }
    
    @SuppressWarnings("SpellCheckingInspection")
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate inBiome(final ResourceLocation biome) {
        
        return LocationPredicate.inBiome(ResourceKey.create(Registries.BIOME, biome));
    }
    
    @SuppressWarnings("SpellCheckingInspection")
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate inBiome(final String biome) {
        
        return inBiome(new ResourceLocation(biome));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate inDimension(final ResourceLocation dimension) {
        
        return LocationPredicate.inDimension(ResourceKey.create(Registries.DIMENSION, dimension));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate inDimension(final String dimension) {
        
        return inDimension(new ResourceLocation(dimension));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate inStructure(final ResourceLocation structure) {
        
        return LocationPredicate.inStructure(ResourceKey.create(Registries.STRUCTURE, structure));
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static LocationPredicate inStructure(final String structure) {
        
        return inStructure(new ResourceLocation(structure));
    }
    
}
