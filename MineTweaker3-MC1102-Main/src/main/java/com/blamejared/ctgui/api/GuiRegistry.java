package com.blamejared.ctgui.api;

import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jared.
 */
public class GuiRegistry {

    public static Map<List<Integer>, IGuiHandler> guiMap = new HashMap<>();

    public static void registerGui(List<Integer> idRange, IGuiHandler handler) {
        getGuiMap().put(idRange, handler);
    }

    public static IGuiHandler getHandlerForID(int id) {
        for (Map.Entry<List<Integer>, IGuiHandler> entry : getGuiMap().entrySet()) {
            if (entry.getKey().contains(id)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static Map<List<Integer>, IGuiHandler> getGuiMap() {
        return guiMap;
    }
}
