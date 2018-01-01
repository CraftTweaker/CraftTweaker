package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.world.IFacing")
@ZenRegister
public interface IFacing {
	
	@ZenMethod
	IFacing opposite();
	
	@ZenGetter("name")
	@ZenMethod
	String getName();
	
	Object getInternal();
}
