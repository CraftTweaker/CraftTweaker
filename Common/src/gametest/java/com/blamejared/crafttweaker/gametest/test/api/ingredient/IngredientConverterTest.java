package com.blamejared.crafttweaker.gametest.test.api.ingredient;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IngredientConverter;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.TestModifier;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessIngredient;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

@CraftTweakerGameTestHolder
public class IngredientConverterTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void emptyIngredientReturnsEmpty(GameTestHelper helper) {
        
        final Ingredient ingredient = Ingredient.EMPTY;
        
        final IIngredient result = IngredientConverter.fromIngredient(ingredient);
        
        assertThat(result).isSameInstanceAs(IIngredientEmpty.INSTANCE);
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void stackReturnsIItemStack(GameTestHelper helper) {
        
        final IItemStack expectedStack = immutableStack(Items.IRON_NUGGET);
        final Ingredient ingredient = Ingredient.of(expectedStack.getInternal());
        
        final IIngredient result = IngredientConverter.fromIngredient(ingredient);
        
        assertThat(expectedStack)
                .isEqualTo(result);
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void itemListReturnsListIngredient(GameTestHelper helper) {
        
        final IItemStack stackA = immutableStack(Items.IRON_INGOT);
        final IItemStack stackB = immutableStack(Items.IRON_NUGGET);
        
        final List<Ingredient.ItemValue> lists = Arrays.asList(
                new Ingredient.ItemValue(stackA.getInternal()),
                new Ingredient.ItemValue(stackB.getInternal())
        );
        final IIngredient[] expectedIngredients = new IIngredient[] {stackA, stackB};
        
        final Ingredient ingredient = AccessIngredient.callFromValues(lists.stream());
        
        //Act
        final IIngredient result = IngredientConverter.fromIngredient(ingredient);
        
        //Assert
        
        assertThat(result).isInstanceOf(IIngredientList.class);
        assertThat(((IIngredientList) result).getIngredients()).asList()
                .containsExactly((Object[]) expectedIngredients);
    }
    
}
