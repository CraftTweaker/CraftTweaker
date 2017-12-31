package crafttweaker.mc1120.server;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.server.AbstractServer;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.player.CommandBlockPlayer;
import crafttweaker.mc1120.player.RconPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOps;
import net.minecraft.tileentity.CommandBlockBaseLogic;

/**
 * @author Stan
 */
public class MCServer extends AbstractServer {

    private final MinecraftServer server;

    public MCServer(MinecraftServer server) {
        this.server = server;
    }

    private static IPlayer getPlayer(ICommandSender commandSender) {
        if (commandSender instanceof EntityPlayer) {
            return CraftTweakerMC.getIPlayer((EntityPlayer) commandSender);
        } else if (commandSender instanceof RConConsoleSource) {
            return new RconPlayer(commandSender);
        } else if (commandSender instanceof CommandBlockBaseLogic) {
            return new CommandBlockPlayer(commandSender);
        } else if (commandSender.getName().equals("Server")) {
            return ServerPlayer.INSTANCE;
        } else {
            System.out.println("Unsupported command sender: " + commandSender + " defaulting to server player!");
            System.out.println("player name: " + commandSender.getName());
            System.out.println("Please report to mod author if this is incorrect!");
            return ServerPlayer.INSTANCE;
        }
    }

    @SuppressWarnings("MethodCallSideOnly")
    @Override
    public boolean isOp(IPlayer player) {
        if (player == ServerPlayer.INSTANCE)
            return true;

        UserListOps ops = CraftTweaker.server.getPlayerList().getOppedPlayers();
        return !(server != null && server.isDedicatedServer()) || ops.isEmpty() || ops.getGameProfileFromName(player.getName()) != null || player instanceof RconPlayer;
    }
}
