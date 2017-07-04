package atm.bloodworkxgaming;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static atm.bloodworkxgaming.SpecialMessagesChat.getNormalMessage;

/**
 * @author BloodWorkXGaming
 */
public abstract class CraftTweakerCommand {

    public final static ITextComponent[] NO_DESCRIPTION = new ITextComponent[]{getNormalMessage("No Description provided")};
    public final static List<String> EMPTY_LIST = new ArrayList<>(0);

    protected final String subCommandName;
    private ITextComponent[] description;

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

    public ITextComponent[] getDescription() {
        return description == null ? NO_DESCRIPTION : description;
    }

    public void setDescription(ITextComponent... descriptionIn){
        this.description = descriptionIn;
    }

    public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        return EMPTY_LIST;
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
