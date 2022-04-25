package com.blamejared.crafttweaker.natives.block.type.cauldron;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/type/cauldron/AbstractCauldronBlock")
@NativeTypeRegistration(value = AbstractCauldronBlock.class, zenCodeName = "crafttweaker.api.block.type.cauldron.AbstractCauldronBlock")
public class ExpandAbstractCauldronBlock {
    
    /**
     * Checks if this cauldron is full.
     *
     * @param blockState The current block state of the cauldron.
     *
     * @return True if full, false otherwise.
     *
     * @docParam blockState <blockstate:minecraft:cauldron:level=3>
     */
    @ZenCodeType.Method
    public static boolean isFull(AbstractCauldronBlock internal, BlockState blockState) {
        
        return internal.isFull(blockState);
    }
    
}
