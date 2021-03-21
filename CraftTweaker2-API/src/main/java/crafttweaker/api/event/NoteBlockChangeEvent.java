package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.NoteBlockChangeEvent")
@ZenRegister
public interface NoteBlockChangeEvent extends INoteBlockEvent, IEventCancelable {
    
    @ZenGetter("oldNote")
    String getOldNote();

    @ZenGetter("oldOctave")
    String getOldOctave();
}