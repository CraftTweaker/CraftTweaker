package crafttweaker.mc1120.player;

import crafttweaker.api.player.IUser;
import crafttweaker.api.server.IServer;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.server.MCServer;
import crafttweaker.mc1120.world.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Jared
 */
public class CommandBlockPlayer implements IUser {
    
    private final ICommandSender sender;
    
    public CommandBlockPlayer(ICommandSender sender) {
        this.sender = sender;
    }
    
    @Override
    public String getDisplayName() {
        return sender.getDisplayName().getFormattedText();
    }
    
    @Override
    public IBlockPos getPosition() {
        return new MCBlockPos(sender.getPosition());
    }
    
    @Override
    public IWorld getWorld() {
        return new MCWorld(sender.getEntityWorld());
    }
    
    @Override
    public IServer getServer() {
        return new MCServer(sender.getServer());
    }
    
    @Override
    public void sendMessage(String text) {
        sender.sendMessage(new TextComponentString(text));
    }
    
}
