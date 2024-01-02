package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public final class NeoForgeLootModifierMapAdapter extends AbstractMap<ResourceLocation, ILootModifier> {
    
    private final Map<ResourceLocation, IGlobalLootModifier> wrapped;
    
    private NeoForgeLootModifierMapAdapter(final Map<ResourceLocation, IGlobalLootModifier> wrapped) {
        
        this.wrapped = wrapped;
    }
    
    public static Map<ResourceLocation, ILootModifier> adapt(final Map<ResourceLocation, IGlobalLootModifier> map) {
        
        return new NeoForgeLootModifierMapAdapter(map);
    }
    
    @Override
    public boolean isEmpty() {
        
        return this.wrapped.isEmpty();
    }
    
    @Override
    public int size() {
        
        return this.wrapped.size();
    }
    
    @Override
    public boolean containsValue(final Object value) {
        
        if(value instanceof IGlobalLootModifier) {
            
            return this.wrapped.containsValue(value);
        }
        
        if(value instanceof final NeoForgeLootModifierAdapter wrapper) {
            
            return this.wrapped.containsValue(wrapper.modifier());
        }
        
        return super.containsValue(value);
    }
    
    @Override
    public boolean containsKey(final Object key) {
        
        return this.wrapped.containsKey(key);
    }
    
    @Override
    public ILootModifier get(Object key) {
        
        return NeoForgeLootModifierAdapter.adapt(this.wrapped.get(key));
    }
    
    @Override
    public ILootModifier put(final ResourceLocation key, final ILootModifier value) {
        
        return NeoForgeLootModifierAdapter.adapt(this.wrapped.put(key, CraftTweakerLootModifierAdapter.adapt(value)));
    }
    
    @Override
    public ILootModifier remove(final Object key) {
        
        return NeoForgeLootModifierAdapter.adapt(this.wrapped.remove(key));
    }
    
    @Override
    public void clear() {
        
        this.wrapped.clear();
    }
    
    @Nonnull
    @Override
    public Set<ResourceLocation> keySet() {
        
        return this.wrapped.keySet();
    }
    
    @Nonnull
    @Override
    public Collection<ILootModifier> values() {
        
        return NeoForgeLootModifierValuesCollectionAdapter.adapt(this.wrapped.values());
    }
    
    @Nonnull
    @Override
    public Set<Entry<ResourceLocation, ILootModifier>> entrySet() {
        
        return NeoForgeLootModifierMapEntrySetAdapter.adapt(this.wrapped.entrySet());
    }
    
}
