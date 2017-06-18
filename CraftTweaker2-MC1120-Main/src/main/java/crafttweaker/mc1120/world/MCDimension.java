package crafttweaker.mc1120.world;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IDimension;
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
}
