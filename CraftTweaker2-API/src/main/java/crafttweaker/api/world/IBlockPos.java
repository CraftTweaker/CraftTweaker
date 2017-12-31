package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IPosition3f;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.world.IBlockPos")
@ZenRegister
public interface IBlockPos {

	@ZenMethod
	@ZenGetter("x")
	int getX();
	
	@ZenMethod
	@ZenGetter("y")
	int getY();
	
	@ZenMethod
	@ZenGetter("z")
	int getZ();

	@ZenMethod
	IBlockPos getOffset(String directionName, int offset);

	@ZenCaster
	@ZenMethod
	IPosition3f asPosition3f();

	Object getInternal();

}
