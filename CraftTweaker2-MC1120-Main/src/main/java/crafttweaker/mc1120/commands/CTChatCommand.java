package crafttweaker.mc1120.commands;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

/**
 * @author BloodWorkXGaming
 */
public class CTChatCommand extends CommandBase {
    public static final String CRAFTTWEAKER_LOG_PATH = new File("crafttweaker.log").getAbsolutePath();
    private static final List<String> aliases = new ArrayList<>();
    private static final Map<String, CraftTweakerCommand> craftTweakerCommands = new TreeMap<>();
    
    static {
        aliases.add("ct");
        aliases.add("mt");
        aliases.add("minetweaker");
    }
    
    public static void register(FMLServerStartingEvent ev) {
        Commands.registerCommands();
        ev.registerServerCommand(new CTChatCommand());
    }
    
    public static void registerCommand(CraftTweakerCommand command) {
        craftTweakerCommands.put(command.getSubCommandName(), command);
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
        
        for(int i = 0; i < commands.length; i++) {
            sb.append(commands[i]);
            if(i != commands.length - 1)
                sb.append(" | ");
            
        }
        
        return sb.toString();
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length <= 0) {
            sender.sendMessage(new TextComponentString(getUsage(sender)));
            return;
        }
        
        if(craftTweakerCommands.containsKey(args[0])) {
            if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                craftTweakerCommands.get(args[0]).executeCommand(server, sender, ArrayUtils.subarray(args, 1, args.length));
            } else {
                craftTweakerCommands.get(args[0]).executeCommand(server, sender, ArrayUtils.subarray(args, 1, args.length));
            }
        } else {
            sender.sendMessage(SpecialMessagesChat.getClickableCommandText("\u00A7cNo such command! \u00A76[Click to show help]", "/ct help", true));
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
        
        if(args.length <= 0) {
            return new ArrayList<>(keys);
        }
        
        // First sub-command
        if(args.length == 1) {
            for(String cmd : keys) {
                if(cmd.startsWith(args[0])) {
                    currentPossibleCommands.add(cmd);
                }
            }
            return currentPossibleCommands;
        }
        
        // gives subcommands of the subcommand
        // each has to implement on it's own for special requirements
        CraftTweakerCommand subCommand = craftTweakerCommands.get(args[0]);
        if(subCommand != null) {
            System.out.println(Arrays.toString(ArrayUtils.subarray(args, 1, args.length)));
            return subCommand.getSubSubCommand(server, sender, ArrayUtils.subarray(args, 1, args.length), targetPos);
            
        }
        
        // returns empty by default
        return currentPossibleCommands;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    public static Map<String, CraftTweakerCommand> getCraftTweakerCommands() {
        return craftTweakerCommands;
    }
}
