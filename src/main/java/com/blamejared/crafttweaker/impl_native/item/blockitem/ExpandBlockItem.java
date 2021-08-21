package com.blamejared.crafttweaker.impl_native.item.blockitem;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/block/BlockItem")
@NativeTypeRegistration(value = BlockItem.class, zenCodeName = "crafttweaker.api.item.block.BlockItem")
public class ExpandBlockItem {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Caster
    @ZenCodeType.Getter("block")
    public static Block getBlock(BlockItem internal) {
        
        return internal.getBlock();
    }
    
}
