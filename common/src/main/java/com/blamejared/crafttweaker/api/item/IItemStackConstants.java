package com.blamejared.crafttweaker.api.item;

import com.google.common.base.Suppliers;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.Internal
class IItemStackConstants {
    
    static final Supplier<IItemStack> EMPTY_STACK = Suppliers.memoize(() -> IItemStack.of(ItemStack.EMPTY));
    
}
