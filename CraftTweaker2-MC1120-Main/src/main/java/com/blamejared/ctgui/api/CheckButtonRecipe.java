package com.blamejared.ctgui.api;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CheckButtonRecipe extends CheckButton {

    private String propertyKey;

    public CheckButtonRecipe(GuiBase parent, int id, int xPos, int yPos, String displayString, boolean isChecked, String propertyKey) {
        super(parent, id, xPos, yPos, displayString, isChecked);
        this.propertyKey = propertyKey;
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) {
            setIsChecked(!isChecked());
            parent.selectedSlot.getPropertyMap().put(getPropertyKey(), isChecked());
            if(isChecked()) {
                updateSlider();
            } else {
                for(Slider slider : parent.getMenu().getSliders(this)) {
                    parent.getButtonList().remove(slider);
                }
            }
            return true;
        }
        return false;
    }
    
    private void updateSlider() {
        for(Slider slider : parent.getMenu().getSliders(this)) {
            if(GuiBase.isBlock(parent.selectedSlot.getStack())) {
                Block b = Block.getBlockFromItem(parent.selectedSlot.getStack().getItem());
                NonNullList<ItemStack> list = NonNullList.create();
                b.getSubBlocks(CreativeTabs.SEARCH, list);
                slider.setValue(parent.selectedSlot.getProperty(slider.getPropertyKey()));
                slider.maxValue = list.size() - 1;
                slider.updateSlider();
            } else {
                if(parent.selectedSlot.getStack().getItem().getHasSubtypes()) {
                    NonNullList<ItemStack> list = NonNullList.create();
                    parent.selectedSlot.getStack().getItem().getSubItems(CreativeTabs.SEARCH, list);
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
    }
    
    @Override
    public void setIsChecked(boolean isChecked) {
        super.setIsChecked(isChecked);
        if(isChecked()) {
            updateSlider();
            for(GuiButton but : incompatible) {
                but.enabled = false;
            }
        } else {
            for(Slider slider : parent.getMenu().getSliders(this)) {
                parent.getButtonList().remove(slider);
            }
            for(GuiButton but : incompatible) {
                but.enabled = true;
            }
        }
    }

    public String getPropertyKey() {
        return propertyKey;
    }
}
