package com.blamejared.crafttweaker.impl.blocks;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.MCBlock")
@Document(value = "vanilla/blocks/MCBlock")
@ZenWrapper(wrappedClass = "net.minecraft.block.Block", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.asString()")
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
    public String asString() {
        return internal.toString();
    }
    
    public Block getInternal() {
        return internal;
    }
}
