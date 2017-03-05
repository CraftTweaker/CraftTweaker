package minetweaker.mods.jei;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "crafttweakerjei", name = "CraftTweaker JEI Support", version = "1.0.1", dependencies = "required-before:crafttweaker;")
public class JEIMod {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
        //		if(Loader.isModLoaded("JEI"))
        //			MineTweakerAPI.registerClass(JEI.class);
    }
}
