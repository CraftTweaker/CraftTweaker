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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Stan
 */
public class MineTweakerOpenBrowserHandler implements IMessageHandler<MineTweakerOpenBrowserPacket, IMessage> {
    @Override
    public IMessage onMessage(MineTweakerOpenBrowserPacket message, MessageContext ctx) {
        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(message.getUrl()));
            } catch(IOException | URISyntaxException ignored) {
            }
        } else {
            System.out.println("Desktop not supported");
        }

        return null;
    }
}
