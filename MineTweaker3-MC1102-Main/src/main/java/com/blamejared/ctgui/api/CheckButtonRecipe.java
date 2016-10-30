package com.blamejared.ctgui.api;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class CheckButtonRecipe extends CheckButton {

    private String propertyKey;

    public CheckButtonRecipe(GuiBase parent, int id, int xPos, int yPos, String displayString, boolean isChecked, String propertyKey) {
        super(parent, id, xPos, yPos, displayString, isChecked);
        this.propertyKey = propertyKey;
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
            setIsChecked(!isChecked());
            parent.selectedSlot.getPropertyMap().put(getPropertyKey(), isChecked());
            if (isChecked()) {
                for (Slider slider : parent.getMenu().getSliders(this)) {
                    if (GuiBase.isBlock(parent.selectedSlot.getStack())) {
                        Block b = Block.getBlockFromItem(parent.selectedSlot.getStack().getItem());
                        ArrayList<ItemStack> list = new ArrayList<>();
                        b.getSubBlocks(parent.selectedSlot.getStack().getItem(), CreativeTabs.SEARCH, list);
                        slider.setValue(parent.selectedSlot.getProperty(slider.getPropertyKey()));
                        slider.maxValue = list.size() - 1;
                        slider.updateSlider();
                    } else {
                        if (parent.selectedSlot.getStack().getItem().getHasSubtypes()) {
                            ArrayList<ItemStack> list = new ArrayList<>();
                            parent.selectedSlot.getStack().getItem().getSubItems(parent.selectedSlot.getStack().getItem(), CreativeTabs.SEARCH, list);
                            slider.setValue(parent.selectedSlot.getProperty(slider.getPropertyKey()));
                            slider.maxValue = list.size() - 1;
                            slider.updateSlider();
                        } else {
                            slider.setValue(parent.selectedSlot.getProperty(slider.getPropertyKey()));
                            slider.maxValue = parent.selectedSlot.getStack().getMaxDamage();
                            slider.updateSlider();
                        }
                    }
                    parent.getButtonList().add(slider);
                }
            } else {
                for (Slider slider : parent.getMenu().getSliders(this)) {
                    parent.getButtonList().remove(slider);
                }
            }
            return true;
        }
        return false;
    }


    public void setIsChecked(boolean isChecked) {
        super.setIsChecked(isChecked);
        if (isChecked()) {
            for (Slider slider : parent.getMenu().getSliders(this)) {
                if (GuiBase.isBlock(parent.selectedSlot.getStack())) {
                    Block b = Block.getBlockFromItem(parent.selectedSlot.getStack().getItem());
                    ArrayList<ItemStack> list = new ArrayList<>();
                    b.getSubBlocks(parent.selectedSlot.getStack().getItem(), CreativeTabs.SEARCH, list);
                    slider.setValue(parent.selectedSlot.getProperty(slider.getPropertyKey()));
                    slider.maxValue = list.size() - 1;
                    slider.updateSlider();
                } else {
                    if (parent.selectedSlot.getStack().getItem().getHasSubtypes()) {
                        ArrayList<ItemStack> list = new ArrayList<>();
                        parent.selectedSlot.getStack().getItem().getSubItems(parent.selectedSlot.getStack().getItem(), CreativeTabs.SEARCH, list);
                        slider.setValue(parent.selectedSlot.getProperty(slider.getPropertyKey()));
                        slider.maxValue = list.size() - 1;
                        slider.updateSlider();
                    } else {
                        slider.setValue(parent.selectedSlot.getProperty(slider.getPropertyKey()));
                        slider.maxValue = parent.selectedSlot.getStack().getMaxDamage();
                        slider.updateSlider();
                    }
                }
                parent.getButtonList().add(slider);
            }
            for (GuiButton but : incompatible) {
                but.enabled = false;
            }
        } else {
            for (Slider slider : parent.getMenu().getSliders(this)) {
                parent.getButtonList().remove(slider);
            }
            for (GuiButton but : incompatible) {
                but.enabled = true;
            }
        }
    }

    public String getPropertyKey() {
        return propertyKey;
    }
}
