package minetweaker.mc1102.client;

import minetweaker.api.client.IClient;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;

/**
 * @author Stan
 */
public class MCClient implements IClient {

    @Override
    public IPlayer getPlayer() {
        return MineTweakerMC.getIPlayer(Minecraft.getMinecraft().player);
    }

    @Override
    public String getLanguage() {
        return FMLClientHandler.instance().getCurrentLanguage();
    }
}
