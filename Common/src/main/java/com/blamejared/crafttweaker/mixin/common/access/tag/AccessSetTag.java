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
    <T> ImmutableList<T> getValuesList();
    
    @Mutable
    @Accessor("valuesList")
    <T> void setValuesList(ImmutableList<T> valuesList);
    
    @Accessor("values")
    <T> Set<T> getValues();
    
    @Mutable
    @Accessor("values")
    <T> void setValues(Set<T> valuesList);
    
    @Mutable
    @Accessor("closestCommonSuperType")
    <T> void setClosestCommonSuperType(Class<?> valuesList);
    
    @Invoker("<init>")
    static <T> SetTag<T> init(Set<T> set, Class<?> clazz) {
        
        throw new AssertionError();
    }
    
    
    @Invoker("findCommonSuperClass")
    static <T> Class<?> findCommonSuperClass(Set<T> values) {
        
        throw new AssertionError();
    }
    
}
