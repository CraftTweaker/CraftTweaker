package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EntityTypePredicate")
@NativeTypeRegistration(value = EntityTypePredicate.class, zenCodeName = "crafttweaker.api.predicate.EntityTypePredicate")
public final class ExpandEntityTypePredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityTypePredicate any() {
        
        return EntityTypePredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityTypePredicate create(final EntityType<?> type) {
        
        return EntityTypePredicate.of(type);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityTypePredicate create(final MCTag<EntityType<?>> type) {
        
        return EntityTypePredicate.of(type.getInternal());
    }
    
}
