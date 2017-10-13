package com.blamejared.ctgui.api;

import com.google.common.collect.*;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.*;

/**
 * Created by Jared.
 */
public class GuiRegistry {
    
    public static BiMap<String, Integer> guiMap = HashBiMap.create();
    
    public static void registerGui(String name) {
        registerGui(name, guiMap.size() - 1);
    }
    
    public static void registerGuis(String... names) {
        for(String s : names) {
            registerGui(s, guiMap.size() - 1);
        }
    }
    
    public static void registerGui(String name, int ID) {
        guiMap.put(name, ID);
    }
    
    public static int getID(String name) {
        return guiMap.getOrDefault(name, Integer.MIN_VALUE);
    }
    
    public static String getName(int id) {
        return guiMap.inverse().get(id);
    }
    
    
    public static Map<String, Integer> getGuiMap() {
        return guiMap;
    }
}
