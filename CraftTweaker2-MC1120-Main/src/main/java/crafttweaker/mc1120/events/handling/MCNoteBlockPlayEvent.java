package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.NoteBlockPlayEvent;
import net.minecraftforge.event.world.NoteBlockEvent;

public class MCNoteBlockPlayEvent extends MCNoteBlockEvent implements NoteBlockPlayEvent {
    private NoteBlockEvent.Play event;

    public MCNoteBlockPlayEvent(NoteBlockEvent.Play event) {
        super(event);
        this.event = event;
    }

    @Override
    public String getInstrument() {
        return event.getInstrument().toString();
    }

    @Override
    public void setInstrument(String instrument) {
        event.setInstrument(NoteBlockEvent.Instrument.valueOf(instrument.toUpperCase()));
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}