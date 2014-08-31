/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.mc164.client.MCClient;
import minetweaker.runtime.providers.ScriptProviderMemory;

public class MCPacketHandler implements IPacketHandler {
	public static final Charset UTF8 = Charset.forName("utf-8");
	
	public static final String CHANNEL_SERVERSCRIPT = "MTServerScript";
	public static final String CHANNEL_OPENBROWSER = "MTOpenBrowser";
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		// catches some special cases caused by unknown reasons
		if (packet == null || packet.channel == null || packet.data == null) return;
		
		if (packet.channel.equals(CHANNEL_SERVERSCRIPT)) {
			System.out.println("Received script data: " + packet.data.length + " bytes");
			
			if (MineTweakerAPI.server == null) {
				MineTweakerAPI.client = new MCClient();
				
				MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderMemory(packet.data));
				MineTweakerImplementationAPI.reload();
			}
		} else if (packet.channel.equals(CHANNEL_OPENBROWSER)) {
			String url = UTF8.decode(ByteBuffer.wrap(packet.data)).toString().trim();
			if(Desktop.isDesktopSupported()){
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI(url));
				} catch (IOException e) {
				} catch (URISyntaxException e) {
				}
			}
		}
	}
}
