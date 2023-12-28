package com.blamejared.crafttweaker.api.fluid;

import com.google.common.base.Suppliers;
import net.minecraft.world.level.material.Fluids;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

class FluidStackConstants {
    
    static final Supplier<IFluidStack> EMPTY = Suppliers.memoize(() -> IFluidStack.of(Fluids.EMPTY, 0));
    static final BiPredicate<IFluidStack, IFluidStack> TAG_EQUALS = (first, second) -> !first.hasTag() ? !second.hasTag() : second.hasTag() && first.getInternalTag()
            .equals(second.getInternalTag());
    
}
