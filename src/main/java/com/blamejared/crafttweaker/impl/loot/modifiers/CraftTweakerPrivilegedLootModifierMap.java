package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.CraftTweaker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

final class CraftTweakerPrivilegedLootModifierMap extends AbstractMap<ResourceLocation, IGlobalLootModifier> {
    private final Map<ResourceLocation, IGlobalLootModifier> defaultMap;
    private final Map<ResourceLocation, IGlobalLootModifier> ctMap;
    
    CraftTweakerPrivilegedLootModifierMap(final Map<ResourceLocation, IGlobalLootModifier> clone) {
        if (clone instanceof CraftTweakerPrivilegedLootModifierMap) {
            final CraftTweakerPrivilegedLootModifierMap other = (CraftTweakerPrivilegedLootModifierMap) clone;
            this.defaultMap = new HashMap<>(other.defaultMap);
            this.ctMap = new LinkedHashMap<>(other.ctMap);
        } else {
            this.defaultMap = new HashMap<>();
            this.ctMap = new LinkedHashMap<>();
            this.putAll(clone);
        }
    }
    
    private static boolean isCtModifier(final ResourceLocation key) {
        return CraftTweaker.MODID.equals(key.getNamespace());
    }
    
    @Override
    public int size() {
        return this.defaultMap.size() + this.ctMap.size();
    }
    
    @Override
    public boolean containsValue(final Object value) {
        return this.defaultMap.containsValue(value) || this.ctMap.containsValue(value);
    }
    
    @Override
    public boolean containsKey(final Object key) {
        return this.defaultMap.containsKey(key) || this.ctMap.containsKey(key);
    }
    
    @Override
    public IGlobalLootModifier get(final Object key) {
        final IGlobalLootModifier v = this.defaultMap.get(key);
        return v == null? this.ctMap.get(key) : v;
    }
    
    @Override
    public IGlobalLootModifier put(final ResourceLocation key, final IGlobalLootModifier value) {
        if(isCtModifier(key)) {
            return this.ctMap.put(key, value);
        } else {
            return this.defaultMap.put(key, value);
        }
    }
    
    @Override
    public IGlobalLootModifier remove(final Object key) {
        final IGlobalLootModifier v = this.defaultMap.remove(key);
        return v == null? this.ctMap.remove(key) : v;
    }
    
    @Override
    public void clear() {
        this.defaultMap.clear();
        this.ctMap.clear();
    }
    
    @Override
    public Set<Entry<ResourceLocation, IGlobalLootModifier>> entrySet() {
        final Set<Entry<ResourceLocation, IGlobalLootModifier>> set = new LinkedHashSet<>(this.size());
        set.addAll(this.defaultMap.entrySet());
        set.addAll(this.ctMap.entrySet());
        return set;
    }
}
