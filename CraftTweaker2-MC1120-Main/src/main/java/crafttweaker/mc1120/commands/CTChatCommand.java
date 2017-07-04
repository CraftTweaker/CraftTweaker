package crafttweaker.mc1120.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

/**
 * @author BloodWorkXGaming
 */
public class CTChatCommand extends CommandBase{

    public static Map<String, CraftTweakerCommand> craftTweakerCommands = new TreeMap<>();

    public static final String CRAFTTWEAKER_LOG_PATH = new File("crafttweaker.log").getAbsolutePath();

    public static final List<String> aliases = new ArrayList<>();
    static {
        aliases.add("ct");
    }

    public static void register(FMLServerStartingEvent ev){
        Commands.registerCommands();
        ev.registerServerCommand(new CTChatCommand());
    }

    @Override
    public String getName() {
        return "crafttweaker";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        StringBuilder sb = new StringBuilder();

        sb.append("/crafttweaker ");

        String[] commands = new String[craftTweakerCommands.keySet().size()];
        craftTweakerCommands.keySet().toArray(commands);

        for (int i = 0; i < commands.length; i++) {
            sb.append(commands[i]);
            if (i != commands.length - 1) sb.append(" | ");

        }

        return sb.toString();
    }

    public static void sendUsage(ICommandSender sender) {
        sender.sendMessage(SpecialMessagesChat.EMPTY_TEXTMESSAGE);

        for (Map.Entry<String, CraftTweakerCommand> entry : craftTweakerCommands.entrySet()) {
            for (ITextComponent s : entry.getValue().getDescription()) {
                sender.sendMessage(s);
            }
            sender.sendMessage(SpecialMessagesChat.EMPTY_TEXTMESSAGE);
        }
    }



    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 0){
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }

        if (craftTweakerCommands.containsKey(args[0])){
            if (sender.getCommandSenderEntity() instanceof EntityPlayer){
                craftTweakerCommands.get(args[0]).executeCommand(
                        server, sender, ArrayUtils.subarray(args, 1, args.length));
            }else {
                craftTweakerCommands.get(args[0]).executeCommand(
                        server, sender, ArrayUtils.subarray(args, 1, args.length));
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        Set<String> keys = craftTweakerCommands.keySet();
        List<String> currentPossibleCommands = new ArrayList<>();

        if (args.length <= 0){
            return new ArrayList<>(keys);
        }

        // First sub-command
        if (args.length == 1){
            for (String cmd: keys) {
                if (cmd.startsWith(args[0])){
                    currentPossibleCommands.add(cmd);
                }
            }
            return currentPossibleCommands;
        }

        // gives subcommands of the subcommand
        // each has to implement on it's own for special requirements
        if (args.length >= 2){
            CraftTweakerCommand subCommand = craftTweakerCommands.get(args[0]);
            if (subCommand != null){
                System.out.println(Arrays.toString(ArrayUtils.subarray(args, 1, args.length)));
                return subCommand.getSubSubCommand(server, sender, ArrayUtils.subarray(args, 1, args.length), targetPos);

            }
        }

        // returns empty by default
        return currentPossibleCommands;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }

    public static void registerCommand(CraftTweakerCommand command){
        craftTweakerCommands.put(command.getSubCommandName(), command);
    }
}
