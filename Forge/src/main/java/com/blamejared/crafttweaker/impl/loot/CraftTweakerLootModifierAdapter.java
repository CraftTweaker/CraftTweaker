package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.loot.IGlobalLootModifier;

final class CraftTweakerLootModifierAdapter implements IGlobalLootModifier {
    
    private final ILootModifier modifier;
    
    private CraftTweakerLootModifierAdapter(final ILootModifier modifier) {
        
        this.modifier = modifier;
    }
    
    static IGlobalLootModifier adapt(final ILootModifier modifier) {
        
        return modifier == null ? null : new CraftTweakerLootModifierAdapter(modifier);
    }
    
    @Override
    public ObjectArrayList<ItemStack> apply(final ObjectArrayList<ItemStack> generatedLoot, final LootContext context) {
        
        return this.modifier.doApply(generatedLoot, context);
    }
    
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        
        throw new UnsupportedOperationException("Data generation of loot modifiers is not supported");
    }
    
}
