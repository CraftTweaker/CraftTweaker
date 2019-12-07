package minetweaker.mc1102.entity;

import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.item.IItemStack;
import net.minecraft.entity.Entity;
import stanhebben.zenscript.value.*;

import java.util.*;

/**
 * @author Stan
 */
public class MCEntityDefinition implements IEntityDefinition {

    private final Class<? extends Entity> entityClass;
    private final String entityName;

    private final Map<IItemStack, IntRange> dropsToAdd = new HashMap<>();
    private final Map<IItemStack, IntRange> dropsToAddPlayerOnly = new HashMap<>();
    private final List<IItemStack> dropsToRemove = new ArrayList<>();

    public MCEntityDefinition(Class<? extends Entity> entityClass, String entityName) {
        this.entityClass = entityClass;
        this.entityName = entityName;
    }


    @Override
    public String getId() {
        return entityClass.getName();
    }

    @Override
    public String getName() {
        return entityName;
    }

    @Override
    public void addDrop(IItemStack stack, int min, int max) {
        dropsToAdd.put(stack, new IntRange(min, max));
    }

    @Override
    public void addPlayerOnlyDrop(IItemStack stack, int min, int max) {
        dropsToAddPlayerOnly.put(stack, new IntRange(min, max));
    }

    @Override
    public void removeDrop(IItemStack stack) {
        dropsToRemove.add(stack);
    }

    @Override
    public Map<IItemStack, IntRange> getDropsToAdd() {
        return dropsToAdd;
    }

    @Override
    public Map<IItemStack, IntRange> getDropsToAddPlayerOnly() {
        return dropsToAddPlayerOnly;
    }

    @Override
    public List<IItemStack> getDropsToRemove() {
        return dropsToRemove;
    }
}
