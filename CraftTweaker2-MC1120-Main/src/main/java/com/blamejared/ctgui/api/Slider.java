package com.blamejared.ctgui.api;

import net.minecraftforge.fml.client.config.GuiSlider;

/**
 * Created by Jared.
 */
public class Slider extends GuiSlider {

    private String propertyKey;

    public Slider(String propertyKey, int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr);
        this.propertyKey = propertyKey;
    }

    public Slider(String propertyKey, int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, par);
        this.propertyKey = propertyKey;
    }


    public String getPropertyKey() {
        return propertyKey;
    }
}
