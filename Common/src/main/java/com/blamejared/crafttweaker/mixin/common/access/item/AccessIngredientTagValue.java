package com.blamejared.crafttweaker.mixin.common.access.item;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Ingredient.TagValue.class)
public interface AccessIngredientTagValue {
    
    @Accessor("tag")
    Tag<Item> crafttweaker$getTag();
    
}
