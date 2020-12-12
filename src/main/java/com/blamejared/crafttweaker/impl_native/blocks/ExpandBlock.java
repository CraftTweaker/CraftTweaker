package com.blamejared.crafttweaker.impl_native.blocks;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeExpansion(Block.class)
public class ExpandBlock {
    
    @ZenCodeType.Method
    public static BlockState getDefaultState(Block internal) {
        return internal.getDefaultState();
    }
    
    @ZenCodeType.Getter("lootTable")
    public static String getLootTable(Block internal) {
        return internal.getLootTable().toString();
    }
    
    @ZenCodeType.Method
    public static boolean canSpawnInBlock(Block internal) {
        return internal.canSpawnInBlock();
    }
    
    @ZenCodeType.Getter("translationKey")
    public static String getTranslationKey(Block internal) {
        return internal.getTranslationKey();
    }
    
    @ZenCodeType.Caster()
    public static String asString(Block internal) {
        return internal.toString();
    }
    
    public static String getCommandString(Block internal) {
        return "<block:" + internal.getRegistryName() + ">";
    }
}
