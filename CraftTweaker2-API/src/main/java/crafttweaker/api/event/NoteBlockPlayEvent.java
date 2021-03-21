package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.NoteBlockPlayEvent")
@ZenRegister
public interface NoteBlockPlayEvent extends INoteBlockEvent, IEventCancelable {
    
    @ZenGetter("instrument")
    String getInstrument();

    @ZenSetter("instrument")
    void setInstrument(String instrument);
}