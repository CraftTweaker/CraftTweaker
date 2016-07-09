/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * @author Stan
 */
public class MineTweakerCopyClipboardHandler implements IMessageHandler<MineTweakerCopyClipboardPacket, IMessage>{
    @Override
    public IMessage onMessage(MineTweakerCopyClipboardPacket message, MessageContext ctx){
        if(Desktop.isDesktopSupported()){
            StringSelection stringSelection = new StringSelection(message.getData());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }

        return null;
    }
}
