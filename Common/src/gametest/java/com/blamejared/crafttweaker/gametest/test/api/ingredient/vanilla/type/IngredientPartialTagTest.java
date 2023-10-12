package com.blamejared.crafttweaker.gametest.test.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientPartialTag;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.framework.annotation.parametized.ParameterizedGameTest;
import com.blamejared.crafttweaker.gametest.framework.parameterized.Arguments;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class IngredientPartialTagTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @ParameterizedGameTest(argumentSource = @ParameterizedGameTest.Source(method = "testTestArguments"))
    @TestModifier(implicitSuccession = true)
    public void testTest(GameTestHelper helper, Arguments arguments) {
        
        CraftTweakerVanillaIngredient subject = IngredientPartialTag.of(stack(Items.APPLE, compoundTag -> {
            compoundTag.putString("key", "value");
            compoundTag.putString("first", "second");
        }));
        assertThat(subject.test(arguments.input()), is(arguments.<Boolean> expected()));
    }
    
    public static Stream<Arguments.Builder> testTestArguments() {
        
        return Stream.of(
                Arguments.Builder.named("exact passes")
                        .input(() -> {
                            ItemStack stack = Items.APPLE.getDefaultInstance();
                            CompoundTag tag = stack.getOrCreateTag();
                            tag.putString("key", "value");
                            tag.putString("first", "second");
                            return stack;
                        }).expected(true),
                Arguments.Builder.named("partial passes")
                        .input(() -> {
                            ItemStack stack = Items.APPLE.getDefaultInstance();
                            CompoundTag tag = stack.getOrCreateTag();
                            tag.putString("key", "value");
                            tag.putString("first", "second");
                            tag.putString("third", "fourth");
                            return stack;
                        }).expected(true),
                Arguments.Builder.named("same keys different values fail")
                        .input(() -> {
                            ItemStack stack = Items.APPLE.getDefaultInstance();
                            CompoundTag tag = stack.getOrCreateTag();
                            tag.putString("key", "second");
                            tag.putString("first", "second");
                            return stack;
                        }).expected(false),
                Arguments.Builder.named("same nbt different item fail")
                        .input(() -> {
                            ItemStack stack = Items.ARROW.getDefaultInstance();
                            CompoundTag tag = stack.getOrCreateTag();
                            tag.putString("key", "second");
                            tag.putString("first", "second");
                            return stack;
                        }).expected(false),
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
