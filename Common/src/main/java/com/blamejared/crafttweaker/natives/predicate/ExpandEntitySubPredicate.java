package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EntitySubPredicate")
@NativeTypeRegistration(value = EntitySubPredicate.class, zenCodeName = "crafttweaker.api.predicate.EntitySubPredicate")
public final class ExpandEntitySubPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static EntitySubPredicate any() {
        
        return EntitySubPredicate.ANY;
    }
    
    
    @ZenCodeType.Method
    public static boolean matches(EntitySubPredicate internal, Entity entity, ServerLevel level, @ZenCodeType.Nullable Vec3 pos) {
        
        return internal.matches(entity, level, pos);
    }
}
