package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.tag.manager.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tags.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.item.MCItemDefinition>")
public class ExpandItemTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(MCTag<MCItemDefinition> _this) {
        final ITag<Item> internal = TagManagerItem.INSTANCE.getInternal(_this);
        if(internal == null) {
            CraftTweakerAPI.logWarning("Tag %s does not exist, replacing with empty IItemStack", _this.getCommandString());
            return MCItemStack.EMPTY.get();
        }
        final Ingredient ingredient = Ingredient.fromTag(internal);
        return IIngredient.fromIngredient(ingredient);
    }
}
