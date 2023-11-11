package com.blamejared.crafttweaker.mixin.common.transform.loot;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.loot.ILootTableIdHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class MixinLootTable implements ILootTableIdHolder.Mutable {
    
    @Unique
    private ResourceLocation crafttweaker$tableId;
    
    @Override
    public void crafttweaker$tableId(final ResourceLocation id) {
        
        if(this.crafttweaker$tableId == null) {
            this.crafttweaker$tableId = id;
        }
    }
    
    @Override
    public ResourceLocation crafttweaker$tableId() {
        
        return Objects.requireNonNull(this.crafttweaker$tableId, "tableId");
    }
    
    @Inject(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", at = @At("HEAD"))
    private void crafttweaker$getRandomItemsRaw$populateContextWithTableId(final LootContext $$0, final Consumer<ItemStack> $$1, final CallbackInfo ci) {
        
        GenericUtil.<ILootTableIdHolder.Mutable>uncheck($$0).crafttweaker$tableId(this.crafttweaker$tableId());
    }
    
}
