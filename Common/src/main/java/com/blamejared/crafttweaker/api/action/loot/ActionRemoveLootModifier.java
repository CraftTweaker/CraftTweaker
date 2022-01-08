package com.blamejared.crafttweaker.api.action.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ActionRemoveLootModifier extends ActionLootModifier {
    private final String removalDescription;
    private final Predicate<Map.Entry<ResourceLocation, ILootModifier>> predicate;
    
    public ActionRemoveLootModifier(final String description, final Predicate<Map.Entry<ResourceLocation, ILootModifier>> predicate,
                                    final Supplier<Map<ResourceLocation, ILootModifier>> mapGetter) {
        super(mapGetter);
        this.removalDescription = description;
        this.predicate = predicate;
    }
    
    @Override
    public void apply() {
        
        final Map<ResourceLocation, ILootModifier> map = this.modifiersMap();
        map.entrySet()
                .stream()
                .filter(this.predicate)
                .map(Map.Entry::getKey)
                .toList()
                .forEach(map::remove);
    }
    
    @Override
    public String describe() {
        
        return this.removalDescription == null ? "Removing all loot modifiers" : "Removing loot modifiers " + this.removalDescription;
    }
}
