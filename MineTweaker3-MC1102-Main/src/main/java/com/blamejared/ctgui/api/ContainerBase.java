package com.blamejared.ctgui.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class ContainerBase extends Container {

    protected List<SlotRecipe> recipeSlots = new LinkedList<>();

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }


    @Nullable
    @Override
    public ItemStack slotClick(int i, int mousebtn, ClickType clickTypeIn, EntityPlayer player) {
        ItemStack stack = null;

        if (i >= 0 && getSlot(i) != null && getSlot(i) instanceof SlotRecipe) // Fake slots
        {
            if (mousebtn == 2) {
                getSlot(i).putStack(null);
            } else if (mousebtn == 0) {
                InventoryPlayer playerInv = player.inventory;
                getSlot(i).onSlotChanged();
                ItemStack stackSlot = getSlot(i).getStack();
                ItemStack stackHeld = playerInv.getItemStack();

                if (stackSlot != null) stack = stackSlot.copy();

                if (stackHeld != null) {
                    ItemStack newStack = stackHeld.copy();
                    if (!(getSlot(i) instanceof SlotRecipeOutput)) newStack.stackSize = 1;
                    getSlot(i).putStack(newStack);
                } else getSlot(i).putStack(null);
            } else if (mousebtn == 1) {
                InventoryPlayer playerInv = player.inventory;
                getSlot(i).onSlotChanged();
                ItemStack stackSlot = getSlot(i).getStack();
                ItemStack stackHeld = playerInv.getItemStack();

                if (stackSlot != null) stack = stackSlot.copy();

                if (stackHeld != null) {
                    stackHeld = stackHeld.copy();
                    if (stackSlot != null && stackHeld.isItemEqual(stackSlot) && (i == 0 || i == 0)) {
                        int max = stackSlot.getMaxStackSize();
                        if (++stackSlot.stackSize > max) stackSlot.stackSize = max;
                        getSlot(i).putStack(stackSlot);
                    } else {
                        stackHeld.stackSize = 1;
                        getSlot(i).putStack(stackHeld);
                    }
                } else {
                    if (stackSlot != null) {
                        stackSlot.stackSize--;
                        if (stackSlot.stackSize == 0) getSlot(i).putStack(null);
                    }
                }
            }
        } else {
            stack = super.slotClick(i, mousebtn, clickTypeIn, player);
        }
        return stack;
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return null;
    }

    public List<SlotRecipe> getRecipeSlots() {
        return recipeSlots;
    }

    @Override
    protected Slot addSlotToContainer(Slot slotIn) {
        if (slotIn instanceof SlotRecipe) {
            getRecipeSlots().add((SlotRecipe) slotIn);
        }
        return super.addSlotToContainer(slotIn);
    }
}
