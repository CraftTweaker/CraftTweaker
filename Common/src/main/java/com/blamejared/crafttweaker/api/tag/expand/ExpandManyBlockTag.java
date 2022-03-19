package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.block.CTBlockIngredient;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * This expansion specifically targets itemTags.
 * It adds implicit casters to IIngredient and IData, so that you can use them wherever you can use IIngredient.
 * <p>
 * Only downside is that if you want to use Ingredient Transformers, you will need to call `asIIngredient()` first.
 */
@ZenRegister
@Document("vanilla/api/tag/ExpandManyBlockTag")
@ZenCodeType.Expansion("crafttweaker.api.util.Many<crafttweaker.api.tag.MCTag<crafttweaker.api.block.Block>>")
public class ExpandManyBlockTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CTBlockIngredient asIngredient(Many<MCTag<Block>> internal) {
        
        return new CTBlockIngredient.BlockTagWithAmountIngredient(internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTBlockIngredient asList(Many<MCTag<Block>> internal, CTBlockIngredient other) {
        
        List<CTBlockIngredient> elements = new ArrayList<>();
        elements.add(asIngredient(internal));
        elements.add(other);
        return new CTBlockIngredient.CompoundBlockIngredient(elements);
    }
    
}
