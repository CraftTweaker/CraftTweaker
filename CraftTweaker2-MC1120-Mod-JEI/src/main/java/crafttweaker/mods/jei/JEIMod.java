package crafttweaker.mods.jei;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import static crafttweaker.mods.jei.JEI.*;

@Mod(modid = "crafttweakerjei", name = "CraftTweaker JEI Support", version = "2.0.0", dependencies = "after:jei;")
public class JEIMod {
    
	@Mod.EventHandler
    public void onFMLLoadComplete(FMLLoadCompleteEvent event) {
        if(Loader.isModLoaded("jei")) {
            try {
                LATE_ACTIONS.forEach(CraftTweakerAPI::apply);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void onRegistered(){
		DESCRIPTIONS.forEach(CraftTweakerAPI::apply);
	}
}
