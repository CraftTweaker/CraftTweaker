package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A class to handle some data when using item.
 */
@ZenRegister
@Document("vanilla/api/item/MCItemUseContext")
@NativeTypeRegistration(value = ItemUseContext.class, zenCodeName = "crafttweaker.api.item.MCItemUseContext")
public class ExpandItemUseContext {
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(ItemUseContext internal) {
        return internal.getPos();
    }

    @ZenCodeType.Getter("world")
    public static World getWorld(ItemUseContext internal) {
        return internal.getWorld();
    }

    @ZenCodeType.Getter("player")
    @ZenCodeType.Nullable
    public static PlayerEntity getPlayer(ItemUseContext internal) {
        return internal.getPlayer();
    }

    @ZenCodeType.Getter("hand")
    public static Hand getHand(ItemUseContext internal) {
        return internal.getHand();
    }

    @ZenCodeType.Getter("direction")
    public static MCDirection getDirection(ItemUseContext internal) {
        return MCDirection.get(internal.getFace());
    }

    @ZenCodeType.Getter("hitVector")
    public static Vector3d getHitVector(ItemUseContext internal) {
        return internal.getHitVec();
    }

    @ZenCodeType.Getter("hasSecondaryUseForPlayer")
    public static boolean hasSecondaryUseForPlayer(ItemUseContext internal) {
        return internal.hasSecondaryUseForPlayer();
    }

    /**
     * @return the player's horizontal facing, returns north if the player is null
     */
    @ZenCodeType.Getter("placementHorizontalFacing")
    public static MCDirection getPlacementHorizontalFacing(ItemUseContext internal) {
        return MCDirection.get(internal.getPlacementHorizontalFacing());
    }
}
