package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.impl.item.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import org.junit.jupiter.api.*;

import java.util.*;

class IngredientConverterTest extends CraftTweakerTest {
    
    private static void assertIsEmptyStack(IIngredient result) {
        
        Assertions.assertTrue(result instanceof IItemStack);
        Assertions.assertSame(ItemStack.EMPTY, ((IItemStack) result).getInternal());
    }
    
    private static <T> void assertInstanceOf(T object, Class<? extends T> cls) {
        
        Assertions.assertTrue(cls.isInstance(object), "Expected " + object + " to be instanceof " + cls.getCanonicalName());
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
        Assertions.assertEquals(expectedStack, result);
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
        assertInstanceOf(result, MCIngredientList.class);
        Assertions.assertArrayEquals(expectedIngredients, ((MCIngredientList) result).getIngredients());
    }
    
    //TODO, how to test tags here?
    
}