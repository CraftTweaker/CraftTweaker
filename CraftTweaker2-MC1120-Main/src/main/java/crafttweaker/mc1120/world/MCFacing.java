package crafttweaker.mc1120.world;

import crafttweaker.api.world.IFacing;
import net.minecraft.util.EnumFacing;

public class MCFacing implements IFacing{
	
	private final EnumFacing facing;
	
	public MCFacing(EnumFacing facing) {
		this.facing = facing;
	}

	@Override
	public IFacing opposite() {
		return new MCFacing(facing.getOpposite());
	}

	@Override
	public Object getInternal() {
		return facing;
	}

	@Override
	public String getName() {
		return facing.name();
	}
}