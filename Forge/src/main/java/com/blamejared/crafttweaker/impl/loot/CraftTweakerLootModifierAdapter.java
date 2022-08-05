package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
final class CraftTweakerLootModifierAdapter implements IGlobalLootModifier {
    
    private final ILootModifier modifier;
    
    private CraftTweakerLootModifierAdapter(final ILootModifier modifier) {
        
        this.modifier = modifier;
    }
    
    static IGlobalLootModifier adapt(final ILootModifier modifier) {
        
        return modifier == null ? null : new CraftTweakerLootModifierAdapter(modifier);
    }
    
//    @Nonnull
//    @Override
//    public List<ItemStack> apply(final List<ItemStack> generatedLoot, final LootContext context) {
//
//        return this.modifier.doApply(generatedLoot, context);
//    }
    
    @Override
    public @NotNull ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        //TODO 1.19 SILK
        return null;
    }
    
    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        //TODO 1.19 SILK
        return null;
    }
    
}
