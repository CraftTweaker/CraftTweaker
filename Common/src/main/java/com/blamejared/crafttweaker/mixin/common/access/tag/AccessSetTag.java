package com.blamejared.crafttweaker.mixin.common.access.tag;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.SetTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(SetTag.class)
public interface AccessSetTag {
    
    @Accessor("valuesList")
    <T> ImmutableList<T> crafttweaker$getValuesList();
    
    @Mutable
    @Accessor("valuesList")
    <T> void crafttweaker$setValuesList(ImmutableList<T> valuesList);
    
    @Accessor("values")
    <T> Set<T> crafttweaker$getValues();
    
    @Mutable
    @Accessor("values")
    <T> void crafttweaker$setValues(Set<T> valuesList);
    
    @Mutable
    @Accessor("closestCommonSuperType")
    <T> void crafttweaker$setClosestCommonSuperType(Class<?> valuesList);
    
    @Invoker("<init>")
    static <T> SetTag<T> crafttweaker$init(Set<T> set, Class<?> clazz) {
        
        throw new AssertionError();
    }
    
    @Invoker("findCommonSuperClass")
    static <T> Class<?> crafttweaker$findCommonSuperClass(Set<T> values) {
        
        throw new AssertionError();
    }
    
}
