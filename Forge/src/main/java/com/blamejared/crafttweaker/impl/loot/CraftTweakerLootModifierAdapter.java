package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
final class CraftTweakerLootModifierAdapter implements IGlobalLootModifier {
    
    private final ILootModifier modifier;
    
    private CraftTweakerLootModifierAdapter(final ILootModifier modifier) {
        
        this.modifier = modifier;
    }
    
    static IGlobalLootModifier adapt(final ILootModifier modifier) {
        
        return new CraftTweakerLootModifierAdapter(modifier);
    }
    
    @Nonnull
    @Override
    public List<ItemStack> apply(final List<ItemStack> generatedLoot, final LootContext context) {
        
        return this.modifier.doApply(generatedLoot, context);
    }
    
}
