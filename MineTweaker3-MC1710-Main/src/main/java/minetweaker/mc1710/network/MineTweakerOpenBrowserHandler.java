/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Stan
 */
public class MineTweakerOpenBrowserHandler implements IMessageHandler<MineTweakerOpenBrowserPacket, IMessage> {
	@Override
	public IMessage onMessage(MineTweakerOpenBrowserPacket message, MessageContext ctx) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(message.getUrl()));
			} catch (IOException e) {
			} catch (URISyntaxException e) {
			}
		} else {
			System.out.println("Desktop not supported");
		}

		return null;
	}
}
