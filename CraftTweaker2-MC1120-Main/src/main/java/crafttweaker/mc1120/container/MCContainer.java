package crafttweaker.mc1120.container;

import crafttweaker.api.container.IContainer;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.inventory.Container;

import java.util.Iterator;
import java.util.Objects;

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
        container.putStackInSlot(i, CraftTweakerMC.getItemStack(stack));
    }
    
    @Override
    public Container getInternal() {
        return container;
    }
    
    @Override
    public Iterator<IItemStack> iterator() {
        return container.inventoryItemStacks.stream().map(CraftTweakerMC::getIItemStack).iterator();
    }
    
    @Override
    public String asString() {
        return container.toString();
    }
    
    @Override
    public String toString() {
        return "MCContainer: " + asString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCContainer that = (MCContainer) o;
        return Objects.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(container);
    }
}
