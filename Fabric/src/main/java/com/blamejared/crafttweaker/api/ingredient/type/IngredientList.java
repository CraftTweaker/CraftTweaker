package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.*;
import java.util.*;

@ParametersAreNonnullByDefault
public class IngredientList implements CustomIngredient {
    
    private final List<Ingredient> children;
    private final boolean requiresTesting;
    
    public IngredientList(List<Ingredient> children) {
        
        this.children = children;
        // If an ingredient isn't accessible, we assume it to be simple.
        // IDEA thinks that Ingredient isn't extendable because it is final, but we AW it.
        //noinspection ConstantConditions
        this.requiresTesting = children.stream()
                .filter(CustomIngredient.class::isInstance)
                .map(CustomIngredient.class::cast)
                .anyMatch(CustomIngredient::requiresTesting);
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        if(stack == null) {
            return false;
        }
        
        return children.stream().anyMatch(child -> child.test(stack));
    }
    
    @Override
    public List<ItemStack> getMatchingStacks() {
        
        return getChildren().stream().map(Ingredient::getItems).flatMap(Arrays::stream).toList();
    }
    
    @Override
    public CustomIngredientSerializer<IngredientList> getSerializer() {
        
        return IngredientListSerializer.INSTANCE;
    }
    
    @Override
    public boolean requiresTesting() {
        
        return requiresTesting;
    }
    
    
    public List<Ingredient> getChildren() {
        
        return children;
    }
    
}
