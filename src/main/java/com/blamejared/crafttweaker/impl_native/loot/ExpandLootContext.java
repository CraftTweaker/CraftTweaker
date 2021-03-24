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

/**
 * Holds all information that may be obtainable from a loot table roll, allowing for identifying key information.
 *
 * Not all parameters are present at all times, for obvious reasons. For example, information related to an entity will
 * not be available if the loot table being rolled is the one for a block.
 */
@ZenRegister
@Document("vanilla/api/loot/LootContext")
@NativeTypeRegistration(value = LootContext.class, zenCodeName = "crafttweaker.api.loot.LootContext")
public class ExpandLootContext {
    /**
     * Gets the current entity, if present; null otherwise.
     *
     * @param internal The context.
     * @return The entity that owns the loot table.
     */
    @ZenCodeType.Getter("thisEntity")
    @ZenCodeType.Nullable
    public static Entity getThisEntity(final LootContext internal) {
        return internal.get(LootParameters.THIS_ENTITY);
    }

    /**
     * Gets the last player that damaged the current entity, if present; null otherwise.
     *
     * @param internal The context.
     * @return The last player that caused damage to the entity.
     */
    @ZenCodeType.Getter("lastDamagePlayer")
    @ZenCodeType.Nullable
    public static PlayerEntity getLastDamagePlayer(final LootContext internal) {
        return internal.get(LootParameters.LAST_DAMAGE_PLAYER);
    }

    /**
     * Gets the damage source that caused the death of the current entity, if present; null otherwise.
     *
     * @param internal The context.
     * @return The damage source that caused the death of the entity.
     */
    @ZenCodeType.Getter("damageSource")
    @ZenCodeType.Nullable
    public static DamageSource getDamageSource(final LootContext internal) {
        return internal.get(LootParameters.DAMAGE_SOURCE);
    }

    /**
     * Gets the entity that caused the death of the current entity, if present; null otherwise.
     *
     * The entity may or may not be a player. To get only the player, refer to <code>lastDamagePlayer</code>.
     *
     * @param internal The context.
     * @return The entity that killed the current entity.
     */
    @ZenCodeType.Getter("killerEntity")
    @ZenCodeType.Nullable
    public static Entity getKillerEntity(final LootContext internal) {
        return internal.get(LootParameters.KILLER_ENTITY);
    }

    /**
     * Gets the entity that effectively killed the current entity, if present; null otherwise.
     *
     * The difference between this and <code>killerEntity</code> resides on the direct-ness of the entity. For example,
     * if a player kills a skeleton with an arrow, the player will be the <code>killerEntity</code>, while the arrow
     * will be the <code>directKillerEntity</code>.
     *
     * @param internal The context.
     * @return The entity that effectively killed the entity.
     */
    @ZenCodeType.Getter("directKillerEntity")
    @ZenCodeType.Nullable
    public static Entity getDirectKillerEntity(final LootContext internal) {
        return internal.get(LootParameters.DIRECT_KILLER_ENTITY);
    }

    /**
     * Gets the origin, or location, of the loot roll, if present; null otherwise.
     *
     * @param internal The context.
     * @return The origin of the roll.
     */
    @ZenCodeType.Getter("origin")
    @ZenCodeType.Nullable
    public static Vector3d getOrigin(final LootContext internal) {
        return internal.get(LootParameters.field_237457_g_);
    }

    /**
     * Gets the block state that was broken, if present; null otherwise.
     *
     * @param internal The context.
     * @return The broken block state.
     */
    @ZenCodeType.Getter("blockState")
    @ZenCodeType.Nullable
    public static BlockState getBlockState(final LootContext internal) {
        return internal.get(LootParameters.BLOCK_STATE);
    }

    /**
     * Gets the block entity that was present at the location of the broken block, if present; null otherwise.
     *
     * @param internal The context.
     * @return The block entity.
     */
    @ZenCodeType.Getter("tileEntity")
    @ZenCodeType.Nullable
    public static TileEntity getTileEntity(final LootContext internal) {
        return internal.get(LootParameters.BLOCK_ENTITY);
    }

    /**
     * Gets the tool that was used to break the block or perform additional behavior, if present; an empty stack
     * otherwise.
     *
     * @param internal The context.
     * @return The tool that was used.
     */
    @ZenCodeType.Getter("tool")
    public static IItemStack getTool(final LootContext internal) {
        final ItemStack nativeTool = internal.get(LootParameters.TOOL);
        return nativeTool == null || nativeTool.isEmpty()? MCItemStack.EMPTY.get() : new MCItemStack(nativeTool);
    }

    /**
     * Gets the explosion radius that caused the death of the entity or the destruction of the block, if present; 0.0
     * otherwise.
     *
     * @param internal The context.
     * @return The explosion radius.
     */
    @ZenCodeType.Getter("explosionRadius")
    public static float getExplosionRadius(final LootContext internal) {
        final Float nativeRadius = internal.get(LootParameters.EXPLOSION_RADIUS);
        return nativeRadius == null? 0.0F : nativeRadius;
    }

    /**
     * Gets the looting modifier, calculated based on the current parameters.
     *
     * @param internal The context.
     * @return The looting modifier.
     */
    @ZenCodeType.Getter("lootingModifier")
    public static int getLootingModifier(final LootContext internal) {
        return internal.getLootingModifier();
    }

    /**
     * Gets the world where the interaction happened, if it exists or can be obtained; null otherwise.
     *
     * @param internal The context.
     * @return The world.
     */
    @ZenCodeType.Getter("world")
    @ZenCodeType.Nullable
    public static World getWorld(final LootContext internal) {
        return internal.getWorld();
    }

    /**
     * Gets the luck factor of the player, as computed by the current parameters.
     *
     * @param internal The context.
     * @return The luck factor of the player.
     */
    @ZenCodeType.Getter("luck")
    public static float getLuck(final LootContext internal) {
        return internal.getLuck();
    }

    /**
     * Gets the loot table id of the current one being rolled, if available; a place-holder otherwise.
     *
     * @param internal The context.
     * @return The id of the table currently being rolled.
     */
    @ZenCodeType.Getter("lootTableId")
    public static ResourceLocation getLootTableId(final LootContext internal) {
        return internal.getQueriedLootTableId();
    }
}
