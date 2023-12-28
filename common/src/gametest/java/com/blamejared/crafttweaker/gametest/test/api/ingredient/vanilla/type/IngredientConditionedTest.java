package com.blamejared.crafttweaker.gametest.test.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamaged;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientConditioned;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.framework.annotation.parametized.ParameterizedGameTest;
import com.blamejared.crafttweaker.gametest.framework.parameterized.Arguments;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class IngredientConditionedTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @ParameterizedGameTest(argumentSource = @ParameterizedGameTest.Source(method = "testTestArguments"))
    @TestModifier(implicitSuccession = true)
    public void testTest(GameTestHelper helper, Arguments arguments) {
        
        CraftTweakerVanillaIngredient subject = IngredientConditioned.of(new IIngredientConditioned<IIngredient>(immutableStack(Items.DIAMOND_AXE), ConditionDamaged.getInstance()));
        assertThat(subject.test(arguments.input()), is(arguments.<Boolean> expected()));
    }
    
    public static Stream<Arguments.Builder> testTestArguments() {
        
        return Stream.of(
                Arguments.Builder.named("damaged item passes")
                        .input(() -> {
                            ItemStack stack = Items.DIAMOND_AXE.getDefaultInstance();
                            stack.setDamageValue(2);
                            return stack;
                        })
                        .expected(true),
                Arguments.Builder.named("undamaged item does not pass")
                        .input(Items.DIAMOND_AXE.getDefaultInstance())
                        .expected(false),
                Arguments.Builder.named("air does not pass")
                        .input(Items.AIR.getDefaultInstance())
                        .expected(false),
                Arguments.Builder.named("empty does not pass")
                        .input(ItemStack.EMPTY)
                        .expected(false),
                Arguments.Builder.named("null does not pass")
                        .expected(false)
        );
    }
    
}
