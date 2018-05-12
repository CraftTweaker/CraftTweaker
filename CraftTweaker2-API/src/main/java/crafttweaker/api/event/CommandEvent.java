package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.command.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.CommandEvent")
@ZenRegister
public interface CommandEvent extends IEventCancelable {

    @ZenGetter("commandSender")
    ICommandSender getCommandSender();

    @ZenGetter("command")
    ICommand getCommand();

    @ZenGetter("parameters")
    String[] getParameters();

    @ZenSetter("parameters")
    void setParameters(String[] parameters);
}
