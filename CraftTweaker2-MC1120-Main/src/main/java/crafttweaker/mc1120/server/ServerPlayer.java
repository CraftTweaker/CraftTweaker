package crafttweaker.mc1120.server;

import crafttweaker.api.chat.IChatMessage;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.util.Position3f;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.net.URI;
import java.util.logging.*;

/**
 * @author Stan
 */
public class ServerPlayer implements IPlayer {

    public static final ServerPlayer INSTANCE = new ServerPlayer();

    private ServerPlayer() {

    }

    @Override
    public String getId() {
        return "server";
    }

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public IData getData() {
        return null;
    }
    
    @Override
    public int getXP() {
        return 0;
    }
    
    @Override
    public void setXP(int xp) {
        
    }
    
    @Override
    public void removeXP(int xp) {
        
    }
    
    @Override
    public void update(IData data) {
    }

    @Override
    public void sendChat(IChatMessage message) {
        FMLServerHandler.instance().getServer().sendMessage((ITextComponent) message.getInternal());
    }

    @Override
    public void sendChat(String message) {
        FMLServerHandler.instance().getServer().sendMessage(new TextComponentString(message));
    }

    @Override
    public int getHotbarSize() {
        return 0;
    }

    @Override
    public IItemStack getHotbarStack(int i) {
        return null;
    }

    @Override
    public int getInventorySize() {
        return 0;
    }

    @Override
    public IItemStack getInventoryStack(int i) {
        return null;
    }

    @Override
    public IItemStack getCurrentItem() {
        return null;
    }

    @Override
    public boolean isCreative() {
        return true;
    }

    @Override
    public boolean isAdventure() {
        return false;
    }

    @Override
    public void openBrowser(String url) {
        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(URI.create(url));
            } catch(IOException ex) {
                Logger.getLogger(ServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void copyToClipboard(String value) {
        if(Desktop.isDesktopSupported()) {
            StringSelection stringSelection = new StringSelection(value);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    @Override
    public void give(IItemStack stack) {

    }
    
    @Override
    public double getX() {
        return 0;
    }
    
    @Override
    public double getY() {
        return 0;
    }
    
    @Override
    public double getZ() {
        return 0;
    }
    
	@Override
	public Position3f getPosition() {
		return new Position3f((float)getX(), (float) getY(), (float) getZ());
	}

	@Override
	public void teleport(Position3f pos) {		
	}
}
