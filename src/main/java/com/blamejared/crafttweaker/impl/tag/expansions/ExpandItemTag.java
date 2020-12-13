package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.ingredients.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.tag.manager.*;
import com.blamejared.crafttweaker.impl_native.item.ExpandItem;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tags.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.item.ExpandItem>")
public class ExpandItemTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(MCTag<ExpandItem> _this) {
        final ITag<Item> internal = TagManagerItem.INSTANCE.getInternal(_this);
        if(internal == null) {
            CraftTweakerAPI.logWarning("Tag %s does not exist, replacing with empty IItemStack", _this.getCommandString());
            return MCItemStack.EMPTY.get();
        }
        final Ingredient ingredient = Ingredient.fromTag(internal);
        return new IIngredientWrapped(ingredient, _this.getCommandString());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IData asIData(MCTag<ExpandItem> _this) {
        return asIIngredient(_this).asIData();
    }
}
