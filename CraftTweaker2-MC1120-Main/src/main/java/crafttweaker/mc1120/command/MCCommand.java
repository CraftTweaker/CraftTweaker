package crafttweaker.mc1120.command;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.command.ICommandSender;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.server.IServer;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.command.*;

import java.util.*;

public class MCCommand implements crafttweaker.api.command.ICommand {
    
    private final ICommand command;
    
    public MCCommand(ICommand iCommand) {
        this.command = iCommand;
    }
    
    @Override
    public String getName() {
        return command.getName();
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        return command.getUsage(CraftTweakerMC.getICommandSender(sender));
    }
    
    @Override
    public List<String> getAliases() {
        return command.getAliases();
    }
    
    @Override
    public void execute(IServer server, ICommandSender sender, String[] args) {
        try {
            command.execute(CraftTweakerMC.getMCServer(server), CraftTweakerMC.getICommandSender(sender), args);
        } catch(CommandException ex) {
            CraftTweakerAPI.logError("Error during command execution: ", ex);
        }
    }
    
    @Override
    public boolean checkPermission(IServer server, ICommandSender sender) {
        return command.checkPermission(CraftTweakerMC.getMCServer(server), CraftTweakerMC.getICommandSender(sender));
    }
    
    @Override
    public List<String> getTabCompletions(IServer server, ICommandSender sender, String[] args, IBlockPos targetPos) {
        return command.getTabCompletions(CraftTweakerMC.getMCServer(server), CraftTweakerMC.getICommandSender(sender), args, CraftTweakerMC.getBlockPos(targetPos));
    }
    
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return command.isUsernameIndex(args, index);
    }
    
    @Override
    public int compare(crafttweaker.api.command.ICommand other) {
        return command.compareTo(CraftTweakerMC.getICommand(other));
    }
    
    @Override
    public ICommand getInternal() {
        return command;
    }
}
