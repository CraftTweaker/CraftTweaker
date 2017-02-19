/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1112.chat;

import minetweaker.api.chat.IChatMessage;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Stan
 */
public class MCChatMessage implements IChatMessage{
    private final ITextComponent data;

    public MCChatMessage(String message){
        data = new TextComponentString(message);
    }

    public MCChatMessage(ITextComponent data){
        this.data = data;
    }

    @Override
    public IChatMessage add(IChatMessage other){
        return new MCChatMessage(data.appendSibling((ITextComponent) other.getInternal()));
    }

    @Override
    public Object getInternal(){
        return data;
    }
}
