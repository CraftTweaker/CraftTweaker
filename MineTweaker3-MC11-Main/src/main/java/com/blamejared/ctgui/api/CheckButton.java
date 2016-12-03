package com.blamejared.ctgui.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class CheckButton extends GuiCheckBox {

    protected GuiBase parent;
    protected List<GuiButton> incompatible;

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

    public void setIncompatible(List<GuiButton> incompatible) {
        this.incompatible = incompatible;
    }

    public List<GuiButton> getIncompatible() {
        return incompatible;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + getButtonWidth() && mouseY < this.yPosition + this.height;
            GlStateManager.enableAlpha();
            GL11.glColor4d(1, 1, 1, 0);
            GuiUtils.drawGradientRect(30, this.xPosition - 2, this.yPosition - 2, this.xPosition + getBackgroundSize(), this.yPosition + height + 2, 0x9F100010, 0x9F100010);
            GlStateManager.disableAlpha();
            GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.xPosition, this.yPosition, 0, 46, 11, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;

            if (packedFGColour != 0) {
                color = packedFGColour;
            } else if (!this.enabled) {
                color = 10526880;
            }

            if (this.isChecked()) {
                this.drawGradientRect(this.xPosition + 2, this.yPosition + 2, this.xPosition + 11 - 2, this.yPosition + height - 2, Color.cyan.darker().getRGB(), Color.cyan.darker().getRGB());
            }

            this.drawString(mc.fontRendererObj, displayString, xPosition + 11 + 2, yPosition + 2, color);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
            setIsChecked(!isChecked());
            return true;
        }
        return false;
    }


    public void setIsChecked(boolean isChecked) {
        super.setIsChecked(isChecked);
        if (isChecked()) {
            for (GuiButton but : incompatible) {
                but.enabled = false;
                if (but instanceof CheckButton) {
                    ((CheckButton) but).setIsChecked(false);
                }
            }
        } else {
            for (GuiButton but : incompatible) {
                but.enabled = true;
            }
        }
    }

}
