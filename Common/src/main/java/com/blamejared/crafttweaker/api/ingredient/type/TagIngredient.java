package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.natives.item.ExpandItem;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.TagIngredient")
@Document("vanilla/api/ingredient/type/TagIngredient")
public class TagIngredient implements IIngredient {
    
    private final KnownTag<Item> internal;
    
    public TagIngredient(KnownTag<Item> internal) {
        
        this.internal = internal;
    }
    
    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        return Arrays.stream(getItems()).anyMatch(item -> item.matches(stack, true));
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return Ingredient.of((TagKey<Item>) internal.getTagKey());
    }
    
    @Override
    public String getCommandString() {
        
        return internal.getCommandString();
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return internal.getInternal()
                .getValues()
                .stream()
                .map(Holder::value)
                .map(ExpandItem::getDefaultInstance)
                .toArray(IItemStack[]::new);
    }
    
}
