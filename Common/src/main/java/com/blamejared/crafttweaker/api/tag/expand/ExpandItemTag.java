package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.ingredient.type.WrappingIIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * This expansion specifically targets itemTags.
 * It adds implicit casters to IIngredient and IData, so that you can use them wherever you can use IIngredient.
 * <p>
 * Only downside is that if you want to use Ingredient Transformers, you will need to call `asIIngredient()` first.
 */
@ZenRegister
@Document("vanilla/api/tag/ExpandItemTag")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.item.ItemDefinition>")
public class ExpandItemTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(MCTag<Item> internal) {
        
        final TagKey<Item> internalTag = internal.getTagKey();
        if(internalTag == null) {
            CraftTweakerAPI.LOGGER.warn("Tag '{}' does not exist, replacing with empty IIngredient", internal.getCommandString());
            return IIngredientEmpty.INSTANCE;
        }
        final Ingredient ingredient = Ingredient.of(internalTag);
        return new WrappingIIngredient(ingredient, internal.getCommandString());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IData asIData(MCTag<Item> internal) {
        
        return asIIngredient(internal).asIData();
    }
    
    @ZenCodeType.Method
    public static void add(MCTag<Item> internal, List<IItemStack> items) {
        
        internal.add(items.stream()
                .map(IItemStack::getDefinition)
                .toArray(Item[]::new));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredientWithAmount asIIngredientWithAmount(MCTag<Item> _this) {
        
        final IIngredient iIngredient = asIIngredient(_this);
        return iIngredient.asIIngredientWithAmount();
    }
    
}
