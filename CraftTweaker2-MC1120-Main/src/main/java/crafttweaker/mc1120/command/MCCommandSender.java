package crafttweaker.mc1120.command;

import crafttweaker.api.server.IServer;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.server.MCServer;
import crafttweaker.mc1120.world.*;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;

public class MCCommandSender implements crafttweaker.api.command.ICommandSender {
    
    private final ICommandSender sender;
    
    public MCCommandSender(ICommandSender commandSender) {
        sender = commandSender;
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
    
    @Override
    public ICommandSender getInternal() {
        return sender;
    }
    
}
