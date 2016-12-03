package com.blamejared.ctgui;

import com.blamejared.ctgui.api.GuiRegistry;
import com.blamejared.ctgui.client.GuiHandlerVanilla;
import com.blamejared.ctgui.commands.CommandCTGUI;
import com.blamejared.ctgui.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.ArrayList;
import java.util.List;

import static com.blamejared.ctgui.reference.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, clientSideOnly = true, dependencies = "required-after:MineTweaker3;")
public class MTRecipe {

    @Mod.Instance(MOD_ID)
    public static MTRecipe INSTANCE;

    @SidedProxy(clientSide = "com.blamejared.ctgui.proxy.ClientProxy", serverSide = "com.blamejared.ctgui.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        List<Integer> idrange = new ArrayList<>();
        idrange.add(1);
        idrange.add(2);
        GuiRegistry.registerGui(idrange, new GuiHandlerVanilla());
        PROXY.registerGuis();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandCTGUI());
    }

}
