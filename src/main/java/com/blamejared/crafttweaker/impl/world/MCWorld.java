package com.blamejared.crafttweaker.impl.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker.impl.blocks.MCBlockState;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.util.MCBlockPos;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.world.MCWorld")
@Document("vanilla/api/world/MCWorld")
@ZenWrapper(wrappedClass = "net.minecraft.world.World")
public class MCWorld {
    public MCWorld(World internal) {
        this.internal = internal;
    }

    private World internal;

    @ZenCodeType.Getter("remote")
    @ZenCodeType.Method
    public boolean isRemote() {
        return internal.isRemote;
    }

    @ZenCodeType.Getter("dayTime")
    public boolean isDayTime() {
        return internal.isDaytime();
    }

    @ZenCodeType.Getter("nightTime")
    public boolean isNightTime() {
        return internal.isNightTime();
    }

    @ZenCodeType.Getter("gameTime")
    public long getTime() {
        return internal.getGameTime();
    }

    @ZenCodeType.Getter("seaLevel")
    public int getSeaLevel() {
        return internal.getSeaLevel();
    }

    @ZenCodeType.Getter("raining")
    public boolean isRaining() {
        return internal.isRaining();
    }

    @ZenCodeType.Getter("thundering")
    public boolean isThundering() {
        return internal.isThundering();
    }

    @ZenCodeType.Getter("hardcore")
    public boolean isHardcore() {
        return internal.getWorldInfo().isHardcore();
    }

    @ZenCodeType.Getter("difficulty")
    public String getDifficulty() {
        return internal.getWorldInfo().getDifficulty().getTranslationKey();
    }

    @ZenCodeType.Getter("difficultyLocked")
    public boolean isDifficultyLocked() {
        return internal.getWorldInfo().isDifficultyLocked();
    }

    @ZenCodeType.Getter("dimension")
    public String getDimension() {
        return internal.getDimensionKey().getRegistryName().toString();
    }

    @ZenCodeType.Method
    public boolean isRainingAt(MCBlockPos pos) {
        return internal.isRainingAt(pos.getInternal());
    }

    @ZenCodeType.Method
    public int getStrongPower(MCBlockPos pos) {
        return internal.getStrongPower(pos.getInternal());
    }

    @ZenCodeType.Method
    public int getRedstonePower(MCBlockPos pos, MCDirection direction) {
        return internal.getRedstonePower(pos.getInternal(), direction.getInternal());
    }

    @ZenCodeType.Method
    public int getRedstonePowerFromNeighbors(MCBlockPos pos) {
        return internal.getRedstonePowerFromNeighbors(pos.getInternal());
    }

    @ZenCodeType.Method
    public IData getTileData(MCBlockPos pos) {
        CompoundNBT nbt = new CompoundNBT();
        TileEntity te = internal.getTileEntity(pos.getInternal());
        return te == null ? new MapData() : NBTConverter.convert(te.write(nbt));
    }

    @ZenCodeType.Method
    public boolean setBlockState(MCBlockPos pos, MCBlockState state) {
        return internal.setBlockState(pos.getInternal(), state.getInternal());
    }

    @ZenCodeType.Method
    public MCBlockState getBlockState(MCBlockPos pos) {
        return new MCBlockState(internal.getBlockState(pos.getInternal()));
    }

    @ZenCodeType.Method
    public boolean isBlockPowered(MCBlockPos pos) {
        return internal.isBlockPowered(pos.getInternal());
    }

    @ZenCodeType.Method
    public MCBiome getBiome(MCBlockPos pos) {
        return new MCBiome(internal.getBiome(pos.getInternal()));
    }

    @ZenCodeType.Method
    public int nextRandomInt() {
        return internal.rand.nextInt();
    }

    @ZenCodeType.Method
    public int nextRandomInt(int bound) {
        return internal.rand.nextInt(bound);
    }

    @ZenCodeType.Method
    public boolean nextRandomBoolean() {
        return internal.rand.nextBoolean();
    }

    public World getInternal() {
        return internal;
    }
}
