package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessIngredient;
import com.faux.ingredientextension.api.ingredient.IngredientExtendable;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public class IngredientList extends IngredientExtendable {
    
    private final List<Ingredient> children;
    private final boolean requiresTesting;
    
    public IngredientList(List<Ingredient> children) {
        
        super(getValues(children));
        this.children = children;
        // If an ingredient isn't accessible, we assume it to be simple.
        // IDEA thinks that Ingredient isn't extendable because it is final, but we AW it.
        //noinspection ConstantConditions
        this.requiresTesting = children.stream()
                .filter(ingredient -> ingredient instanceof IngredientExtendable)
                .map(ingredient -> (IngredientExtendable) ingredient)
                .allMatch(IngredientExtendable::requiresTesting);
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        if(stack == null) {
            return false;
        }
        
        return children.stream().anyMatch(child -> child.test(stack));
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientListSerializer.INSTANCE;
    }
    
    @Override
    public boolean requiresTesting() {
        
        return requiresTesting;
    }
    
    
    public List<Ingredient> getChildren() {
        
        return children;
    }
    
    private static Stream<Value> getValues(List<Ingredient> children) {
        
        // TODO This may cause issues since we have such a big value array, it needs more investigation
        return children.stream()
                .filter(Objects::nonNull) // stops AccessIngredient::getValues from giving a warning
                .map(ingredient -> (AccessIngredient) (Object) ingredient)
                .map(AccessIngredient::crafttweaker$getValues)
                .flatMap(Arrays::stream);
    }
    
}
