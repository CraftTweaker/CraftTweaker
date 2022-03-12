package com.blamejared.crafttweaker.mixin.common.transform.loot;

import com.blamejared.crafttweaker.api.loot.LootCapturingConsumer;
import com.blamejared.crafttweaker.api.loot.LootModifierManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class MixinLootTable {
    
    @ModifyVariable(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", at = @At("HEAD"), argsOnly = true)
    private Consumer<ItemStack> crafttweaker$getRandomItems$injectCapturingConsumer(final Consumer<ItemStack> original) {
        
        return LootCapturingConsumer.of(original);
    }
    
    @Inject(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", at = @At("TAIL"))
    private void crafttweaker$getRandomItems$runLootModifiers(final LootContext contextData, final Consumer<ItemStack> stacksOut, final CallbackInfo ci) {
        
        ((LootCapturingConsumer) stacksOut).release(loot -> LootModifierManager.INSTANCE.applyModifiers(loot, contextData));
    }
    
}
