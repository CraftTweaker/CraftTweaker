package com.blamejared.crafttweaker.mixin.common.transform.loot;

import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin(LootContext.class)
public abstract class MixinLootContext implements ILootTableIdHolder.Mutable {
    
    @Unique
    private ResourceLocation crafttweaker$queriedTableId;
    
    @Override
    public void crafttweaker$tableId(final ResourceLocation id) {
        if (this.crafttweaker$queriedTableId == null) {
            this.crafttweaker$queriedTableId = id;
        }
    }
    
    @Override
    public ResourceLocation crafttweaker$tableId() {
        
        return Objects.requireNonNullElse(this.crafttweaker$queriedTableId, CRAFTTWEAKER$UNKNOWN_TABLE_ID);
    }
    
}
