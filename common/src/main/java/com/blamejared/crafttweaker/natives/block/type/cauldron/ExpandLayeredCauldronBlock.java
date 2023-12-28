package com.blamejared.crafttweaker.natives.block.type.cauldron;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/type/cauldron/LayeredCauldronBlock")
@NativeTypeRegistration(value = LayeredCauldronBlock.class, zenCodeName = "crafttweaker.api.block.type.cauldron.LayeredCauldronBlock")
public class ExpandLayeredCauldronBlock {
    
    /**
     * Lowers the fill level of the layered Cauldron at the given position.
     *
     * @param blockState The blockstate of the cauldron.
     * @param level      The current level.
     * @param position   The position of the cauldron.
     *
     * @docParam blockState <blockstate:minecraft:cauldron:level=3>
     * @docParam level level
     * @docParam position new BlockPos(1, 2, 3);
     */
    @ZenCodeType.StaticExpansionMethod
    public static void lowerFillLevel(BlockState blockState, Level level, BlockPos position) {
        
        LayeredCauldronBlock.lowerFillLevel(blockState, level, position);
    }
    
}
