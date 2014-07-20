/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.server;

import java.util.Arrays;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.AbstractServer;
import minetweaker.api.server.ICommandFunction;
import minetweaker.api.server.ICommandTabCompletion;
import minetweaker.api.server.ICommandValidator;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.management.UserListOps;

/**
 *
 * @author Stan
 */
public class MCServer extends AbstractServer {
	private final MinecraftServer server;
	
	public MCServer(MinecraftServer server) {
		this.server = server;
	}
	
	@Override
	public void addCommand(String name, String usage, String[] aliases, ICommandFunction function, ICommandValidator validator, ICommandTabCompletion completion) {
		ICommand command = new MCCommand(name, usage, aliases, function, validator, completion);
		MineTweakerAPI.apply(new AddCommandAction(command));
	}
	
	@Override
	public void removeCommand(String name) {
		ICommand command = (ICommand)((CommandHandler) server.getCommandManager()).getCommands().get(name);
		if (command == null) {
			MineTweakerAPI.logWarning("No such command: " + name);
		} else {
			MineTweakerAPI.apply(new RemoveCommandAction(command));
		}
	}

	@Override
	public boolean isOp(IPlayer player) {
		if (player == ServerPlayer.INSTANCE)
			return true;
		
		UserListOps ops = MinecraftServer.getServer().getConfigurationManager().func_152603_m();
		if (server.isDedicatedServer()) {
			return ops.func_152690_d() || ops.func_152700_a(player.getName()) != null;
		} else {
			return true;
		}
	}
	
	private static IPlayer getPlayer(ICommandSender commandSender) {
		if (commandSender instanceof EntityPlayer) {
			return MineTweakerMC.getIPlayer((EntityPlayer) commandSender);
		} else if (commandSender instanceof DedicatedServer) {
			return ServerPlayer.INSTANCE;
		} else {
			System.out.println("Unsupported command sender: " + commandSender);
			System.out.println("player name: " + commandSender.getCommandSenderName());
			return null;
		}
	}
	
	private static void removeCommand(ICommand command) {
		CommandHandler ch = (CommandHandler) MinecraftServer.getServer().getCommandManager();
		ch.getCommands().remove(command.getCommandName());

		if (command.getCommandAliases() != null) {
			for (String alias : (List<String>)command.getCommandAliases()) {
				ch.getCommands().remove(alias);
			}
		}
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
		public List getCommandAliases() {
			return aliases;
		}

		@Override
		public void processCommand(ICommandSender var1, String[] var2) {
			function.execute(var2, getPlayer(var1));
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
		public List addTabCompletionOptions(ICommandSender var1, String[] var2) {
			if (completion != null) {
				return Arrays.asList(completion.getTabCompletionOptions(var2, getPlayer(var1)));
			} else {
				return null;
			}
		}

		@Override
		public boolean isUsernameIndex(String[] var1, int var2) {
			return false;
		}

		@Override
		public int compareTo(Object o) {
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
			CommandHandler ch = (CommandHandler) MinecraftServer.getServer().getCommandManager();
			ch.registerCommand(command);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			removeCommand(command);
		}

		@Override
		public String describe() {
			return "Adding command " + command.getCommandName();
		}

		@Override
		public String describeUndo() {
			return "Removing command " + command.getCommandName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private class RemoveCommandAction implements IUndoableAction {
		private final ICommand command;
		
		public RemoveCommandAction(ICommand command) {
			this.command = command;
		}

		@Override
		public void apply() {
			removeCommand(command);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			CommandHandler ch = (CommandHandler) MinecraftServer.getServer().getCommandManager();
			ch.registerCommand(command);
		}

		@Override
		public String describe() {
			return "Adding command " + command.getCommandName();
		}

		@Override
		public String describeUndo() {
			return "Removing command " + command.getCommandName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
