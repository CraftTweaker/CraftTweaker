/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.item.*;
import stanhebben.zenscript.value.*;

import java.util.*;

/**
 *
 * @author Stan
 */
public class MCEntityDefinition implements IEntityDefinition {
	private final EntityRegistry.EntityRegistration registration;
    
    private final Map<IItemStack, IntRange> dropsToAdd = new HashMap<IItemStack, IntRange>();
    private final Map<IItemStack, IntRange> dropsToAddPlayerOnly = new HashMap<IItemStack, IntRange>();
    private final List<IItemStack> dropsToRemove = new ArrayList<IItemStack>();

	public MCEntityDefinition(EntityRegistry.EntityRegistration registration) {
		this.registration = registration;
	}

	@Override
	public String getId() {
		return registration.getEntityClass().getName();
	}

	@Override
	public String getName() {
		return registration.getEntityName();
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
