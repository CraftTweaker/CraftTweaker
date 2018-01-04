package crafttweaker.mc1120.player;

import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.command.MCCommandSender;
import net.minecraft.command.ICommandSender;

/**
 * @author Jared
 */
public class RconPlayer extends MCCommandSender implements IPlayer{
    
    public RconPlayer(ICommandSender sender) {
        super(sender);
    }
}
