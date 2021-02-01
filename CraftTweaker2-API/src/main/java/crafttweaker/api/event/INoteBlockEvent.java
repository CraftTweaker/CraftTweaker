package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.INoteBlockEvent")
@ZenRegister
public interface INoteBlockEvent extends IBlockEvent {
    
    @ZenGetter("note")
    String getNote();

    @ZenSetter("note")
    void setNote(String note, String octave);

    @ZenGetter("octave")
    String getOctave();

    @ZenGetter("noteId")
    int getNoteId();
}