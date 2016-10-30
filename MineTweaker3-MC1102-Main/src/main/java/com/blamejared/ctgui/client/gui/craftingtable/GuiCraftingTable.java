package com.blamejared.ctgui.client.gui.craftingtable;

import com.blamejared.ctgui.api.CheckButton;
import com.blamejared.ctgui.api.ContainerBase;
import com.blamejared.ctgui.api.GuiBase;
import com.blamejared.ctgui.api.SlotRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Jared.
 */
public class GuiCraftingTable extends GuiBase {

    public GuiCraftingTable(ContainerBase container) {
        super(container, 176, 166, true);
    }

    private CheckButton shaped;
    private CheckButton shapeless;
    private CheckButton mirror;

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
        if (shapeless.isChecked()) {
            type += "Shapeless";
            shapel = true;
        } else if (shaped.isChecked()) {
            type += "Shaped";
        }
        if (mirror.isChecked()) {
            type += "Mirrored";
        }
        if (shapel) {
            return String.format("recipes.%s(%s, [%s%s%s%s%s%s%s%s%s]);", type, container.getRecipeSlots().get(0).getItemString(), getShapelessItem(container.getRecipeSlots().get(1)), getShapelessItem(container.getRecipeSlots().get(2)), getShapelessItem(container.getRecipeSlots().get(3)), getShapelessItem(container.getRecipeSlots().get(4)), getShapelessItem(container.getRecipeSlots().get(5)), getShapelessItem(container.getRecipeSlots().get(6)), getShapelessItem(container.getRecipeSlots().get(7)), getShapelessItem(container.getRecipeSlots().get(8)), getShapelessItem(container.getRecipeSlots().get(9)));
        }
        return String.format("recipes.%s(%s, [[%s, %s, %s],[%s, %s, %s], [%s, %s, %s]]);", type, container.getRecipeSlots().get(0).getItemString(), container.getRecipeSlots().get(1).getItemString(), container.getRecipeSlots().get(2).getItemString(), container.getRecipeSlots().get(3).getItemString(), container.getRecipeSlots().get(4).getItemString(), container.getRecipeSlots().get(5).getItemString(), container.getRecipeSlots().get(6).getItemString(), container.getRecipeSlots().get(7).getItemString(), container.getRecipeSlots().get(8).getItemString(), container.getRecipeSlots().get(9).getItemString());
    }

    private String getShapelessItem(SlotRecipe slot) {
        String ret = "";
        if (slot.getHasStack()) {
            ret += "," + slot.getItemString();
        }
        return ret.isEmpty() ? ret : ret.substring(1);
    }

    @Override
    public String getOutputRemove() {
        boolean hasrecipe = false;
        for (int i = 1; i < container.getRecipeSlots().size(); i++) {
            SlotRecipe slot = container.getRecipeSlots().get(i);
            if (slot.getHasStack()) {
                hasrecipe = true;
            }
        }

        String type = "remove";
        boolean shapel = false;
        if (hasrecipe)
            if (shapeless.isChecked()) {
                type += "Shapeless";
                shapel = true;
            } else if (shaped.isChecked()) {
                type += "Shaped";
            }
        if (shapel) {
            return String.format("recipes.%s(%s, [%s%s%s%s%s%s%s%s%s]);", type, container.getRecipeSlots().get(0).getItemString(), getShapelessItem(container.getRecipeSlots().get(1)), getShapelessItem(container.getRecipeSlots().get(2)), getShapelessItem(container.getRecipeSlots().get(3)), getShapelessItem(container.getRecipeSlots().get(4)), getShapelessItem(container.getRecipeSlots().get(5)), getShapelessItem(container.getRecipeSlots().get(6)), getShapelessItem(container.getRecipeSlots().get(7)), getShapelessItem(container.getRecipeSlots().get(8)), getShapelessItem(container.getRecipeSlots().get(9)));
        }
        return hasrecipe ? String.format("recipes.%s(%s, [[%s, %s, %s],[%s, %s, %s], [%s, %s, %s]]);", type, container.getRecipeSlots().get(0).getItemString(), container.getRecipeSlots().get(1).getItemString(), container.getRecipeSlots().get(2).getItemString(), container.getRecipeSlots().get(3).getItemString(), container.getRecipeSlots().get(4).getItemString(), container.getRecipeSlots().get(5).getItemString(), container.getRecipeSlots().get(6).getItemString(), container.getRecipeSlots().get(7).getItemString(), container.getRecipeSlots().get(8).getItemString(), container.getRecipeSlots().get(9).getItemString()) : String.format("recipes.removeShaped(%s);", container.getRecipeSlots().get(0).getItemString());
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("textures/gui/container/crafting_table.png");
    }


}
