package com.blamejared.crafttweaker.api.ingredient;

import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientCraftTweakerBase;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientSingleton;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.expand.ExpandItemTag;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessIngredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

public class IngredientConverter {
    
    public static IIngredient fromIngredient(Ingredient ingredient) {
        
        // TODO: this needs to be overhauled to handle empty values and the fabric ingredient system
        //noinspection ConstantConditions
        if(((Object) ingredient) instanceof IngredientCraftTweakerBase base) {
            return base.getCrTIngredient();
        }
        
        //noinspection ConstantConditions
        if(((Object) ingredient) instanceof IngredientSingleton<?> single) {
            return single.getInstance();
        }
        
        if(ingredient == Ingredient.EMPTY) {
            return empty();
        }
        
        return fromIItemLists(((AccessIngredient) (Object) ingredient).crafttweaker$getValues());
    }
    
    private static IIngredient fromIItemLists(Ingredient.Value... itemLists) {
        
        final IIngredient[] ingredients = Arrays.stream(itemLists)
                .map(IngredientConverter::fromIItemList)
                .filter(IngredientConverter::notEmptyStack)
                .toArray(IIngredient[]::new);
        
        return mergeIngredients(ingredients);
    }
    
    
    //All Ingredients have a reference to this
    //Allows us to check for tags
    private static IIngredient fromIItemList(Ingredient.Value value) {
        
        //Tags -> MCTag
        if(value instanceof Ingredient.TagValue) {
            return fromTagList((Ingredient.TagValue) value);
        }
        
        //Forge StackList, or anything else -> check matching stacks
        final IItemStack[] ingredients = value.getItems()
                .stream()
                .filter(stack -> !stack.isEmpty())
                .map(IItemStack::of)
                .toArray(IItemStack[]::new);
        
        return mergeIngredients(ingredients);
    }
    
    private static IIngredient fromTagList(Ingredient.TagValue value) {
        
        final ResourceLocation location = value.tag().location();
        return CraftTweakerTagRegistry.INSTANCE.findKnownManager(Registries.ITEM)
                .map(mcTags -> mcTags.tag(location))
                .map(ExpandItemTag::asIIngredient)
                .orElseThrow(() -> new RuntimeException("Error while converting ingredient: '" + value + "' to an IIngredient!"));
    }
    
    private static IIngredient empty() {
        
        return IIngredientEmpty.INSTANCE;
    }
    
    private static IIngredient mergeIngredients(IIngredient... ingredients) {
        
        if(ingredients.length == 0) {
            return empty();
        }
        
        if(ingredients.length == 1) {
            return ingredients[0];
        }
        return new IIngredientList(ingredients);
    }
    
    private static boolean notEmptyStack(IIngredient iIngredient) {
        
        return !(iIngredient instanceof IItemStack && iIngredient.isEmpty());
    }
    
}
