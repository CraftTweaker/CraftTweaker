package crafttweaker.mc1120.proxies;

import crafttweaker.*;
import crafttweaker.mc1120.events.ClientEventHandler;
import crafttweaker.mc1120.game.MCGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.client.resource.*;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerEvents() {
        super.registerEvents();
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
    
    @Override
    public void registerReloadListener() {
        super.registerReloadListener();
        IReloadableResourceManager manager = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
        manager.registerReloadListener((ISelectiveResourceReloadListener) (resourceManager, resourcePredicate) -> {
            if(resourcePredicate.test(VanillaResourceType.LANGUAGES)) {
                for(IAction action : MCGame.TRANSLATION_ACTIONS) {
                    CraftTweakerAPI.apply(action);
                }
            }
        });
    }
}
