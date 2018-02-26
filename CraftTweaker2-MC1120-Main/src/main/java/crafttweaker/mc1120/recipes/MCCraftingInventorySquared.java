package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.ICraftingInventory;
import net.minecraft.inventory.*;

/**
 * @author Stan
 */
public class MCCraftingInventorySquared implements ICraftingInventory {
    
    private final IInventory inventory;
    private final IPlayer player;
    private final int bordersize, stackcount;
    
    protected MCCraftingInventorySquared(IInventory inventory) {
        this(inventory, null);
    }
    
    public MCCraftingInventorySquared(IInventory inventory, IPlayer player) {
        
        this.inventory = inventory;
        
        this.bordersize = (int) Math.sqrt(inventory.getSizeInventory());
        this.stackcount = inventory.getSizeInventory();
        this.player = player == null ? getPlayerFromInventory(inventory) : player;
    }
    
    private static IPlayer getPlayerFromInventory(IInventory inventory) {
        if(inventory instanceof InventoryCrafting) {
            InventoryCrafting inventoryCrafting = (InventoryCrafting) inventory;
            Container eventHandler = inventoryCrafting.eventHandler;
            if(eventHandler != null) {
                for(Slot slot : eventHandler.inventorySlots)
                    if(slot instanceof SlotCrafting) {
                        return CraftTweakerMC.getIPlayer(((SlotCrafting) slot).player);
                    }
            }
        }
        return null;
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
        return CraftTweakerMC.getIItemStack(inventory.getStackInSlot(i));
    }
    
    @Override
    public IItemStack getStack(int x, int y) {
        return getStack(x + y * getWidth());
    }
    
    @Override
    public void setStack(int x, int y, IItemStack stack) {
        setStack(x + y * getWidth(), stack);
    }
    
    @Override
    public void setStack(int i, IItemStack stack) {
        inventory.setInventorySlotContents(i, CraftTweakerMC.getItemStack(stack));
    }
    
    @Override
    public IItemStack[][] getItems() {
        IItemStack[][] output = new IItemStack[getHeight()][getWidth()];
        IItemStack[] oneDimensional = getItemArray();
        for(int row = 0; row < getHeight(); row++) {
            for(int column = 0; column < getWidth(); column++) {
                output[row][column] = oneDimensional[row * getWidth() + column];
            }
        }
        return output;
    }
    
    @Override
    public IItemStack[] getItemArray() {
        IItemStack[] output = new IItemStack[getSize()];
        for(int slot = 0; slot < getSize(); slot++) {
            output[slot] = CraftTweakerMC.getIItemStack(inventory.getStackInSlot(slot));
        }
        return output;
    }
}
