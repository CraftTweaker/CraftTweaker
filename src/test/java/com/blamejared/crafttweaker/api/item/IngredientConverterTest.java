package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.CraftTweakerTest;
import com.blamejared.crafttweaker.impl.item.MCIngredientList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientConverterTest extends CraftTweakerTest {
    
    private static void assertIsEmptyStack(IIngredient result) {
        
        assertThat(result).isInstanceOfSatisfying(IItemStack.class,
                stack -> assertThat(stack.getInternal()).isSameAs(ItemStack.EMPTY));
    }
    
    @Test
    void emptyIngredientReturnsEmpty() {
        //Arrange
        final Ingredient ingredient = Ingredient.EMPTY;
        
        //Act
        final IIngredient result = IngredientConverter.fromIngredient(ingredient);
        
        //Assert
        assertIsEmptyStack(result);
    }
    
    @Test
    void itemStackReturnsIItemStack() {
        //Arrange
        final IItemStack expectedStack = testContext.mockItems.ironNugget;
        final Ingredient ingredient = Ingredient.fromStacks(expectedStack.getInternal());
        
        //Act
        final IIngredient result = IngredientConverter.fromIngredient(ingredient);
        
        //Assert
        assertThat(expectedStack).isEqualTo(result);
    }
    
    @Test
    void itemListReturnsListIngredient() {
        
        final IItemStack stackA = testContext.mockItems.ironIngot;
        final IItemStack stackB = testContext.mockItems.ironNugget;
        
        final List<Ingredient.SingleItemList> lists = Arrays.asList(
                new Ingredient.SingleItemList(stackA.getInternal()),
                new Ingredient.SingleItemList(stackB.getInternal())
        );
        final IIngredient[] expectedIngredients = new IIngredient[] {stackA, stackB};
        
        final Ingredient ingredient = Ingredient.fromItemListStream(lists.stream());
        
        //Act
        final IIngredient result = IngredientConverter.fromIngredient(ingredient);
        
        //Assert
        assertThat(result).isInstanceOfSatisfying(MCIngredientList.class,
                list -> assertThat(list.getIngredients()).contains(expectedIngredients));
    }
    
    //TODO, how to test tags here?
    
}