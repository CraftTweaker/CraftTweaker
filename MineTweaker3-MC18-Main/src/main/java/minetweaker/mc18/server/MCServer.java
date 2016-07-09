/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18.server;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.AbstractServer;
import minetweaker.api.server.ICommandFunction;
import minetweaker.api.server.ICommandTabCompletion;
import minetweaker.api.server.ICommandValidator;
import minetweaker.mc18.MineTweakerMod;
import minetweaker.mc18.player.CommandBlockPlayer;
import minetweaker.mc18.player.RconPlayer;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.util.BlockPos;

import java.util.Arrays;
import java.util.List;

/**
 * @author Stan
 */
public class MCServer extends AbstractServer {
    private final MinecraftServer server;

    public MCServer(MinecraftServer server) {
        this.server = server;
    }

    private static IPlayer getPlayer(ICommandSender commandSender) {
        if (commandSender instanceof EntityPlayer) {
            return MineTweakerMC.getIPlayer((EntityPlayer) commandSender);
        } else if (commandSender instanceof DedicatedServer) {
            return ServerPlayer.INSTANCE;
        } else if (commandSender instanceof RConConsoleSource) {
            return new RconPlayer(commandSender);
        } else if (commandSender instanceof CommandBlockLogic) {
            return new CommandBlockPlayer(commandSender);
        } else {
            System.out.println("Unsupported command sender: " + commandSender);
            System.out.println("player name: " + commandSender.getName());
            return null;
        }
    }

    @Override
    public void addCommand(String name, String usage, String[] aliases, ICommandFunction function, ICommandValidator validator, ICommandTabCompletion completion) {

        ICommand command = new MCCommand(name, usage, aliases, function, validator, completion);
        MineTweakerAPI.apply(new AddCommandAction(command));
    }

    @Override
    public boolean isOp(IPlayer player) {
        if (player == ServerPlayer.INSTANCE)
            return true;

        UserListOps ops = MineTweakerMod.server.getConfigurationManager().getOppedPlayers();
        if (server.isDedicatedServer() && ops != null) {
            //TODO figure if this is correct
            //            return ops.func_152690_d() || ops.func_152700_a(player.getName()) != null || player instanceof RconPlayer;
            return ops.isEmpty() || ops.getGameProfileFromName(player.getName()) != null || player instanceof RconPlayer;
        } else {
            return true;
        }
    }

    @Override
    public boolean isCommandAdded(String name) {
        return MineTweakerMod.server.getCommandManager().getCommands().containsKey(name);
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
        public String getCommandName() {
            return name;
        }

        @Override
        public String getCommandUsage(ICommandSender var1) {
            return usage;
        }

        @Override
        public List<String> getCommandAliases() {
            return aliases;
        }

        @Override
        public boolean canCommandSenderUseCommand(ICommandSender var1) {
            if (validator == null) {
                return true;
            } else {
                return validator.canExecute(getPlayer(var1));
            }
        }


        @Override
        public boolean isUsernameIndex(String[] var1, int var2) {
            return false;
        }


        @Override
        public void processCommand(ICommandSender sender, String[] args) throws CommandException {
            function.execute(args, getPlayer(sender));
        }

        @Override
        public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
            if (completion != null) {
                return Arrays.asList(completion.getTabCompletionOptions(args, getPlayer(sender)));
            } else {
                return null;
            }
        }

        @Override
        public int compareTo(ICommand o) {
            return 0;
        }
    }

    private class AddCommandAction implements IUndoableAction {
        private final ICommand command;

        public AddCommandAction(ICommand command) {
            this.command = command;
        }

        @Override
        public void apply() {
            CommandHandler ch = (CommandHandler) MineTweakerMod.server.getCommandManager();
            if (!ch.getCommands().containsValue(command))
                ch.registerCommand(command);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {

        }

        @Override
        public String describe() {
            CommandHandler ch = (CommandHandler) MineTweakerMod.server.getCommandManager();
            if (!ch.getCommands().containsValue(command))
                return "Adding command " + command.getCommandName();
            return "Tried to add command: " + command.getCommandName() + ", failed. THIS IS NOT AN ERROR!";
        }

        @Override
        public String describeUndo() {
            return "Tried to remove command: " + command.getCommandName() + ", failed. THIS IS NOT AN ERROR!";
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

}
