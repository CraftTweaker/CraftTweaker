package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.CropGrowPreEvent")
@ZenRegister
public interface CropGrowPreEvent extends IBlockEvent, IEventHasResult {
}
