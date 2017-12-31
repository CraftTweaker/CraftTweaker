package com.blamejared.ctgui;

import com.blamejared.ctgui.api.GuiRegistry;
import com.blamejared.ctgui.commands.CommandCTGUI;
import com.blamejared.ctgui.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import static com.blamejared.ctgui.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, clientSideOnly = true, dependencies = "required-after:crafttweaker;", acceptedMinecraftVersions = "[1.12, 1.13)")
public class MTRecipe {

    @Mod.Instance(MOD_ID)
    public static MTRecipe INSTANCE;

    @SidedProxy(clientSide = "com.blamejared.ctgui.proxy.ClientProxy", serverSide = "com.blamejared.ctgui.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PROXY.registerGuis();
        PROXY.registerEvents();
        GuiRegistry.registerGuis(GUI_HANDLED.toArray(new String[0]));

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandCTGUI());
    }

}
