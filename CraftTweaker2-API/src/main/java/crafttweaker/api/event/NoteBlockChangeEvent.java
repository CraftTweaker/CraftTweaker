package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.NoteBlockChangeEvent")
@ZenRegister
public interface NoteBlockChangeEvent extends INoteBlockEvent, IEventCancelable {
    
    @ZenGetter("oldNote")
    @ZenMethod
    String getOldNote();

    @ZenGetter("oldOctave")
    @zenMethod
    String getOldOctave();
}