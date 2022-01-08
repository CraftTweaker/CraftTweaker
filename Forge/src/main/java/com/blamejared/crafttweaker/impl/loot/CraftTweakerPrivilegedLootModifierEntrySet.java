package com.blamejared.crafttweaker.impl.loot;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

final class CraftTweakerPrivilegedLootModifierEntrySet extends AbstractSet<Map.Entry<ResourceLocation, IGlobalLootModifier>> {
    
    private final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> defaultSet;
    private final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> ctSet;
    private final Predicate<ResourceLocation> ctVerifier;
    
    private CraftTweakerPrivilegedLootModifierEntrySet(final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> defaultSet,
                                                       final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> ctSet,
                                                       final Predicate<ResourceLocation> ctVerifier) {
        
        this.defaultSet = defaultSet;
        this.ctSet = ctSet;
        this.ctVerifier = ctVerifier;
    }
    
    static Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> wrap(final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> defaultSet,
                                                                      final Set<Map.Entry<ResourceLocation, IGlobalLootModifier>> ctSet,
                                                                      final Predicate<ResourceLocation> ctVerifier) {
        
        return new CraftTweakerPrivilegedLootModifierEntrySet(defaultSet, ctSet, ctVerifier);
    }
    
    @Override
    public boolean add(final Map.Entry<ResourceLocation, IGlobalLootModifier> entry) {
        
        if(this.ctVerifier.test(entry.getKey())) {
            return this.ctSet.add(entry);
        }
        return this.defaultSet.add(entry);
    }
    
    @Override
    public boolean isEmpty() {
        
        return this.defaultSet.isEmpty() && this.ctSet.isEmpty();
    }
    
    @Override
    public boolean contains(final Object o) {
        
        return this.defaultSet.contains(o) || this.ctSet.contains(o);
    }
    
    @Override
    public boolean remove(Object o) {
        
        return this.defaultSet.remove(o) || this.ctSet.remove(o);
    }
    
    @Override
    public void clear() {
        
        this.defaultSet.clear();
        this.ctSet.clear();
    }
    
    @Override
    public Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> iterator() {
        
        return CraftTweakerPrivilegedLootModifierEntrySetIterator.of(this.defaultSet.iterator(), this.ctSet.iterator());
    }
    
    @Override
    public int size() {
        
        return this.defaultSet.size() + this.ctSet.size();
    }
    
}
