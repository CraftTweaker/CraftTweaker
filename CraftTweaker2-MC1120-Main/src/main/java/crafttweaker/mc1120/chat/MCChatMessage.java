package crafttweaker.mc1120.chat;

import crafttweaker.api.chat.IChatMessage;
import net.minecraft.util.text.*;

/**
 * @author Stan
 */
public class MCChatMessage implements IChatMessage {

    private final ITextComponent data;

    public MCChatMessage(String message) {
        data = new TextComponentString(message);
    }

    public MCChatMessage(ITextComponent data) {
        this.data = data;
    }

    @Override
    public IChatMessage add(IChatMessage other) {
        return new MCChatMessage(data.appendSibling((ITextComponent) other.getInternal()));
    }

    @Override
    public Object getInternal() {
        return data;
    }
    
    // TODO: REMVOE AGAIN
    
    @Override
    public int getBblabla(int num) {
        return 0;
    }
}
