/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1112.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * @author Stan
 */
public class MineTweakerLoadScriptsPacket implements IMessage{
    private byte[] data;

    public MineTweakerLoadScriptsPacket(){
        // used for deserialization
    }

    public MineTweakerLoadScriptsPacket(byte[] data){
        this.data = data;
    }

    public byte[] getData(){
        return data;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        int size = buf.readInt();
        data = new byte[size];
        buf.readBytes(data);
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(data.length);
        buf.writeBytes(data);
    }
}
