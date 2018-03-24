package crafttweaker.api.command;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.*;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;

@ZenClass("crafttweaker.command.ICommandManager")
@ZenRegister
public interface ICommandManager {
    
    @ZenMethod
    int executeCommand(ICommandSender sender, String rawCommand);
    
    @ZenMethod
    List<String> getTabCompletions(ICommandSender sender, String input, @Optional IBlockPos pos);
    
    @ZenMethod
    List<ICommand> getPossibleCommands(ICommandSender sender);
    
    @ZenGetter("commands")
    Map<String, ICommand> getCommands();
}
