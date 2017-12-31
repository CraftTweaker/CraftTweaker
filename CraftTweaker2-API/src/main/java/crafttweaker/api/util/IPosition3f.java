package crafttweaker.api.util;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.util.Position3f")
@ZenRegister
public interface IPosition3f {
	
	@ZenGetter("x")
	@ZenMethod
	float getX();
	
	@ZenGetter("y")
	@ZenMethod
	float getY();
	
	@ZenGetter("z")
	@ZenMethod
	float getZ();
	
	
	
	@ZenSetter("x")
	@ZenMethod
	void setX(float x);
	
	@ZenSetter("y")
	@ZenMethod
	void setY(float y);
	
	@ZenSetter("z")
	@ZenMethod
	void setZ(float z);
	
	@ZenMethod
    @ZenCaster
	IBlockPos asBlockPos();
}
