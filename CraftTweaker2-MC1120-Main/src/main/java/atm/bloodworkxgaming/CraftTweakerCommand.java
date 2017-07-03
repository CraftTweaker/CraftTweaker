package atm.bloodworkxgaming;

import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by jonas on 03.07.2017.
 */
public abstract class CraftTweakerCommand {

    public final String subCommandName;
    public final String[] description;
    // private final ICommandFunction function;
    public final String[] subSubCommands;


    public CraftTweakerCommand(String subCommandName, String[] description) {
        this(subCommandName, description, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public CraftTweakerCommand(String subCommandName, String[] description, String[] subSubCommands) {
        this.subCommandName = subCommandName;
        this.description = description;
        this.description[0] = "\u00A79" + this.description[0];
        // this.function = function;
        this.subSubCommands = subSubCommands;
    }

    /**
     *
     * @param server
     * @param sender
     * @param args: Has only the args after this original event
     */
    public abstract void executeCommand(MinecraftServer server, ICommandSender sender, String[] args);


}
