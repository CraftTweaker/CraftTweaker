package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.natives.item.ExpandItemStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import java.util.List;
import java.util.stream.Collectors;

final class ForgeLootModifierAdapter implements ILootModifier {
    
    private final IGlobalLootModifier modifier;
    
    private ForgeLootModifierAdapter(final IGlobalLootModifier modifier) {
        
        this.modifier = modifier;
    }
    
    static ILootModifier adapt(final IGlobalLootModifier modifier) {
        
        return modifier == null ? null : new ForgeLootModifierAdapter(modifier);
    }
    
    @Override
    public List<IItemStack> modify(final List<IItemStack> loot, final LootContext context) {
        
        return this.doApply(loot.stream()
                        .map(IItemStack::getInternal)
                        .collect(Collectors.toCollection(ObjectArrayList::new)), context)
                .stream()
                .map(ExpandItemStack::asIItemStack)
                .collect(Collectors.toList());
    }
    
    @Override
    public ObjectArrayList<ItemStack> doApply(final ObjectArrayList<ItemStack> loot, final LootContext context) {
        
        return this.modifier.apply(loot, context);
    }
    
    IGlobalLootModifier modifier() {
        
        return this.modifier;
    }
    
}
