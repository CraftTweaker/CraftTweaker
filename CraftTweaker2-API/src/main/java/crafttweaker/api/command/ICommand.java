package crafttweaker.api.command;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.server.IServer;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenClass("crafttweaker.command.ICommand")
@ZenRegister
public interface ICommand {
    
    @ZenGetter("name")
    String getName();
    
    @ZenMethod
    String getUsage(ICommandSender sender);
    
    @ZenGetter("aliases")
    List<String> getAliases();
    
    @ZenMethod
    void execute(IServer server, ICommandSender sender, String[] args);
    
    @ZenMethod
    boolean checkPermission(IServer server, ICommandSender sender);
    
    @ZenMethod
    List<String> getTabCompletions(IServer server, ICommandSender sender, String[] args, @Optional IBlockPos targetPos);
    
    @ZenMethod
    boolean isUsernameIndex(String[] args, int index);
    
    @ZenOperator(OperatorType.COMPARE)
    int compare(ICommand other);
    
    Object getInternal();
}
