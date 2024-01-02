package com.blamejared.crafttweaker.impl.loot;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.Iterator;
import java.util.Map;

final class CraftTweakerPrivilegedLootModifierEntrySetIterator implements Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> {
    
    private final Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> defaultIterator;
    private final Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> ctIterator;
    private boolean targetCt;
    
    private CraftTweakerPrivilegedLootModifierEntrySetIterator(final Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> defaultIterator,
                                                               final Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> ctIterator) {
        
        this.defaultIterator = defaultIterator;
        this.ctIterator = ctIterator;
        this.targetCt = false;
    }
    
    static CraftTweakerPrivilegedLootModifierEntrySetIterator of(final Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> defaultIterator,
                                                                 final Iterator<Map.Entry<ResourceLocation, IGlobalLootModifier>> ctIterator) {
        
        return new CraftTweakerPrivilegedLootModifierEntrySetIterator(defaultIterator, ctIterator);
    }
    
    @Override
    public boolean hasNext() {
        
        return this.defaultIterator.hasNext() || this.ctIterator.hasNext();
    }
    
    @Override
    public Map.Entry<ResourceLocation, IGlobalLootModifier> next() {
        
        if(this.targetCt || (this.targetCt = !this.defaultIterator.hasNext())) {
            return this.ctIterator.next();
        }
        
        return this.defaultIterator.next();
    }
    
    @Override
    public void remove() {
        
        if(this.targetCt) {
            this.ctIterator.remove();
        } else {
            this.defaultIterator.remove();
        }
    }
    
}
