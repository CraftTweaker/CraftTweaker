package crafttweaker.mc1120.command;

import crafttweaker.api.command.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.command.ICommandManager;

import java.util.*;
import java.util.stream.Collectors;

public class MCCommandManager implements crafttweaker.api.command.ICommandManager {
    
    
    private final ICommandManager commandManager;
    
    public MCCommandManager(ICommandManager commandManager) {
        this.commandManager = commandManager;
    }
    
    @Override
    public int executeCommand(ICommandSender sender, String rawCommand) {
        return commandManager.executeCommand(CraftTweakerMC.getICommandSender(sender), rawCommand);
    }
    
    @Override
    public List<String> getTabCompletions(ICommandSender sender, String input, IBlockPos pos) {
        return commandManager.getTabCompletions(CraftTweakerMC.getICommandSender(sender), input, CraftTweakerMC.getBlockPos(pos));
    }
    
    @Override
    public List<ICommand> getPossibleCommands(ICommandSender sender) {
        return commandManager.getPossibleCommands(CraftTweakerMC.getICommandSender(sender)).stream().map(CraftTweakerMC::getICommand).collect(Collectors.toList());
    }
    
    @Override
    public Map<String, ICommand> getCommands() {
        Map<String, ICommand> out = new HashMap<>();
        commandManager.getCommands().forEach((key, command) -> out.put(key, CraftTweakerMC.getICommand(command)));
        return out;
    }
}
