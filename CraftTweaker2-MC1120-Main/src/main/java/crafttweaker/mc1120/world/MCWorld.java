package crafttweaker.mc1120.world;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IPosition3f;
import crafttweaker.api.world.IBiome;
import crafttweaker.api.world.IWorld;
import crafttweaker.api.world.IWorldInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Stan
 */
public class MCWorld implements IWorld {

    private final World world;

    public MCWorld(World world) {
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
    public IBlock getBlock(int x, int y, int z) {
        return CraftTweakerMC.getBlock(world, x, y, z);
    }
    
    @Override
    public IBiome getBiome(IPosition3f position) {
    	return new MCBiome(world.getBiome(new BlockPos(position.getX(), position.getY(), position.getZ())));
    }

	@Override
	public IWorldInfo getWorldInfo() {
		return new MCWorldInfo(world.getWorldInfo());
	}

	@Override
	public boolean isRemote() {
		return world.isRemote;
	}

	@Override
	public boolean isRaining() {
		return world.isRaining();
	}

	@Override
	public boolean isDayTime() {
		return world.isDaytime();
	}

	@Override
	public boolean isSurfaceWorld() {
		return world.provider.isSurfaceWorld();
	}

	@Override
	public long getWorldTime() {
		return world.getTotalWorldTime();
	}

	@Override
	public int getMoonPhase() {
		return world.getMoonPhase();
	}

	@Override
	public int getDimension() {
		return world.provider.getDimension();
	}

	@Override
	public String getDimensionType() {
		return world.provider.getDimensionType().getName();
	}

	@Override
	public String getWorldType() {
		return world.getWorldType().getName();
	}
	
	@Override
	public Object getInternal() {
		return world;
	}
}
