package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.NoteBlockChangeEvent;
import net.minecraftforge.event.world.NoteBlockEvent;

public class MCNoteBlockChangeEvent extends MCNoteBlockEvent implements NoteBlockChangeEvent {
    private NoteBlockEvent.Change event;

    public MCNoteBlockChangeEvent(NoteBlockEvent.Change event) {
        super(event);
        this.event = event;
    }

    @Override
    public String getOldNote() {
        return event.getOldNote().toString();
    }

    @Override
    public String getOldOctave() {
        return event.getOldOctave().toString();
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