package com.blamejared.ctgui.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CheckButton extends GuiCheckBox {

    protected GuiBase parent;
    protected List<GuiButton> incompatible = new ArrayList<>();

    protected int backgroundSize = 100;

    public CheckButton(GuiBase parent, int id, int xPos, int yPos, String displayString, boolean isChecked) {
        super(id, xPos, yPos, displayString, isChecked);
        this.parent = parent;
    }


    public int getBackgroundSize() {
        return backgroundSize;
    }

    public void setBackgroundSize(int backgroundSize) {
        this.backgroundSize = backgroundSize;
    }

    public List<GuiButton> getIncompatible() {
        return incompatible;
    }

    public void setIncompatible(List<GuiButton> incompatible) {
        this.incompatible = incompatible;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + getButtonWidth() && mouseY < this.y + this.height;
            GlStateManager.enableAlpha();
            GL11.glColor4d(1, 1, 1, 0);
            GuiUtils.drawGradientRect(30, this.x - 2, this.y - 2, this.x + getBackgroundSize(), this.y + height + 2, 0x9F100010, 0x9F100010);
            GlStateManager.disableAlpha();
            GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46, 11, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;

            if(packedFGColour != 0) {
                color = packedFGColour;
            } else if(!this.enabled) {
                color = 10526880;
            }

            if(this.isChecked()) {
                this.drawGradientRect(this.x + 2, this.y + 2, this.x + 11 - 2, this.y + height - 2, Color.cyan.darker().getRGB(), Color.cyan.darker().getRGB());
            }

            this.drawString(mc.fontRenderer, displayString, x + 11 + 2, y + 2, color);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) {
            setIsChecked(!isChecked());
            return true;
        }
        return false;
    }


    public void setIsChecked(boolean isChecked) {
        super.setIsChecked(isChecked);
        if(isChecked()) {
            for(GuiButton but : incompatible) {
                but.enabled = false;
                if(but instanceof CheckButton) {
                    ((CheckButton) but).setIsChecked(false);
                }
            }
        } else {
            for(GuiButton but : incompatible) {
                but.enabled = true;
            }
        }
    }

}
