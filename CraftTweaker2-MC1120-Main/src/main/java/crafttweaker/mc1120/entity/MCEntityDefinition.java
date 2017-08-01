package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.util.IntegerRange;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.*;

/**
 * @author Stan
 */
public class MCEntityDefinition implements IEntityDefinition {

    private final EntityEntry entityEntry;

    private final Map<IItemStack, IntegerRange> dropsToAdd = new HashMap<>();
    private final Map<IItemStack, IntegerRange> dropsToAddPlayerOnly = new HashMap<>();
    private final List<IItemStack> dropsToRemove = new ArrayList<>();

    public MCEntityDefinition(EntityEntry entityEntry) {
        this.entityEntry = entityEntry;
    }


    @Override
    public String getId() {
        return entityEntry.getRegistryName().toString();
    }

    @Override
    public String getName() {
        return entityEntry.getName();
    }

    @Override
    public void addDrop(IItemStack stack, int min, int max) {
        dropsToAdd.put(stack, new IntegerRange(min, max));
    }

    @Override
    public void addPlayerOnlyDrop(IItemStack stack, int min, int max) {
        dropsToAddPlayerOnly.put(stack, new IntegerRange(min, max));
    }

    @Override
    public void removeDrop(IItemStack stack) {
        dropsToRemove.add(stack);
    }

    @Override
    public Map<IItemStack, IntegerRange> getDropsToAdd() {
        return dropsToAdd;
    }

    @Override
    public Map<IItemStack, IntegerRange> getDropsToAddPlayerOnly() {
        return dropsToAddPlayerOnly;
    }

    @Override
    public List<IItemStack> getDropsToRemove() {
        return dropsToRemove;
    }
    
    public Object getInternal() {
        return entityEntry;
    }
}
