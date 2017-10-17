package crafttweaker.mc1120.entity;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.entity.IEntityDrop;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.util.IntegerRange;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class MCEntityDefinition implements IEntityDefinition {

    private final EntityEntry entityEntry;
    private final String entityName;

    private final List<IEntityDrop> drops = new ArrayList<>();
    private final List<IItemStack> dropsToRemove = new ArrayList<>();
    private boolean clearDrops = false;

    public MCEntityDefinition(EntityEntry entityEntry) {
        this.entityEntry = entityEntry;
        this.entityName = entityEntry.getName();
    }


    @Override
    public String getId() {
        return entityEntry.getRegistryName() != null ? entityEntry.getRegistryName().toString() : "no_registry_name";
    }

    @Override
    public String getName() {
        return entityName;
    }

    @Override
    public void addDrop(IItemStack stack, int min, int max, float chance) {
        if (min < 0 || max < 0 || chance < 0 || chance > 1) {
            CraftTweakerAPI.logError(String.format("Invalid value provided: <entity:%s>.addDrop(%s, %d, %d, %s).", entityName, stack, min, max, Float.toString(chance)));
            return;
        }
        drops.add(new EntityDrop(stack, min, max, chance));
    }
    
    @Override
    public void addDrop(WeightedItemStack stack, int min, int max){
    	this.addDrop(stack.getStack(), min, max, stack.getChance());
    }
    


    @Override
    public void addPlayerOnlyDrop(IItemStack stack, int min, int max, float chance) {
        if (min < 0 || max < 0 || chance < 0 || chance > 1) {
            CraftTweakerAPI.logError(String.format("Invalid value provided: <entity:%s>.addPlayerOnlyDrop(%s, %d, %d, %s).", entityName, stack, min, max, Float.toString(chance)));
            return;
        }
        drops.add(new EntityDrop(stack, min, max, chance, true));
    }
    
    @Override
    public void addPlayerOnlyDrop(WeightedItemStack stack, int min, int max) {
    	this.addPlayerOnlyDrop(stack.getStack(), min, max, stack.getChance());
    	
    }

    @Override
    public void removeDrop(IItemStack stack) {
        dropsToRemove.add(stack);
    }

    @Override
    public void clearDrops() {
        clearDrops = true;
    }

    @Override
    public List<IEntityDrop> getDrops() {
        return drops;
    }

    @Override
    public Map<IItemStack, IntegerRange> getDropsToAdd() {
        return drops.stream().filter(drop -> !drop.isPlayerOnly()).collect(Collectors.toMap(IEntityDrop::getItemStack, IEntityDrop::getRange));
    }

    @Override
    public Map<IItemStack, IntegerRange> getDropsToAddPlayerOnly() {
        return drops.stream().filter(IEntityDrop::isPlayerOnly).collect(Collectors.toMap(IEntityDrop::getItemStack, IEntityDrop::getRange));
    }

    @Override
    public List<IItemStack> getDropsToRemove() {
        return dropsToRemove;
    }

    @Override
    public boolean shouldClearDrops() {
        return clearDrops;
    }
    
    @Override
    public Object getInternal() {
        return entityEntry;
    }
}
