package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.natives.item.ExpandItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/LootContext")
@NativeTypeRegistration(value = LootContext.class, zenCodeName = "crafttweaker.api.loot.LootContext")
public final class ExpandLootContext {
    
    @ZenCodeType.Getter("thisEntity")
    @ZenCodeType.Nullable
    public static Entity getThisEntity(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.THIS_ENTITY);
    }
    
    @ZenCodeType.Getter("lastDamagePlayer")
    @ZenCodeType.Nullable
    public static Player getLastDamagePlayer(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.LAST_DAMAGE_PLAYER);
    }
    
    @ZenCodeType.Getter("damageSource")
    @ZenCodeType.Nullable
    public static DamageSource getDamageSource(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
    }
    
    @ZenCodeType.Getter("killerEntity")
    @ZenCodeType.Nullable
    public static Entity getKillerEntity(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.KILLER_ENTITY);
    }
    
    @ZenCodeType.Getter("directKillerEntity")
    @ZenCodeType.Nullable
    public static Entity getDirectKillerEntity(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.DIRECT_KILLER_ENTITY);
    }
    
    @ZenCodeType.Getter("origin")
    @ZenCodeType.Nullable
    public static Vec3 getOrigin(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.ORIGIN);
    }
    
    @ZenCodeType.Getter("blockState")
    @ZenCodeType.Nullable
    public static BlockState getBlockState(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.BLOCK_STATE);
    }
    
    @ZenCodeType.Getter("blockEntity")
    @ZenCodeType.Nullable
    public static BlockEntity getTileEntity(final LootContext internal) {
        
        return internal.getParamOrNull(LootContextParams.BLOCK_ENTITY);
    }
    
    @ZenCodeType.Getter("tool")
    public static IItemStack getTool(final LootContext internal) {
        
        final ItemStack nativeTool = internal.getParamOrNull(LootContextParams.TOOL);
        return ExpandItemStack.asIItemStack(nativeTool == null ? ItemStack.EMPTY : nativeTool);
    }
    
    @ZenCodeType.Getter("explosionRadius")
    public static float getExplosionRadius(final LootContext internal) {
        
        final Float nativeRadius = internal.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
        return nativeRadius == null ? 0.0F : nativeRadius;
    }
    
    @ZenCodeType.Getter("level")
    public static ServerLevel getWorld(final LootContext internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("luck")
    public static float getLuck(final LootContext internal) {
        
        return internal.getLuck();
    }
    
    @ZenCodeType.Getter("random")
    public static RandomSource getRandom(final LootContext internal) {
        
        return internal.getRandom();
    }
    
}
