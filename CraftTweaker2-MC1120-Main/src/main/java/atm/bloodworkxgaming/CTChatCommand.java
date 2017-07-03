package atm.bloodworkxgaming;

import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jonas on 03.07.2017.
 */
public class CTChatCommand extends CommandBase{

    public static Map<String, CraftTweakerCommand> craftTweakerCommands = new HashMap<>();

    public static final List<String> aliases = new ArrayList<>();
    static {
        aliases.add("ct");
    }

    public static void register(FMLServerStartingEvent ev){
        Commands.registerCommands(craftTweakerCommands);
        ev.registerServerCommand(new CTChatCommand());
    }

    @Override
    public String getName() {
        return "crafttweaker";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 0){
            sender.sendMessage(new TextComponentString(getUsage(sender)));
        }

        if (craftTweakerCommands.containsKey(args[0])){
            if (sender.getCommandSenderEntity() instanceof EntityPlayer){
                craftTweakerCommands.get(args[0]).executeCommand(
                        server, sender, ArrayUtils.subarray(args, 1, args.length - 1));
            }else {
                craftTweakerCommands.get(args[0]).executeCommand(
                        server, sender, ArrayUtils.subarray(args, 1, args.length - 1));
            }
        }
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
