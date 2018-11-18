package crafttweaker.mc1120.server;

import crafttweaker.api.command.ICommandManager;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.*;
import crafttweaker.api.server.AbstractServer;
import crafttweaker.api.server.IServer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.command.MCCommandManager;
import crafttweaker.mc1120.player.CommandBlockPlayer;
import crafttweaker.mc1120.player.RconPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Stan
 */
public class MCServer extends AbstractServer {

    private final MinecraftServer server;

    public MCServer(MinecraftServer server) {
        this.server = server;
    }

    private static IUser getPlayer(ICommandSender commandSender) {
        if(commandSender instanceof EntityPlayer) {
            return CraftTweakerMC.getIPlayer((EntityPlayer) commandSender);
        } else if(commandSender instanceof RConConsoleSource) {
            return new RconPlayer(commandSender);
        } else if(commandSender instanceof CommandBlockBaseLogic) {
            return new CommandBlockPlayer(commandSender);
        } else if(commandSender.getName().equals("Server")) {
            return ServerPlayer.INSTANCE;
        } else {
            CraftTweaker.LOG.error("Unsupported command sender: " + commandSender + " defaulting to server player!");
            CraftTweaker.LOG.error("player name: " + commandSender.getName());
            CraftTweaker.LOG.error("Please report to mod author if this is incorrect!");
            return ServerPlayer.INSTANCE;
        }
    }

    @SuppressWarnings("MethodCallSideOnly")
    @Override
    public boolean isOp(IPlayer player) {
        UserListOps ops = CraftTweaker.server.getPlayerList().getOppedPlayers();
        return !(server != null && server.isDedicatedServer()) || ops.isEmpty() || ops.getGameProfileFromName(player.getName()) != null || player instanceof RconPlayer;
    }
    
    @Override
    public ICommandManager getCommandManager() {
        return new MCCommandManager(server.getCommandManager());
    }
    
    @Override
    public String getDisplayName() {
        return server.getDisplayName().getFormattedText();
    }
    
    @Override
    public IBlockPos getPosition() {
        return CraftTweakerMC.getIBlockPos(server.getPosition());
    }
    
    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(server.getEntityWorld());
    }
    
    @Override
    public IServer getServer() {
        return this;
    }
    
    @Override
    public void sendMessage(String text) {
        server.sendMessage(new TextComponentString(text));
    }
    
    @Override
    public MinecraftServer getInternal() {
        return server;
    }
}
