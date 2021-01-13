package crafttweaker.api.event;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.IWorldEvent")
@ZenRegister
public interface IWorldEvent {

	@ZenMethod
	@ZenGetter("world")
	default IWorld getWorld() {
		CraftTweakerAPI.logError("Default method IWorldEvent#getWorld is not overwritten in " + getClass() + " please report to the author!");
		return null;
	}
}