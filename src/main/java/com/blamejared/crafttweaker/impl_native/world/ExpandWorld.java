package com.blamejared.crafttweaker.impl_native.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/MCWorld")
@NativeTypeRegistration(value = World.class, zenCodeName = "crafttweaker.api.world.MCWorld")
public class ExpandWorld {
    
    @ZenCodeType.Getter("remote")
    @ZenCodeType.Method
    public static boolean isRemote(World internal) {
        return internal.isRemote;
    }
    
    @ZenCodeType.Getter("dayTime")
    public static boolean isDayTime(World internal) {
        return internal.isDaytime();
    }
    
    @ZenCodeType.Getter("nightTime")
    public static boolean isNightTime(World internal) {
        return internal.isNightTime();
    }
    
    @ZenCodeType.Getter("gameTime")
    public static long getTime(World internal) {
        return internal.getGameTime();
    }
    
    @ZenCodeType.Getter("seaLevel")
    public static int getSeaLevel(World internal) {
        return internal.getSeaLevel();
    }
    
    @ZenCodeType.Getter("raining")
    public static boolean isRaining(World internal) {
        return internal.isRaining();
    }
    
    @ZenCodeType.Getter("thundering")
    public static boolean isThundering(World internal) {
        return internal.isThundering();
    }
    
    @ZenCodeType.Getter("hardcore")
    public static boolean isHardcore(World internal) {
        return internal.getWorldInfo().isHardcore();
    }
    
    @ZenCodeType.Getter("difficulty")
    public static String getDifficulty(World internal) {
        return internal.getWorldInfo().getDifficulty().getTranslationKey();
    }
    
    @ZenCodeType.Getter("difficultyLocked")
    public static boolean isDifficultyLocked(World internal) {
        return internal.getWorldInfo().isDifficultyLocked();
    }
    
    @ZenCodeType.Getter("dimension")
    public static String getDimension(World internal) {
        return internal.getDimensionKey().getRegistryName().toString();
    }
    
    @ZenCodeType.Method
    public static boolean isRainingAt(World internal, BlockPos pos) {
        return internal.isRainingAt(pos);
    }
    
    @ZenCodeType.Method
    public static int getStrongPower(World internal, BlockPos pos) {
        return internal.getStrongPower(pos);
    }
    
    @ZenCodeType.Method
    public static int getRedstonePower(World internal, BlockPos pos, MCDirection direction) {
        return internal.getRedstonePower(pos, direction.getInternal());
    }
    
    @ZenCodeType.Method
    public static int getRedstonePowerFromNeighbors(World internal, BlockPos pos) {
        return internal.getRedstonePowerFromNeighbors(pos);
    }
    
    @ZenCodeType.Method
    public static IData getTileData(World internal, BlockPos pos) {
        CompoundNBT nbt = new CompoundNBT();
        TileEntity te = internal.getTileEntity(pos);
        return te == null ? new MapData() : NBTConverter.convert(te.write(nbt));
    }
    
    @ZenCodeType.Method
    public static boolean setBlockState(World internal, BlockPos pos, BlockState state) {
        return internal.setBlockState(pos, state);
    }
    
    @ZenCodeType.Method
    public static BlockState getBlockState(World internal, BlockPos pos) {
        return internal.getBlockState(pos);
    }
    
    @ZenCodeType.Method
    public static boolean isBlockPowered(World internal, BlockPos pos) {
        return internal.isBlockPowered(pos);
    }
    
    @ZenCodeType.Method
    public static Biome getBiome(World internal, BlockPos pos) {
        return internal.getBiome(pos);
    }
}
