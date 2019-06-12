package crafttweaker.mc1120.client;

import crafttweaker.api.client.IClient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.input.Keyboard;

/**
 * @author Stan
 */
public class MCClient implements IClient {

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(Minecraft.getMinecraft().player);
    }

    @Override
    public String getLanguage() {
        return FMLClientHandler.instance().getCurrentLanguage();
    }
    
    @Override
    public boolean isKeyDown(String keyName) {
        return Keyboard.isKeyDown(Keyboard.getKeyIndex(keyName));
    }
    
    @Override
    public boolean isKeyDown(int keyCode) {
        return Keyboard.isKeyDown(keyCode);
    }
}
