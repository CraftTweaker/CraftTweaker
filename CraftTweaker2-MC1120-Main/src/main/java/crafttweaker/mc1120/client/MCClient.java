package crafttweaker.mc1120.client;

import crafttweaker.api.client.IClient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IEntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;

/**
 * @author Stan
 */
public class MCClient implements IClient {

    @Override
    public IEntityPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(Minecraft.getMinecraft().player);
    }

    @Override
    public String getLanguage() {
        return FMLClientHandler.instance().getCurrentLanguage();
    }
}
