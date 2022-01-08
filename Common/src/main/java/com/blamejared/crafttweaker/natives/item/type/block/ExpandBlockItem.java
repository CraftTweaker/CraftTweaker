package com.blamejared.crafttweaker.natives.item.type.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/type/block/BlockItem")
@NativeTypeRegistration(value = BlockItem.class, zenCodeName = "crafttweaker.api.item.type.block.BlockItem")
public class ExpandBlockItem {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Caster
    @ZenCodeType.Getter("block")
    public static Block getBlock(BlockItem internal) {
        
        return internal.getBlock();
    }
    
}
