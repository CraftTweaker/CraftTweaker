package com.blamejared.ctgui.client;

import com.blamejared.ctgui.client.gui.craftingtable.ContainerCraftingTable;
import com.blamejared.ctgui.client.gui.craftingtable.GuiCraftingTable;
import com.blamejared.ctgui.client.gui.furnace.ContainerFurnace;
import com.blamejared.ctgui.client.gui.furnace.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Jared.
 */
public class GuiHandlerVanilla implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 1:
                return new ContainerCraftingTable(player.inventory);
            case 2:
                return new ContainerFurnace(player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 1:
                return new GuiCraftingTable(new ContainerCraftingTable(player.inventory));
            case 2:
                return new GuiFurnace(new ContainerFurnace(player.inventory));
        }
        return null;
    }
}
