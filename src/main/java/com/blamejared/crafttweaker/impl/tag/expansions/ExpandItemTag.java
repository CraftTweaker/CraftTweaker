package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.ingredients.IIngredientWrapped;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.item.MCItemDefinition>")
public class ExpandItemTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(MCTag<Item> _this) {
        final ITag<Item> internal = TagManagerItem.INSTANCE.getInternal(_this);
        if(internal == null) {
            CraftTweakerAPI.logWarning("Tag %s does not exist, replacing with empty IItemStack", _this
                    .getCommandString());
            return MCItemStack.EMPTY.get();
        }
        final Ingredient ingredient = Ingredient.fromTag(internal);
        return new IIngredientWrapped(ingredient, _this.getCommandString());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IData asIData(MCTag<Item> _this) {
        return asIIngredient(_this).asIData();
    }
}
