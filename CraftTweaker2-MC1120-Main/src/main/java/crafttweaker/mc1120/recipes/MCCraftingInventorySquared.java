package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.ICraftingInventory;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

import java.util.*;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

/**
 * @author Stan
 */
public class MCCraftingInventorySquared implements ICraftingInventory {
    
    private final IInventory inventory;
    private final IPlayer player;
    private final int bordersize, stackcount;
    private final IItemStack[][] items;
    
    protected MCCraftingInventorySquared(IInventory inventory) {
        this(inventory, null);
    }
    
    public MCCraftingInventorySquared(IInventory inventory, IPlayer player) {
        
        this.inventory = inventory;
        this.player = player;
        this.bordersize = (int) Math.sqrt(inventory.getSizeInventory());
        int stackcount = 0;
        
        items = new IItemStack[getWidth()][getHeight()];
        for(int x = 0; x < getWidth(); x++) {
            for(int y = 0; y < getHeight(); y++) {
                ItemStack itemStack = inventory.getStackInSlot(x * getWidth() + y);
                if (itemStack.isEmpty()) {
                    items[x][y] = MCItemStack.EMPTY;
                } else {
                    stackcount++;
                    items[x][y] = new MCItemStack(itemStack);
                }
            }
        }
        this.stackcount = stackcount;
    }
    
    public static MCCraftingInventorySquared get(IInventory inventory) {
        return new MCCraftingInventorySquared(inventory);
    }
    
    
    @Override
    public IPlayer getPlayer() {
        return player;
    }
    
    @Override
    public int getSize() {
        return getWidth() * getHeight();
    }
    
    @Override
    public int getWidth() {
        return bordersize;
    }
    
    @Override
    public int getHeight() {
        return bordersize;
    }
    
    @Override
    public int getStackCount() {
        return stackcount;
    }
    
    @Override
    public IItemStack getStack(int i) {
        int x = i / getWidth();
        int y = Math.floorMod(i, getWidth());
        return getStack(x, y);
    }
    
    @Override
    public IItemStack getStack(int x, int y) {
        return items[x][y];
    }
    
    @Override
    public void setStack(int x, int y, IItemStack stack) {
        items[x][y] = stack;
    }
    
    @Override
    public void setStack(int i, IItemStack stack) {
        int x = i / getWidth();
        int y = Math.floorMod(i, getWidth());
        setStack(x, y, stack);
    }
    
    @Override
    public IItemStack[][] getItems() {
        return items;
    }
    
    @Override
    public IItemStack[] getItemArray() {
        IItemStack[] out = new IItemStack[getSize()];
        for(int x = 0; x < items.length; x++) {
            for(int y = 0; y < items[x].length; y++) {
                out[x * y + y] = items[x][y];
            }
        }
        return out;
    }
}
