package com.blamejared.crafttweaker.impl.blocks;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.MCBlock")
@Document("vanilla/api/blocks/MCBlock")
@ZenWrapper(wrappedClass = "net.minecraft.block.Block", displayStringFormat = "%s.asString()")
public class MCBlock implements CommandStringDisplayable {
    
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
    
    @ZenCodeType.Caster()
    public String asString() {
        return internal.toString();
    }
    
    public Block getInternal() {
        return internal;
    }
    
    @Override
    public String getCommandString() {
        return "<block:" + internal.getRegistryName() + ">";
    }
}
