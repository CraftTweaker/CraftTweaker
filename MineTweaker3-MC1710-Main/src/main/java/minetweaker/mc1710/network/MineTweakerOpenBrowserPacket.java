/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Stan
 */
public class MineTweakerOpenBrowserPacket implements IMessage {
	private static final Charset UTF8 = Charset.forName("utf-8");

	private String url;

	public MineTweakerOpenBrowserPacket() {

	}

	public MineTweakerOpenBrowserPacket(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		url = UTF8.decode(ByteBuffer.wrap(buf.array())).toString().trim();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBytes(UTF8.encode(url).array());
	}
}
