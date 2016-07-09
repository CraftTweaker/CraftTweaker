/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.server;

import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stan
 */
public class ServerPlayer implements IPlayer{
    public static final ServerPlayer INSTANCE = new ServerPlayer();

    private ServerPlayer(){

    }

    @Override
    public String getId(){
        return "server";
    }

    @Override
    public String getName(){
        return "Server";
    }

    @Override
    public IData getData(){
        // TODO: implement
        return null;
    }

    @Override
    public void update(IData data){
        // TODO: implement
    }

    @Override
    public void sendChat(IChatMessage message){
        FMLServerHandler.instance().getServer().addChatMessage((ITextComponent) message.getInternal());
    }

    @Override
    public void sendChat(String message){
        FMLServerHandler.instance().getServer().addChatMessage(new TextComponentString(message));
    }

    @Override
    public int getHotbarSize(){
        return 0;
    }

    @Override
    public IItemStack getHotbarStack(int i){
        return null;
    }

    @Override
    public int getInventorySize(){
        return 0;
    }

    @Override
    public IItemStack getInventoryStack(int i){
        return null;
    }

    @Override
    public IItemStack getCurrentItem(){
        return null;
    }

    @Override
    public boolean isCreative(){
        return true;
    }

    @Override
    public boolean isAdventure(){
        return false;
    }

    @Override
    public void openBrowser(String url){
        if(Desktop.isDesktopSupported()){
            try{
                Desktop.getDesktop().browse(URI.create(url));
            }catch(IOException ex){
                Logger.getLogger(ServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void copyToClipboard(String value){
        if(Desktop.isDesktopSupported()){
            StringSelection stringSelection = new StringSelection(value);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    @Override
    public void give(IItemStack stack){

    }
}
