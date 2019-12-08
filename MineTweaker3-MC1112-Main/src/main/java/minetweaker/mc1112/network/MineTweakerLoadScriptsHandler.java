package minetweaker.mc1112.network;

import minetweaker.*;
import minetweaker.mc1112.client.MCClient;
import minetweaker.runtime.providers.ScriptProviderMemory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * @author Stan
 */
public class MineTweakerLoadScriptsHandler implements IMessageHandler<MineTweakerLoadScriptsPacket, IMessage> {

    @Override
    public IMessage onMessage(MineTweakerLoadScriptsPacket message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
        return null;
    }

    private void handle(MineTweakerLoadScriptsPacket message, MessageContext ctx) {
        if(MineTweakerAPI.server == null) {
            MineTweakerAPI.client = new MCClient();

            MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderMemory(message.getData()));
            MineTweakerImplementationAPI.reload();
        }
    }
}
