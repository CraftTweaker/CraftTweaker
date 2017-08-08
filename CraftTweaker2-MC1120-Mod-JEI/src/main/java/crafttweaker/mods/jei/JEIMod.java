package crafttweaker.mods.jei;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;

import static crafttweaker.mods.jei.JEI.LATE_HIDING;

@Mod(modid = "crafttweakerjei", name = "CraftTweaker JEI Support", version = "2.0.0", dependencies = "after:jei;")
public class JEIMod {
    
    
    @Mod.EventHandler
    public void onFMLLoadComplete(FMLLoadCompleteEvent event) {
        if(Loader.isModLoaded("jei"))
            LATE_HIDING.forEach(CraftTweakerAPI::apply);
    }
}
