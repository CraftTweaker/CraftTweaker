package com.blamejared.crafttweaker.natives.block.type.falling;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this (<block:minecraft:sand> as FallingBlock)
 */
@ZenRegister
@Document("vanilla/api/block/type/falling/FallingBlock")
@NativeTypeRegistration(value = FallingBlock.class, zenCodeName = "crafttweaker.api.block.type.falling.FallingBlock")
public class ExpandFallingBlock {
    
    
    /**
     * Checks if the given blockstate stops a block from falling through it.
     *
     * @param state The state the check.
     *
     * @return True if the blockstate can be fallen through, false otherwise.
     *
     * @docParam state <blockstate:minecraft:dirt>
     */
    @ZenCodeType.StaticExpansionMethod
    public static boolean isFree(BlockState state) {
        
        return FallingBlock.isFree(state);
    }
    
    @ZenCodeType.Method
    public static int getDustColor(FallingBlock internal, BlockState state, Level level, BlockPos pos) {
        
        return internal.getDustColor(state, level, pos);
    }
    
}
