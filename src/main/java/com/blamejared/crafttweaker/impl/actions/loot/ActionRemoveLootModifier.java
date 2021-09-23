package com.blamejared.crafttweaker.impl.actions.loot;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ActionRemoveLootModifier extends ActionLootModifier {
    
    private final String description;
    private final Predicate<Map.Entry<ResourceLocation, IGlobalLootModifier>> removalPredicate;
    
    public ActionRemoveLootModifier(final Predicate<Map.Entry<ResourceLocation, IGlobalLootModifier>> removalPredicate, final String description,
                                    final Supplier<Map<ResourceLocation, IGlobalLootModifier>> mapGetter) {
        
        super(mapGetter);
        this.description = description;
        this.removalPredicate = removalPredicate;
    }
    
    public static ActionRemoveLootModifier of(final Predicate<ResourceLocation> removalPredicate, final String description,
                                              final Supplier<Map<ResourceLocation, IGlobalLootModifier>> mapGetter) {
        
        return new ActionRemoveLootModifier(entry -> removalPredicate.test(entry.getKey()), description, mapGetter);
    }
    
    @Override
    public void apply() {
        
        final Map<ResourceLocation, IGlobalLootModifier> map = this.getModifiersMap();
        map.entrySet()
                .stream()
                .filter(this.removalPredicate)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()) // This way it can't throw a CME since we are removing as we are looping
                .forEach(map::remove);
    }
    
    @Override
    public String describe() {
        
        return this.description == null ? "Removing all loot modifiers" : "Removing loot modifiers " + this.description;
    }
    
}
