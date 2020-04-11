package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.command.ICommand;
import crafttweaker.api.command.ICommandSender;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

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
