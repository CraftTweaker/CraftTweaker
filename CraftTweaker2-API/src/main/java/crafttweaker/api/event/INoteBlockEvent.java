package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.INoteBlockEvent")
@ZenRegister
public interface INoteBlockEvent extends IBlockEvent {
    
    @ZenGetter("note")
    @ZenMethod
    String getNote();

    @ZenSetter("note")
    @ZenMethod
    void setNote(String note);

    @ZenGetter("octave")
    @ZenMethod
    String getOctave();

    @ZenSetter("octave")
    @ZenMethod
    void setOctave(String octave);

    @ZenMethod
    void setBoth(String note, String octave);

    @ZenGetter("noteId")
    @ZenMethod
    int getNoteId();
}