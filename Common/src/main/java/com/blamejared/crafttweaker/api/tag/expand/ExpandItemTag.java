package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.ingredient.type.TagIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
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
@ZenCodeType.Expansion("crafttweaker.api.tag.type.KnownTag<crafttweaker.api.item.ItemDefinition>")
public class ExpandItemTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(KnownTag<Item> internal) {
        
        TagKey<?> tagKey = internal.getTagKey();
        if(!internal.exists() && !Services.PLATFORM.isDataGen()) {
            CommonLoggers.api()
                    .warn("Tag '{}' does not exist, replacing with empty IIngredient", internal.getCommandString());
            return IIngredientEmpty.INSTANCE;
        }
        return new TagIngredient(internal);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IData asIData(KnownTag<Item> internal) {
        
        return asIIngredient(internal).asIData();
    }
    
    @ZenCodeType.Method
    public static void add(KnownTag<Item> internal, List<IItemStack> items) {
        
        internal.add(items.stream()
                .map(IItemStack::getDefinition)
                .toArray(Item[]::new));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredientWithAmount asIIngredientWithAmount(KnownTag<Item> _this) {
        
        final IIngredient iIngredient = asIIngredient(_this);
        return iIngredient.asIIngredientWithAmount();
    }
    
}
