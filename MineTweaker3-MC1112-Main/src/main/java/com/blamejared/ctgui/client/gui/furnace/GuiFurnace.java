package com.blamejared.ctgui.client.gui.furnace;

import com.blamejared.ctgui.api.ContainerBase;
import com.blamejared.ctgui.api.GuiBase;
import com.blamejared.ctgui.api.Slider;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Jared.
 */
public class GuiFurnace extends GuiBase {
    private Slider xpSlider;

    public GuiFurnace(ContainerBase container) {
        super(container, 176, 166, false);
    }

    @Override
    public void initGui() {
        super.initGui();
        xpSlider = new Slider("slider", 10, getGuiLeft() - 102 - 2, getGuiTop() + getYSize() + 20, getXSize() + 102, 20, "XP= ", "", 0, 50, 0, true, true);
        getButtonList().add(xpSlider);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!container.getRecipeSlots().get(1).getHasStack()) {
            add.enabled = false;
            remove.enabled = false;
        }
    }

    @Override
    public String getOutputAdd() {
        return String.format("furnace.addRecipe(%s, %s, %s);", container.getRecipeSlots().get(0).getItemString(), container.getRecipeSlots().get(1).getItemString(), xpSlider.getValue() + 0f);
    }

    @Override
    public String getOutputRemove() {
        return String.format("furnace.remove(%s%s);", container.getRecipeSlots().get(0).getItemString(), container.getRecipeSlots().get(1).getHasStack() ? ", " + container.getRecipeSlots().get(1).getItemString() : "");
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("textures/gui/container/furnace.png");
    }

}
