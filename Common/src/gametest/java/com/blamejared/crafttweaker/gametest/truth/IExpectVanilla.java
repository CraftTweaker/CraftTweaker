package com.blamejared.crafttweaker.gametest.truth;

import com.blamejared.crafttweaker.gametest.truth.subject.type.vanilla.ItemStackSubject;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface IExpectVanilla extends IExpect {
    
    default ItemStackSubject assertThat(@Nullable ItemStack target) {
        
        return expect().about(ItemStackSubject.factory()).that(target);
    }
    
}
