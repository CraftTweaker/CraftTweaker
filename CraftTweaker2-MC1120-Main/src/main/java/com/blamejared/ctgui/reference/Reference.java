package com.blamejared.ctgui.reference;

import scala.actors.threadpool.Arrays;

import java.util.List;

public class Reference {
    
    public static final String MOD_ID = "ctgui";
    public static final String MOD_NAME = "CT-GUI";
    public static final String VERSION = "1.0.0";
    
    
    public static final String GUI_NAME_CRAFTING = "craftingtable";
    public static final String GUI_NAME_FURNACE = "furnace";
    
    public static final List<String> GUI_HANDLED = Arrays.asList(new String[]{GUI_NAME_CRAFTING, GUI_NAME_FURNACE});
    
}
