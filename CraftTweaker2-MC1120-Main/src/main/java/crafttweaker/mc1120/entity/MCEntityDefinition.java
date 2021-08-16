package crafttweaker.mc1120.entity;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.entity.IEntityDrop;
import crafttweaker.api.entity.IEntityDropFunction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.util.IntegerRange;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class MCEntityDefinition implements IEntityDefinition {

    private final EntityEntry entityEntry;
    private final String entityName;

    private final List<IEntityDrop> drops = new ArrayList<>();
    private final List<IItemStack> dropsToRemove = new ArrayList<>();
    private List<IEntityDropFunction> dropFunctions = new ArrayList<>();
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
            CraftTweakerAPI.logError(String.format("Invalid value provided: <entity:%s>.addDrop(%s, %d, %d, %s).", entityName, stack, min, max, chance));
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
            CraftTweakerAPI.logError(String.format("Invalid value provided: <entity:%s>.addPlayerOnlyDrop(%s, %d, %d, %s).", entityName, stack, min, max, chance));
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
    public IEntity createEntity(IWorld world) {
        return CraftTweakerMC.getIEntity(entityEntry.newInstance(CraftTweakerMC.getWorld(world)));
    }
    
    @Override
    public IEntity spawnEntity(IWorld world, IBlockPos pos) {
        IEntity out = createEntity(world);
        out.setPosition(pos);
        world.spawnEntity(out);
        return out;
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
    
    @Override
    public String toString() {
    	return "<entity:" + entityName + ">";
    }


	@Override
	public void addDropFunction(IEntityDropFunction function) {
		dropFunctions.add(function);
	}


	@Override
	public List<IEntityDropFunction> getDropFunctions() {
		return dropFunctions ;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCEntityDefinition that = (MCEntityDefinition) o;
        return Objects.equals(entityEntry, that.entityEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityEntry);
    }
}
