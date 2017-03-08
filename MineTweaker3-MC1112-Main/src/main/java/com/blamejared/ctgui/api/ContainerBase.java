package com.blamejared.ctgui.api;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class ContainerBase extends Container {

    protected List<SlotRecipe> recipeSlots = new LinkedList<>();

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }


    @Nullable
    @Override
    public ItemStack slotClick(int i, int mousebtn, ClickType clickTypeIn, EntityPlayer player) {
        ItemStack stack = ItemStack.EMPTY;

        if(i >= 0 && getSlot(i) != null && getSlot(i) instanceof SlotRecipe) // Fake slots
        {
            if(mousebtn == 2) {
                getSlot(i).putStack(ItemStack.EMPTY);
            } else if(mousebtn == 0) {
                InventoryPlayer playerInv = player.inventory;
                getSlot(i).onSlotChanged();
                ItemStack stackSlot = getSlot(i).getStack();
                ItemStack stackHeld = playerInv.getItemStack();

                if(!stackSlot.isEmpty())
                    stack = stackSlot.copy();

                if(!stackHeld.isEmpty()) {
                    ItemStack newStack = stackHeld.copy();
                    if(!(getSlot(i) instanceof SlotRecipeOutput))
                        newStack.setCount(1);
                    ;
                    getSlot(i).putStack(newStack);
                } else
                    getSlot(i).putStack(ItemStack.EMPTY);
            } else if(mousebtn == 1) {
                InventoryPlayer playerInv = player.inventory;
                getSlot(i).onSlotChanged();
                ItemStack stackSlot = getSlot(i).getStack();
                ItemStack stackHeld = playerInv.getItemStack();

                if(!stackSlot.isEmpty())
                    stack = stackSlot.copy();

                if(!stackHeld.isEmpty()) {
                    stackHeld = stackHeld.copy();
                    if(!stackSlot.isEmpty() && stackHeld.isItemEqual(stackSlot) && i == 0) {
                        int max = stackSlot.getMaxStackSize();
                        stackSlot.setCount(stackSlot.getCount() + 1);
                        if(stackSlot.getCount() > max)
                            stackSlot.setCount(max);
                        getSlot(i).putStack(stackSlot);
                    } else {
                        stackHeld.setCount(1);
                        getSlot(i).putStack(stackHeld);
                    }
                } else {
                    if(!stackSlot.isEmpty()) {
                        stackSlot.setCount(stackSlot.getCount() - 1);
                        if(stackSlot.getCount() == 0)
                            getSlot(i).putStack(ItemStack.EMPTY);
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
        return ItemStack.EMPTY;
    }

    public List<SlotRecipe> getRecipeSlots() {
        return recipeSlots;
    }

    @Override
    protected Slot addSlotToContainer(Slot slotIn) {
        if(slotIn instanceof SlotRecipe) {
            getRecipeSlots().add((SlotRecipe) slotIn);
        }
        return super.addSlotToContainer(slotIn);
    }
}
