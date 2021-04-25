package com.blamejared.crafttweaker.impl_native.tileentity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a block entity.
 */
@ZenRegister
@Document("vanilla/api/tileentity/MCTileEntity")
@NativeTypeRegistration(value = TileEntity.class, zenCodeName = "crafttweaker.api.tileentity.MCTileEntity")
public class ExpandTileEntity {
    @ZenCodeType.Getter("world")
    public static World getWorld(TileEntity internal) {
        return internal.getWorld();
    }

    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(TileEntity internal) {
        return internal.getPos();
    }

    @ZenCodeType.Getter("data")
    public static MapData getData(TileEntity internal) {
        return new MapData(internal.write(new CompoundNBT()));
    }

    @ZenCodeType.Method
    public static void updateData(TileEntity internal, MapData data) {
        internal.read(internal.getBlockState(), getData(internal).merge(data).getInternal());
    }
}
