package com.blamejared.ctgui.api;

import com.blamejared.ctgui.client.gui.GuiMenu;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public abstract class GuiBase extends GuiContainer {

    protected ContainerBase container;
    public SlotRecipe selectedSlot;
    protected GuiMenu menu = new GuiMenu();
    public GuiTextField editingField;

    public GuiButton add;
    public GuiButton remove;

    private boolean shouldOpenMenu;

    public GuiBase(ContainerBase container, int xSize, int ySize, boolean shouldOpenMenu) {
        super(container);
        this.container = container;
        this.xSize = xSize;
        this.ySize = ySize;
        this.shouldOpenMenu = shouldOpenMenu;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        if(shouldOpenMenu)
            menu.open(this, guiLeft - 102, guiTop + 2);
        editingField = new GuiTextField(2906, fontRendererObj, guiLeft - 104, guiTop - 26, xSize + 102, 20);
        editingField.setMaxStringLength(Integer.MAX_VALUE);
        editingField.setEnabled(true);
        add = new GuiButton(2906, getGuiLeft() + 86, getGuiTop() + 8, fontRendererObj.getStringWidth("Add") + 4, 20, "Add");
        getButtonList().add(add);
        remove = new GuiButton(2907, getGuiLeft() + 86 + 2 + fontRendererObj.getStringWidth("Add") + 4, getGuiTop() + 8, fontRendererObj.getStringWidth("Remove") + 4, 20, "Remove");
        getButtonList().add(remove);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        editingField.drawTextBox();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if(selectedSlot != null) {
            if(!selectedSlot.getHasStack()) {
                selectedSlot = null;
                editingField.setText("");
                if(shouldOpenMenu) {
                    menu.setState(false);
                    menu.toggle(false);
                    for(Pair<CheckButtonRecipe, Slider[]> but : menu.getButtons()) {
                        for(Slider slider : but.getValue()) {
                            slider.setValue(0);
                            slider.maxValue = 0;
                            slider.updateSlider();
                            buttonList.remove(slider);
                        }
                    }
                }
            } else {
                editingField.setText(selectedSlot.getItemString());
            }
        } else {
            editingField.setText("");
            if(shouldOpenMenu) {
                menu.setState(false);
                menu.toggle(false);
                for(Pair<CheckButtonRecipe, Slider[]> but : menu.getButtons()) {
                    for(Slider slider : but.getValue()) {
                        slider.setValue(0);
                        slider.maxValue = 0;
                        slider.updateSlider();
                        buttonList.remove(slider);
                    }
                }
            }

        }
        editingField.updateCursorCounter();
        final boolean[] canCreate = {true};
        container.getRecipeSlots().stream().filter(slot -> slot instanceof SlotRecipeOutput).filter(slot -> !slot.getHasStack()).forEach(slot -> canCreate[0] = false);
        if(!canCreate[0]) {
            add.enabled = false;
            remove.enabled = false;
        } else {
            add.enabled = true;
            remove.enabled = true;
        }
        if(shouldOpenMenu) {
            if(selectedSlot != null) {
                if(selectedSlot.getHasStack() && !selectedSlot.getStack().hasTagCompound()) {
                    menu.nbt.getLeft().enabled = false;
                }
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        final boolean[] clickedSlot = new boolean[]{false};
        for(SlotRecipe slot : container.getRecipeSlots()) {
            Rectangle rectangle = new Rectangle(guiLeft + slot.xDisplayPosition, guiTop + slot.yDisplayPosition, 18, 18);
            if(rectangle.contains(mouseX, mouseY)) {
                switch(mouseButton) {
                    case 0:
                        break;
                    case 1:
                        if(slot.getHasStack()) {
                            clickedSlot[0] = true;
                            selectedSlot = slot;
                            editingField.setText(slot.getItemString());
                            if(shouldOpenMenu) {
                                menu.toggle(true);
                                for(Pair<CheckButtonRecipe, Slider[]> but : menu.getButtons()) {
                                    for(Slider slider : but.getValue()) {
                                        buttonList.remove(slider);
                                    }
                                }
                                menu.getFromMap(slot.getPropertyMap(), slot.getProperties());
                            }
                        }
                        break;
                    case 2:
                        clickedSlot[0] = true;
                        selectedSlot = null;
                        if(shouldOpenMenu) {
                            menu.toggle(false);
                            for(Pair<CheckButtonRecipe, Slider[]> but : menu.getButtons()) {
                                for(Slider slider : but.getValue()) {
                                    slider.updateSlider();
                                    buttonList.remove(slider);
                                }
                            }
                        }
                        if(editingField != null)
                            editingField.setText("");
                        break;
                }
            }

        }
        if(!clickedSlot[0]) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public abstract String getOutputAdd();

    public abstract String getOutputRemove();


    protected void actionPerformed(GuiButton btn) {
        if(btn.equals(add)) {
            File scriptFile = new File(new File("scripts"), "/recipes.zs");
            if(!scriptFile.exists()) {
                generateFile(scriptFile);
            }
            try {
                List<String> lines = new LinkedList<>();
                BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
                String line;
                while((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                if(lines.isEmpty()) {
                    generateFile(scriptFile);
                    while((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }
                reader.close();
                PrintWriter writer = new PrintWriter(new FileWriter(scriptFile));
                for(int i = 0; i < lines.size(); i++) {
                    String beforeLine = "";
                    if(i > 0)
                        beforeLine = lines.get(i - 1);

                    String lined = lines.get(i);
                    if(beforeLine.trim().equals("//#Add")) {
                        writer.println(getOutputAdd());
                    }
                    if(!lined.isEmpty()) {
                        writer.println(lined);
                    }

                }
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        if(btn.equals(remove)) {
            File scriptFile = new File(new File("scripts"), "/recipes.zs");
            if(!scriptFile.exists()) {
                generateFile(scriptFile);
            }

            try {
                List<String> lines = new LinkedList<>();
                BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
                String line;
                while((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                if(lines.isEmpty()) {
                    generateFile(scriptFile);
                    while((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }
                reader.close();
                PrintWriter writer = new PrintWriter(new FileWriter(scriptFile));
                for(int i = 0; i < lines.size(); i++) {
                    String beforeLine = "";
                    if(i > 0)
                        beforeLine = lines.get(i - 1);

                    String lined = lines.get(i);
                    if(beforeLine.trim().equals("//#Remove")) {
                        writer.println(getOutputRemove());
                    }
                    if(!lined.isEmpty()) {
                        writer.println(lined);
                    }
                }
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }


    public abstract ResourceLocation getTexture();

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.pushAttrib();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(getTexture());
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GlStateManager.popAttrib();
        if(selectedSlot != null) {
            GlStateManager.colorMask(true, true, true, false);
            this.drawGradientRect(guiLeft + selectedSlot.xDisplayPosition, guiTop + selectedSlot.yDisplayPosition, guiLeft + selectedSlot.xDisplayPosition + 16, guiTop + selectedSlot.yDisplayPosition + 16, Color.cyan.darker().getRGB(), Color.cyan.darker().getRGB());
            GlStateManager.colorMask(true, true, true, true);
        }
    }

    public List<GuiButton> getButtonList() {
        return this.buttonList;
    }


    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }


    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public static boolean isBlock(ItemStack stack) {
        ResourceLocation name = Block.REGISTRY.getNameForObject(Block.getBlockFromItem(stack.getItem()));
        return !(name != null && name.toString().equals("minecraft:air")) && Block.REGISTRY.containsKey(name);
    }

    public GuiMenu getMenu() {
        return menu;
    }


    public void generateFile(File f) {
        try {
            f.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(f));
            writer.println("//This file was created via CT-GUI! Editing it is not advised!");
            writer.println("//Don't touch me!");
            writer.println("//#Remove");
            writer.println();
            writer.println("//Don't touch me!");
            writer.println("//#Add");
            writer.println();
            writer.println("//File End");
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
