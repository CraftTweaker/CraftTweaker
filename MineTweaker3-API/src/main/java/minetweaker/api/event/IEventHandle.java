package minetweaker.api.event;

import stanhebben.zenscript.annotations.*;

import java.io.Closeable;

/**
 * Represents an event handle. Event handles are used to cancel event listeners.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.event.IEventHandler")
public interface IEventHandle extends Closeable {

    /**
     * Closes the event listener (stops listening and unregisters the listener).
     */
    @ZenMethod
    @Override
    void close();
}
