package com.blamejared.crafttweaker.mixin.common.access.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LootTable.class)
public interface AccessLootTable {
    
    @Invoker("getRandomItems")
    ObjectArrayList<ItemStack> crafttweaker$callGetRandomItems(LootContext $$0);
    
}
