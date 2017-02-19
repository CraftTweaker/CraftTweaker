/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1112.entity;

import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.util.IntegerRange;
import net.minecraft.entity.Entity;

import java.util.*;

/**
 * @author Stan
 */
public class MCEntityDefinition implements IEntityDefinition {
	
	private final Class<? extends Entity> entityClass;
	private final String entityName;
	
	private final Map<IItemStack, IntegerRange> dropsToAdd = new HashMap<>();
	private final Map<IItemStack, IntegerRange> dropsToAddPlayerOnly = new HashMap<>();
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
}
