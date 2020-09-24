package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IWorld;
import crafttweaker.api.world.IWorldInfo;
import crafttweaker.api.world.IWorldProvider;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.IWorldEvent")
@ZenRegister
public interface IWorldEvent {
	
	@ZenMethod
    @ZenGetter("world")
	IWorld getWorld();
	
	@ZenMethod
    @ZenGetter("worldInfo")
	IWorldInfo getWorldInfo();
	
	@ZenMethod
    @ZenGetter("worldProvider")
	IWorldProvider getWorldProvider();
}
