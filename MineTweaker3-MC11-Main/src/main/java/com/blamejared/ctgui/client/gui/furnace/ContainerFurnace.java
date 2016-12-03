package com.blamejared.ctgui.client.gui.furnace;

import com.blamejared.ctgui.api.ContainerBase;
import com.blamejared.ctgui.api.SlotRecipe;
import com.blamejared.ctgui.api.SlotRecipeOutput;
import com.blamejared.ctgui.client.InventoryFake;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;

/**
 * Created by Jared.
 */
public class ContainerFurnace extends ContainerBase {

    public IInventory inventory = new InventoryFake(1);
    public IInventory craftResult = new InventoryCraftResult();

    public ContainerFurnace(InventoryPlayer invPlayer) {
        //output
        this.addSlotToContainer(new SlotRecipeOutput(craftResult, 0, 116, 35));

        //Slots
        this.addSlotToContainer(new SlotRecipe(inventory, 0, 56, 17));

        //Player
        for(int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 142));
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 84 + y * 18));
            }
        }
    }


}
