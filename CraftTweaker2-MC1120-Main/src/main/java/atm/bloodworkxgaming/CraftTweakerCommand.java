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

    public final static String[] NO_DESCRIPTION = new String[]{"No Description provided"};
    protected final String subCommandName;
    private String[] description;
    // private final ICommandFunction function;
    public String[] subSubCommands = new String[0];


    public CraftTweakerCommand(String subCommandName) {
        this.subCommandName = subCommandName;
        init();
    }

    /**
     * Has to be overwritten
     * Used to set the description and all other values
     * Better for viability, as the constructor is not that full then
     */
    protected abstract void init();

    public String[] getDescription() {
        return description == null ? NO_DESCRIPTION : description;
    }

    public void setDescription(String... descriptionIn){
        for (int i = 0; i < descriptionIn.length; i++) {
            descriptionIn[i] = descriptionIn[i].replace("§", "\u00A7");
        }
        this.description = descriptionIn;
    }

    public void setSubSubCommands(String... subSubCommands){
        this.subSubCommands = subSubCommands;
    }

    public String getSubCommandName() {
        return subCommandName;
    }

    /**
     * Has to be overwritten by the commands
     * @param server
     * @param sender
     * @param args: Has only the args after this original event
     */
    public abstract void executeCommand(MinecraftServer server, ICommandSender sender, String[] args);


}
