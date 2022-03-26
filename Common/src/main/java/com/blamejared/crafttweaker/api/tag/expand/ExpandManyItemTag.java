package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This expansion specifically targets itemTags.
 * It adds implicit casters to IIngredient and IData, so that you can use them wherever you can use IIngredient.
 * <p>
 * Only downside is that if you want to use Ingredient Transformers, you will need to call `asIIngredient()` first.
 */
@ZenRegister
@Document("vanilla/api/tag/ExpandManyItemTag")
@ZenCodeType.Expansion("crafttweaker.api.util.Many<crafttweaker.api.tag.type.KnownTag<crafttweaker.api.item.ItemDefinition>>")
public class ExpandManyItemTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredientWithAmount asIngredient(Many<KnownTag<Item>> internal) {
        
        final IIngredient iIngredient = ExpandItemTag.asIIngredient(internal.getData());
        return iIngredient.mul(internal.getAmount());
    }
    
}
