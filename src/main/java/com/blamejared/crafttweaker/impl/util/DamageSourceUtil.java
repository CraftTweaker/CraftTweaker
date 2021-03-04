package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The class has some static methods to create some specific damage sources.
 */
@ZenRegister
@Document("vanilla/api/util/DamageSourceUtil")
@ZenCodeType.Name("crafttweaker.api.util.DamageSourceUtil")
public class DamageSourceUtil {
    @ZenCodeType.Method
    public static DamageSource causeBeeStingDamage(LivingEntity bee) {
        return DamageSource.causeBeeStingDamage(bee);
    }
    
    @ZenCodeType.Method
    public static DamageSource causeMobDamage(LivingEntity mob) {
        return DamageSource.causeMobDamage(mob);
    }
    
    @ZenCodeType.Method
    public static DamageSource causeIndirectDamage(Entity source, LivingEntity indirectEntityIn) {
        return DamageSource.causeIndirectDamage(source, indirectEntityIn);
    }
    
    @ZenCodeType.Method
    public static DamageSource causePlayerDamage(PlayerEntity player) {
        return DamageSource.causePlayerDamage(player);
    }
}
