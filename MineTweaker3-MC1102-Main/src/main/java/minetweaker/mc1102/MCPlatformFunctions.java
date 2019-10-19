package minetweaker.mc1102;

import minetweaker.IPlatformFunctions;
import minetweaker.api.chat.IChatMessage;
import minetweaker.api.item.IItemDefinition;
import minetweaker.mc1102.chat.MCChatMessage;
import minetweaker.mc1102.item.MCItemDefinition;
import minetweaker.mc1102.network.MineTweakerLoadScriptsPacket;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import static minetweaker.mc1102.MineTweakerMod.NETWORK;

/**
 * @author Stan
 */
public class MCPlatformFunctions implements IPlatformFunctions {

    public static final MCPlatformFunctions INSTANCE = new MCPlatformFunctions();

    private MCPlatformFunctions() {
    }

    @Override
    public IChatMessage getMessage(String message) {
        return new MCChatMessage(message);
    }

    @Override
    public void distributeScripts(byte[] data) {
        NETWORK.sendToAll(new MineTweakerLoadScriptsPacket(data));
    }

    @Override
    public IItemDefinition getItemDefinition(int id) {
        Item item = Item.getItemById(id);
        if(item == null)
            return null;
        ResourceLocation res = Item.REGISTRY.getNameForObject(item);
        String sid = res.getResourceDomain() + ":" + res.getResourcePath();
        return new MCItemDefinition(sid, item);
    }
}
