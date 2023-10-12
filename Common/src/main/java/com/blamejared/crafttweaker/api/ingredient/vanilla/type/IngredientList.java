package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IngredientList implements CraftTweakerVanillaIngredient {
    
    public static IngredientList of(List<Ingredient> children) {
        
        return new IngredientList(children);
    }
    
    public static Ingredient ingredient(List<Ingredient> children) {
        
        return Services.PLATFORM.getIngredientList(children);
    }
    
    private final List<Ingredient> children;
    private final boolean requiresTesting;
    
    private IngredientList(List<Ingredient> children) {
        
        this.children = children;
        this.requiresTesting = children.stream().anyMatch(Services.PLATFORM::doesIngredientRequireTesting);
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
    public boolean requiresTesting() {
        
        return requiresTesting;
    }
    
    public List<Ingredient> getChildren() {
        
        return children;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public IngredientListSerializer serializer() {
        
        return IngredientListSerializer.INSTANCE;
    }
    
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientList that = (IngredientList) o;
        return requiresTesting == that.requiresTesting && Objects.equals(getChildren(), that.getChildren());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getChildren(), requiresTesting);
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("IngredientList{");
        sb.append("children=").append(children);
        sb.append(", requiresTesting=").append(requiresTesting);
        sb.append('}');
        return sb.toString();
    }
    
}
