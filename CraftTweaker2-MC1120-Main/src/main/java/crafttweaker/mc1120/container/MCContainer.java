package crafttweaker.mc1120.container;

import crafttweaker.api.container.IContainer;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.inventory.Container;

import java.util.Iterator;

public class MCContainer implements IContainer {
    
    private final Container container;
    
    public MCContainer(Container container) {
        this.container = container;
    }
    
    
    @Override
    public int getContainerSize() {
        return container.inventorySlots.size();
    }
    
    @Override
    public IItemStack getStack(int i) {
        return CraftTweakerMC.getIItemStack(container.inventoryItemStacks.get(i));
    }
    
    @Override
    public void setStack(int i, IItemStack stack) {
        container.getSlot(i).putStack(CraftTweakerMC.getItemStack(stack));
    }
    
    @Override
    public Container getInternal() {
        return container;
    }
    
    @Override
    public Iterator<IItemStack> iterator() {
        return container.inventoryItemStacks.stream().map(CraftTweakerMC::getIItemStack).iterator();
    }
}
