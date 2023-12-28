package com.blamejared.crafttweaker.api.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.LinkedHashMap;
import java.util.Map;

public class LootModifierManager {
    
    public static final LootModifierManager INSTANCE = new LootModifierManager();
    
    private final Map<ResourceLocation, ILootModifier> modifiers;
    
    private LootModifierManager() {
        
        this.modifiers = new LinkedHashMap<>();
    }
    
    public Map<ResourceLocation, ILootModifier> modifiers() {
        
        return this.modifiers;
    }
    
    public ObjectArrayList<ItemStack> applyModifiers(final ObjectArrayList<ItemStack> generatedLoot, final LootContext context) {
        
        return this.modifiers()
                .values()
                .stream()
                .reduce(
                        generatedLoot,
                        (loot, modifier) -> modifier.doApply(loot, context),
                        (lootA, lootB) -> Util.make(lootA, a -> a.addAll(lootB))
                );
    }
    
}
