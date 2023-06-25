package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.TagPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/TagPredicate")
@NativeTypeRegistration(value = TagPredicate.class, zenCodeName = "crafttweaker.api.predicate.TagPredicate")
public final class ExpandTagPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static <T> TagPredicate<T> isIn(Class<T> tClass, MCTag tag) {
        
        return TagPredicate.is(tag.getTagKey());
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static <T> TagPredicate<T> isNotIn(Class<T> tClass, MCTag tag) {
        
        return TagPredicate.isNot(tag.getTagKey());
    }
    
}
