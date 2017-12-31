package com.blamejared.ctgui.client;

import com.blamejared.ctgui.MTRecipe;
import com.blamejared.ctgui.api.GuiRegistry;
import com.blamejared.ctgui.client.gui.craftingtable.ContainerCraftingTable;
import com.blamejared.ctgui.client.gui.craftingtable.GuiCraftingTable;
import com.blamejared.ctgui.client.gui.furnace.ContainerFurnace;
import com.blamejared.ctgui.client.gui.furnace.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static com.blamejared.ctgui.reference.Reference.GUI_NAME_CRAFTING;
import static com.blamejared.ctgui.reference.Reference.GUI_NAME_FURNACE;

/**
 * Created by Jared.
 */
public class GuiHandler implements IGuiHandler {

    public GuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MTRecipe.INSTANCE, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiRegistry.getName(ID)) {
            case GUI_NAME_CRAFTING:
                return new ContainerCraftingTable(player.inventory);
            case GUI_NAME_FURNACE:
                return new ContainerFurnace(player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiRegistry.getName(ID)) {
            case GUI_NAME_CRAFTING:
                return new GuiCraftingTable(new ContainerCraftingTable(player.inventory));
            case GUI_NAME_FURNACE:
                return new GuiFurnace(new ContainerFurnace(player.inventory));
        }
        return null;
    }
}
