package com.blamejared.crafttweaker.gametest.test.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientAny;
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
public class IngredientAnyTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @ParameterizedGameTest(argumentSource = @ParameterizedGameTest.Source(method = "testTestArguments"))
    @TestModifier(implicitSuccession = true)
    public void testTest(GameTestHelper helper, Arguments arguments) {
        
        CraftTweakerVanillaIngredient subject = IngredientAny.of();
        assertThat(subject.test(arguments.input()), is(arguments.<Boolean> expected()));
    }
    
    public static Stream<Arguments.Builder> testTestArguments() {
        
        return Stream.of(
                Arguments.Builder.named("item passes")
                        .input(Items.APPLE.getDefaultInstance())
                        .expected(true),
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
