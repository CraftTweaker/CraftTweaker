package crafttweaker.mc1120.server;

import crafttweaker.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.server.*;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.player.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;

/**
 * @author Stan
 */
public class MCServer extends AbstractServer {

    private final MinecraftServer server;

    public MCServer(MinecraftServer server) {
        this.server = server;
    }

    private static IPlayer getPlayer(ICommandSender commandSender) {
        if(commandSender instanceof EntityPlayer) {
            return CraftTweakerMC.getIPlayer((EntityPlayer) commandSender);
        } else if(commandSender instanceof RConConsoleSource) {
            return new RconPlayer(commandSender);
        } else if(commandSender instanceof CommandBlockBaseLogic) {
            return new CommandBlockPlayer(commandSender);
        } else if(commandSender.getName().equals("Server")) {
            return ServerPlayer.INSTANCE;
        } else {
            System.out.println("Unsupported command sender: " + commandSender + " defaulting to server player!");
            System.out.println("player name: " + commandSender.getName());
            System.out.println("Please report to mod author if this is incorrect!");
            return ServerPlayer.INSTANCE;
        }
    }

    @Override
    public void addCommand(String name, String usage, String[] aliases, ICommandFunction function, @Optional ICommandValidator validator, @Optional ICommandTabCompletion completion) {
        ICommand command = new MCCommand(name, usage, aliases, function, validator, completion);
        CraftTweakerAPI.apply(new AddCommandAction(command));
    }

    @Override
    public boolean isOp(IPlayer player) {
        if(player == ServerPlayer.INSTANCE)
            return true;
    
        UserListOps ops = CraftTweaker.server.getPlayerList().getOppedPlayers();
        return !(server != null && server.isDedicatedServer()) || ops.isEmpty() || ops.getGameProfileFromName(player.getName()) != null || player instanceof RconPlayer;
    }

    @Override
    public boolean isCommandAdded(String name) {
        return CraftTweaker.server.getCommandManager().getCommands().containsKey(name);
    }

    private class MCCommand implements ICommand {

        private final String name;
        private final String usage;
        private final List<String> aliases;
        private final ICommandFunction function;
        private final ICommandValidator validator;
        private final ICommandTabCompletion completion;

        public MCCommand(String name, String usage, String[] aliases, ICommandFunction function, ICommandValidator validator, ICommandTabCompletion completion) {
            this.name = name;
            this.usage = usage;
            this.aliases = Arrays.asList(aliases);
            this.function = function;
            this.validator = validator;
            this.completion = completion;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getUsage(ICommandSender var1) {
            return usage;
        }

        @Override
        public List getAliases() {
            return aliases;
        }
        
        
        @Override
        public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            function.execute(args, getPlayer(sender));
        }

        @Override
        public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
            return validator == null || validator.canExecute(getPlayer(sender));
        }
        
        @Override
        public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
            if(completion != null) {
                return Arrays.asList(completion.getTabCompletionOptions(args, getPlayer(sender)));
            } else {
                return null;
            }
        }

        @Override
        public boolean isUsernameIndex(String[] var1, int var2) {
            return false;
        }

        @Override
        public int compareTo(ICommand o) {
            return this.getName().compareTo(o.getName());
        }
    }

    private class AddCommandAction implements IAction {

        private final ICommand command;

        public AddCommandAction(ICommand command) {
            this.command = command;
        }

        @Override
        public void apply() {
            CommandHandler ch = (CommandHandler) CraftTweaker.server.getCommandManager();
            if(!ch.getCommands().containsValue(command))
                ch.registerCommand(command);
        }

        @Override
        public String describe() {
            CommandHandler ch = (CommandHandler) CraftTweaker.server.getCommandManager();
            if(!ch.getCommands().containsValue(command))
                return "Adding command " + command.getName();
            return "";
        }
    }

}
