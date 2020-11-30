package com.blamejared.crafttweaker.impl_native.loot;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/MCLootContext")
@NativeTypeRegistration(value = LootContext.class, zenCodeName = "crafttweaker.api.loot.MCLootContext")
public class ExpandLootContext {

    @ZenCodeType.Getter("thisEntity")
    @ZenCodeType.Nullable
    public static Entity getThisEntity(final LootContext $this) {
        return $this.get(LootParameters.THIS_ENTITY);
    }

    @ZenCodeType.Getter("lastDamagePlayer")
    @ZenCodeType.Nullable
    public static PlayerEntity getLastDamagePlayer(final LootContext $this) {
        return $this.get(LootParameters.LAST_DAMAGE_PLAYER);
    }

    @ZenCodeType.Getter("damageSource")
    @ZenCodeType.Nullable
    public static DamageSource getDamageSource(final LootContext $this) {
        return $this.get(LootParameters.DAMAGE_SOURCE);
    }

    @ZenCodeType.Getter("killerEntity")
    @ZenCodeType.Nullable
    public static Entity getKillerEntity(final LootContext $this) {
        return $this.get(LootParameters.KILLER_ENTITY);
    }

    @ZenCodeType.Getter("directKillerEntity")
    @ZenCodeType.Nullable
    public static Entity getDirectKillerEntity(final LootContext $this) {
        return $this.get(LootParameters.DIRECT_KILLER_ENTITY);
    }

    @ZenCodeType.Getter("origin")
    @ZenCodeType.Nullable
    public static Vector3d getOrigin(final LootContext $this) {
        return $this.get(LootParameters.field_237457_g_);
    }

    @ZenCodeType.Getter("blockState")
    @ZenCodeType.Nullable
    public static BlockState getBlockState(final LootContext $this) {
        return $this.get(LootParameters.BLOCK_STATE);
    }

    @ZenCodeType.Getter("tileEntity")
    @ZenCodeType.Nullable
    public static TileEntity getTileEntity(final LootContext $this) {
        return $this.get(LootParameters.BLOCK_ENTITY);
    }

    @ZenCodeType.Getter("tool")
    public static IItemStack getTool(final LootContext $this) {
        final ItemStack nativeTool = $this.get(LootParameters.TOOL);
        return nativeTool == null || nativeTool.isEmpty()? MCItemStack.EMPTY.get() : new MCItemStack(nativeTool);
    }

    @ZenCodeType.Getter("explosionRadius")
    public static float getExplosionRadius(final LootContext $this) {
        final Float nativeRadius = $this.get(LootParameters.EXPLOSION_RADIUS);
        return nativeRadius == null? 0.0F : nativeRadius;
    }

    @ZenCodeType.Getter("lootingModifier")
    public static int getLootingModifier(final LootContext $this) {
        return $this.getLootingModifier();
    }

    @ZenCodeType.Getter("world")
    @ZenCodeType.Nullable
    public static World getWorld(final LootContext $this) {
        return $this.getWorld();
    }

    @ZenCodeType.Getter("luck")
    public static float getLuck(final LootContext $this) {
        return $this.getLuck();
    }

    @ZenCodeType.Getter("lootTableId")
    public static ResourceLocation getLootTableId(final LootContext $this) {
        // TODO("Pending Forge PR")
        return null;
    }
}
