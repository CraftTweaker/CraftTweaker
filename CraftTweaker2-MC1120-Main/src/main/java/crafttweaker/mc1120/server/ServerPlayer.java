package crafttweaker.mc1120.server;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.player.IUser;
import crafttweaker.api.server.IServer;
import crafttweaker.api.text.ITextComponent;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.world.MCBlockPos;
import net.minecraft.util.math.BlockPos;

/**
 * @author Stan
 */
public class ServerPlayer implements IUser {
    
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
    public Object getInternal() {
        return null;
    }
}
