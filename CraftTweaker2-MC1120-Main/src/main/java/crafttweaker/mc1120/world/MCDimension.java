package crafttweaker.mc1120.world;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IPosition3f;
import crafttweaker.api.world.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Stan
 */
public class MCDimension implements IDimension {

    private final World world;

    public MCDimension(World world) {
        this.world = world;
    }

    @Override
    public boolean isDay() {
        return world.isDaytime();
    }

    @Override
    public int getBrightness(int x, int y, int z) {
        return world.getLight(new BlockPos(x, y, z));
    }

    @Override
    public IDimension getDimension() {
        return this;
    }

    @Override
    public IBlock getBlock(int x, int y, int z) {
        return CraftTweakerMC.getBlock(world, x, y, z);
    }
    
    @Override
    public IBiome getBiome(IPosition3f position) {
    	return new MCBiome(world.getBiome(new BlockPos(position.getX(), position.getY(), position.getZ())));
    }
    
    @Override
    public IWorld asWorld() {
        return new MCWorld(world);
    }
}
