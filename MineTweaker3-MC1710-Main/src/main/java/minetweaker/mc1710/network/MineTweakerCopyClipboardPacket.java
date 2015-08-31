/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.network;

import static com.google.common.base.Charsets.UTF_8;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;

/**
 *
 * @author Stan
 */
public class MineTweakerCopyClipboardPacket implements IMessage
{
	private String data;

	public MineTweakerCopyClipboardPacket() {
	}

	public MineTweakerCopyClipboardPacket(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		data = UTF_8.decode(ByteBuffer.wrap(buf.array())).toString().trim();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBytes(UTF_8.encode(data).array());
	}
}
