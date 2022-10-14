package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EntityPredicate")
@NativeTypeRegistration(value = EntityPredicate.class, zenCodeName = "crafttweaker.api.predicate.EntityPredicate")
public final class ExpandEntityPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityPredicate any() {
        
        return EntityPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityPredicate.Builder create() {
        
        return EntityPredicate.Builder.entity();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityPredicate.Builder create(final EntityType<?> entityType) {
        
        return create().of(entityType);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static EntityPredicate.Builder create(final KnownTag<EntityType<?>> entityTag) {

        return create().of(entityTag.getTagKey());
    }
    
}
