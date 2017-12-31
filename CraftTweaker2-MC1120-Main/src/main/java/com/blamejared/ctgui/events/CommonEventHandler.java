package com.blamejared.ctgui.events;

import com.blamejared.ctgui.MTRecipe;
import com.blamejared.ctgui.api.GuiRegistry;
import com.blamejared.ctgui.api.events.CTGUIEvent;
import com.blamejared.ctgui.reference.Reference;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {

    @SubscribeEvent
    public void onCTGUI(CTGUIEvent event) {
        if (Reference.GUI_HANDLED.contains(event.getGuiName()))
            event.getPlayer().openGui(MTRecipe.INSTANCE, GuiRegistry.getID(event.getGuiName()), event.getWorld(), 0, 0, 0);
    }
}
