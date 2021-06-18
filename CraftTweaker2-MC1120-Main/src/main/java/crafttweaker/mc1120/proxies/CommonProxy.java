package crafttweaker.mc1120.proxies;

import crafttweaker.mc1120.events.CommonEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    
    public void registerEvents(){
        MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
    }
    
    public void registerReloadListener(){}

    public void fixRecipeBook() {}
    
}
