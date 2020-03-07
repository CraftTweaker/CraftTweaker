package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.CropGrowEventPre")
@ZenRegister
public interface CropGrowEventPre extends IBlockEvent, IEventHasResult {
}
