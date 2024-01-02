package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

final class NeoForgeLootModifierValuesCollectionAdapter extends AbstractCollection<ILootModifier> {
    
    private final Collection<IGlobalLootModifier> wrapped;
    
    private NeoForgeLootModifierValuesCollectionAdapter(final Collection<IGlobalLootModifier> wrapped) {
        
        this.wrapped = wrapped;
    }
    
    static Collection<ILootModifier> adapt(final Collection<IGlobalLootModifier> wrapped) {
        
        return new NeoForgeLootModifierValuesCollectionAdapter(wrapped);
    }
    
    @Override
    public Iterator<ILootModifier> iterator() {
        
        return NeoForgeLootModifierIteratorAdapter.adapt(this.wrapped.iterator(), NeoForgeLootModifierAdapter::adapt);
    }
    
    @Override
    public int size() {
        
        return this.wrapped.size();
    }
    
    @Override
    public boolean isEmpty() {
        
        return this.wrapped.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        
        if(o instanceof IGlobalLootModifier) {
            
            return this.wrapped.contains(o);
        }
        
        if(o instanceof final NeoForgeLootModifierAdapter wrapper) {
            
            return this.wrapped.contains(wrapper.modifier());
        }
        
        return super.contains(o);
    }
    
    @Override
    public boolean add(final ILootModifier lootModifier) {
        
        return this.wrapped.add(CraftTweakerLootModifierAdapter.adapt(lootModifier));
    }
    
    @Override
    public boolean remove(final Object o) {
        
        if(o instanceof IGlobalLootModifier) {
            
            return this.wrapped.remove(o);
        }
        
        if(o instanceof final NeoForgeLootModifierAdapter wrapper) {
            
            return this.wrapped.remove(wrapper.modifier());
        }
        
        return super.remove(o);
    }
    
    @Override
    public void clear() {
        
        this.wrapped.clear();
    }
    
}
