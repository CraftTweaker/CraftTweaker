package com.blamejared.crafttweaker.impl.blocks;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.MCBlock")
public class MCBlock {
    
    private final Block internal;
    
    public MCBlock(Block internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Method
    public MCBlockState getDefaultState(){
        return new MCBlockState(getInternal().getDefaultState());
    }
    
    @ZenCodeType.Getter("lootTable")
    public String getLootTable() {
        return getInternal().getLootTable().toString();
    }
    
    @ZenCodeType.Method
    public boolean canSpawnInBlock() {
        return getInternal().canSpawnInBlock();
    }
    
    @ZenCodeType.Getter("translationKey")
    public String getTranslationKey() {
        return getInternal().getTranslationKey();
    }
    
    @ZenCodeType.Caster(implicit = false)
    public static String asString(MCBlock block) {
        return block.getInternal().toString();
    }
    
    public Block getInternal() {
        return internal;
    }
}
