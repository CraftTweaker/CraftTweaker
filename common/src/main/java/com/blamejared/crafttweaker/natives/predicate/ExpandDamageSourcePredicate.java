package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/DamageSourcePredicate")
@NativeTypeRegistration(value = DamageSourcePredicate.class, zenCodeName = "crafttweaker.api.predicate.DamageSourcePredicate")
public final class ExpandDamageSourcePredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSourcePredicate.Builder create() {
        
        return DamageSourcePredicate.Builder.damageType();
    }
    
    @ZenCodeType.Method
    public static boolean matches(DamageSourcePredicate internal, ServerPlayer player, DamageSource source) {
        
        return internal.matches(player, source);
    }
    
    @ZenCodeType.Method
    public static boolean matches(DamageSourcePredicate internal, ServerLevel level, Vec3 pos, DamageSource source) {
        
        return internal.matches(level, pos, source);
    }
    
    
}
