package com.blamejared.ctgui.client.gui.craftingtable;

import com.blamejared.ctgui.api.*;
import net.minecraft.util.ResourceLocation;

import java.util.*;

/**
 * Created by Jared.
 */
public class GuiCraftingTable extends GuiBase {

    private CheckButton shaped;
    private CheckButton shapeless;
    private CheckButton mirror;
    public GuiCraftingTable(ContainerBase container) {
        super(container, 176, 166, true);
    }

    @Override
    public void initGui() {
        super.initGui();
        shaped = new CheckButton(this, 2910, getGuiLeft(), getGuiTop() + getYSize() + 4, "Shaped", true);
        shapeless = new CheckButton(this, 2911, getGuiLeft() + 54, getGuiTop() + getYSize() + 4, "Shapeless", false);
        mirror = new CheckButton(this, 2912, getGuiLeft() + 54 + 74, getGuiTop() + getYSize() + 4, "Mirror", false);
        shaped.setBackgroundSize(50);
        shapeless.setBackgroundSize(70);
        mirror.setBackgroundSize(50);

        getButtonList().add(shapeless);
        getButtonList().add(shaped);
        getButtonList().add(mirror);
        shaped.setIncompatible(Collections.singletonList(shapeless));
        shapeless.setIncompatible(Arrays.asList(shaped, mirror));
        mirror.setIncompatible(Collections.singletonList(remove));
        shaped.setIsChecked(true);

    }

    @Override
    public String getOutputAdd() {
        String type = "add";
        boolean shapel = false;
        if(shapeless.isChecked()) {
            type += "Shapeless";
            shapel = true;
        } else if(shaped.isChecked()) {
            type += "Shaped";
        }
        if(mirror.isChecked()) {
            type += "Mirrored";
        }
        if(shapel) {
            return String.format("recipes.%s(%s, [%s%s%s%s%s%s%s%s%s]);", type, container.getRecipeSlots().get(0).getItemString(), getShapelessItem(container.getRecipeSlots().get(1), true), getShapelessItem(container.getRecipeSlots().get(2), container.getRecipeSlots().get(1).getStack().isEmpty()), getShapelessItem(container.getRecipeSlots().get(3), container.getRecipeSlots().get(2).getStack().isEmpty()), getShapelessItem(container.getRecipeSlots().get(4), container.getRecipeSlots().get(3).getStack().isEmpty()), getShapelessItem(container.getRecipeSlots().get(5), container.getRecipeSlots().get(4).getStack().isEmpty()), getShapelessItem(container.getRecipeSlots().get(6), container.getRecipeSlots().get(5).getStack().isEmpty()), getShapelessItem(container.getRecipeSlots().get(7), container.getRecipeSlots().get(7).getStack().isEmpty()), getShapelessItem(container.getRecipeSlots().get(8), container.getRecipeSlots().get(7).getStack().isEmpty()), getShapelessItem(container.getRecipeSlots().get(9), container.getRecipeSlots().get(8).getStack().isEmpty()));
        }
        return String.format("recipes.%s(%s, [[%s, %s, %s],[%s, %s, %s], [%s, %s, %s]]);", type, container.getRecipeSlots().get(0).getItemString(), container.getRecipeSlots().get(1).getItemString(), container.getRecipeSlots().get(2).getItemString(), container.getRecipeSlots().get(3).getItemString(), container.getRecipeSlots().get(4).getItemString(), container.getRecipeSlots().get(5).getItemString(), container.getRecipeSlots().get(6).getItemString(), container.getRecipeSlots().get(7).getItemString(), container.getRecipeSlots().get(8).getItemString(), container.getRecipeSlots().get(9).getItemString());
    }

    private String getShapelessItem(SlotRecipe slot, boolean first) {
        String ret = "";
        if(slot.getStack().isEmpty()){
            return "";
        }
        if(slot.getHasStack()) {
            if(!first) {
                ret += ", ";
            }
            ret += slot.getItemString();
        }
        return ret.isEmpty() ? ret : ret;
    }

    @Override
    public String getOutputRemove() {
        boolean hasrecipe = false;
        for(int i = 1; i < container.getRecipeSlots().size(); i++) {
            SlotRecipe slot = container.getRecipeSlots().get(i);
            if(slot.getHasStack()) {
                hasrecipe = true;
            }
        }
    
        boolean shapel = shapeless.isChecked();
        if(shapel) {
            return String.format("recipes.removeShapeless(%s, [%s%s%s%s%s%s%s%s%s]);", container.getRecipeSlots().get(0).getItemString(), getShapelessItem(container.getRecipeSlots().get(1), true), getShapelessItem(container.getRecipeSlots().get(2), false), getShapelessItem(container.getRecipeSlots().get(3), false), getShapelessItem(container.getRecipeSlots().get(4), false), getShapelessItem(container.getRecipeSlots().get(5), false), getShapelessItem(container.getRecipeSlots().get(6), false), getShapelessItem(container.getRecipeSlots().get(7), false), getShapelessItem(container.getRecipeSlots().get(8), false), getShapelessItem(container.getRecipeSlots().get(9), false));
        }
        return hasrecipe ? String.format("recipes.removeShaped(%s, [[%s, %s, %s],[%s, %s, %s], [%s, %s, %s]]);", container.getRecipeSlots().get(0).getItemString(), container.getRecipeSlots().get(1).getItemString(), container.getRecipeSlots().get(2).getItemString(), container.getRecipeSlots().get(3).getItemString(), container.getRecipeSlots().get(4).getItemString(), container.getRecipeSlots().get(5).getItemString(), container.getRecipeSlots().get(6).getItemString(), container.getRecipeSlots().get(7).getItemString(), container.getRecipeSlots().get(8).getItemString(), container.getRecipeSlots().get(9).getItemString()) : String.format("recipes.remove(%s);", container.getRecipeSlots().get(0).getItemString());
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("textures/gui/container/crafting_table.png");
    }


}
