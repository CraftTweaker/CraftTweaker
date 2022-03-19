package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.block.CTBlockIngredient;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * This expansion specifically targets BlockTags.
 */
@ZenRegister
@Document("vanilla/api/tag/ExpandBlockTag")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.block.Block>")
public class ExpandBlockTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CTBlockIngredient asBlockIngredient(MCTag<Block> internal) {
        
        return new CTBlockIngredient.BlockTagWithAmountIngredient(internal.withAmount(1));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTBlockIngredient asList(MCTag<Block> internal, CTBlockIngredient other) {
        
        List<CTBlockIngredient> elements = new ArrayList<>();
        elements.add(asBlockIngredient(internal));
        elements.add(other);
        return new CTBlockIngredient.CompoundBlockIngredient(elements);
    }
    
}
