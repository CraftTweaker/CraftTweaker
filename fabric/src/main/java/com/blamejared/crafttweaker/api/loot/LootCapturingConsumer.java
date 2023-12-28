package com.blamejared.crafttweaker.api.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public final class LootCapturingConsumer implements Consumer<ItemStack> {
    
    private final ObjectArrayList<ItemStack> capture;
    private final Consumer<ItemStack> wrapped;
    
    private LootCapturingConsumer(final Consumer<ItemStack> wrapped) {
        
        this.capture = new ObjectArrayList<>();
        this.wrapped = wrapped;
    }
    
    public static LootCapturingConsumer of(final Consumer<ItemStack> wrapped) {
        
        return new LootCapturingConsumer(wrapped);
    }
    
    @Override
    public void accept(final ItemStack itemStack) {
        
        this.capture.add(itemStack);
    }
    
    public void release(final Function<ObjectArrayList<ItemStack>, ObjectArrayList<ItemStack>> captureModifier) {
        
        captureModifier.apply(this.capture).forEach(this.wrapped);
    }
    
}
