package minetweaker.mods.jei;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "crafttweakerjei", name = "CraftTweaker JEI Support", version = "1.0.1", dependencies = "required-before:crafttweaker;")
public class JEIMod {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
        if(Loader.isModLoaded("jei")) {
            MineTweakerAPI.registerClass(JEI.class);
        }
    }
}
