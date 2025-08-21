package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Supplier;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.TagIngredient")
@Document("vanilla/api/ingredient/type/TagIngredient")
public class TagIngredient implements IIngredient {
    
    private final Supplier<KnownTag<Item>> internal;
    private final TagKey<Item> key;
    
    private final IngredientConditions conditions = new IngredientConditions();
    private final IngredientTransformers transformers = new IngredientTransformers();
    
    public TagIngredient(TagKey<Item> key) {
        
        this.key = key;
        this.internal = Suppliers.memoize(() -> CraftTweakerTagRegistry.INSTANCE.findKnownManager(Registries.ITEM)
                .map(mcTags -> mcTags.tag(this.key))
                .orElseThrow(() -> new RuntimeException("Error while converting tag: '" + this.key + "' to an IIngredient!")));
    }
    
    public TagKey<Item> key() {
        
        return key;
    }
    
    @Override
    public boolean matches(IItemStack stack) {
        
        return internal.get().getInternal().contains(stack.getDefinition().builtInRegistryHolder());
    }
    
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return Ingredient.of(this.key());
    }
    
    @Override
    public String getCommandString() {
        
        return internal.get().getCommandString();
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return internal.get().getInternal()
                .stream()
                .map(GenericUtil::<Holder<Item>>uncheck)
                .map(Holder::value)
                .map(item -> IItemStack.of(item.getDefaultInstance(), this.conditions(), this.transformers()))
                .toArray(IItemStack[]::new);
    }
    
    @Override
    public IngredientTransformers transformers() {
        
        return transformers;
    }
    
    @Override
    public IngredientConditions conditions() {
        
        return conditions;
    }
    
    @Override
    public String toString() {
        
        return this.getCommandString();
    }
    
}
