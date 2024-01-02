package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class NeoForgeLootModifierMapEntrySetAdapter extends AbstractSet<Map.Entry<ResourceLocation, ILootModifier>> {
    
    private final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> wrapped;
    
    private NeoForgeLootModifierMapEntrySetAdapter(final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> wrapped) {
        
        this.wrapped = wrapped;
    }
    
    static Set<Map.Entry<ResourceLocation, ILootModifier>> adapt(final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> set) {
        
        return new NeoForgeLootModifierMapEntrySetAdapter(set);
    }
    
    @Override
    public boolean add(final Map.Entry<ResourceLocation, ILootModifier> entry) {
        
        return this.wrapped.add(Map.entry(entry.getKey(), CraftTweakerLootModifierAdapter.adapt(entry.getValue())));
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
    public boolean remove(Object o) {
        
        if(o instanceof IGlobalLootModifier) {
            
            return this.wrapped.remove(o);
        }
        
        if(o instanceof final NeoForgeLootModifierAdapter wrapper) {
            
            return this.wrapped.remove(wrapper.modifier());
        }
        
        return super.contains(o);
    }
    
    @Override
    public void clear() {
        
        this.wrapped.clear();
    }
    
    @Override
    public Iterator<Map.Entry<ResourceLocation, ILootModifier>> iterator() {
        
        return NeoForgeLootModifierIteratorAdapter.adapt(this.wrapped.iterator(), entry -> Map.entry(entry.getKey(), NeoForgeLootModifierAdapter.adapt(entry.getValue())));
    }
    
    @Override
    public int size() {
        
        return this.wrapped.size();
    }
    
}
