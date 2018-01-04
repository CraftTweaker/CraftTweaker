package crafttweaker.mc1120.server;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.command.ICommandSender;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.server.IServer;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.world.MCBlockPos;
import net.minecraft.util.math.BlockPos;

/**
 * @author Stan
 */
public class ServerPlayer implements IPlayer {
    
    public static final ServerPlayer INSTANCE = new ServerPlayer();
    
    private ServerPlayer() {
    
    }
    
    @Override
    public String getDisplayName() {
        return "SERVER";
    }
    
    @Override
    public IBlockPos getPosition() {
        return new MCBlockPos(BlockPos.ORIGIN);
    }
    
    @Override
    public IWorld getWorld() {
        return null;
    }
    
    @Override
    public IServer getServer() {
        return CraftTweakerAPI.server;
    }
    
    @Override
    public void sendMessage(String text) {
    }
    
    @Override
    public void sendMessage(IFormattedText text) {
    }
}
