package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.advancements.critereon.SlimePredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/SlimePredicate")
@NativeTypeRegistration(value = SlimePredicate.class, zenCodeName = "crafttweaker.api.predicate.SlimePredicate")
public final class ExpandSlimePredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static SlimePredicate create(final MinMaxBounds.Ints size) {
        
        return SlimePredicate.sized(size);
    }
    
    @ZenCodeType.Method
    public static boolean matches(SlimePredicate internal, Entity entity, ServerLevel level, @ZenCodeType.Nullable Vec3 pos) {
        
        return internal.matches(entity, level, pos);
    }
    
}
