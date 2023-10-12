package com.blamejared.crafttweaker.gametest.test.api.ingredient;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientAny;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.zencode.expand.ExpandString;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.framework.annotation.parametized.ParameterizedGameTest;
import com.blamejared.crafttweaker.gametest.framework.parameterized.Arguments;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class IIngredientTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @ParameterizedGameTest(argumentSource = @ParameterizedGameTest.Source(method = "testMatchesArguments"))
    @TestModifier(implicitSuccession = true)
    public void testMatches(GameTestHelper helper, Arguments arguments) {
        
        IIngredient input = arguments.input();
        IItemStack other = arguments.other();
        boolean expected = arguments.expected();
        assertThat(input.matches(other), is(expected));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @ParameterizedGameTest(argumentSource = @ParameterizedGameTest.Source(method = "testIsEmptyArguments"))
    @TestModifier(implicitSuccession = true)
    public void testIsEmpty(GameTestHelper helper, Arguments arguments) {
        
        IIngredient input = arguments.input();
        boolean expected = arguments.expected();
        assertThat(input.isEmpty(), is(expected));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @ParameterizedGameTest(argumentSource = @ParameterizedGameTest.Source(method = "testContainsArguments"))
    @TestModifier(implicitSuccession = true)
    public void testContains(GameTestHelper helper, Arguments arguments) {
        
        IIngredient input = arguments.input();
        IItemStack other = arguments.other();
        boolean expected = arguments.expected();
        assertThat(input.contains(other), is(expected));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        IIngredient input = IItemStack.of(Items.APPLE.getDefaultInstance());
        DataResult<JsonElement> encodeResult = encode(IIngredient.CODEC, input);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult, is(parseJson("{'item': 'minecraft:apple'}")));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<IIngredient, JsonElement>> decode = decode(IIngredient.CODEC, parseJson("{'item': 'minecraft:apple'}"));
        Pair<IIngredient, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        assertThat(decodeResult.getFirst(), is(IItemStack.of(Items.APPLE.getDefaultInstance())));
    }
    
    public static Stream<Arguments.Builder> testMatchesArguments() {
        
        return Stream.of(
                Arguments.Builder.named("item matches sames")
                        .input(IItemStack.of(Items.APPLE))
                        .other(IItemStack.of(Items.APPLE))
                        .expected(true),
                Arguments.Builder.named("item matches item with bigger amount")
                        .input(IItemStack.of(Items.APPLE))
                        .other(IItemStack.of(Items.APPLE).setAmount(2))
                        .expected(true),
                Arguments.Builder.named("item matches item with data")
                        .input(IItemStack.of(Items.APPLE))
                        .other(IItemStack.of(Items.APPLE)
                                .withTag(new MapData(Map.of("Custom", ExpandString.asData("Data")))))
                        .expected(true),
                Arguments.Builder.named("item does not match different item")
                        .input(IItemStack.of(Items.ARROW))
                        .other(IItemStack.of(Items.APPLE))
                        .expected(false),
                Arguments.Builder.named("item does not match item with smaller amount")
                        .input(IItemStack.of(Items.APPLE).setAmount(2))
                        .other(IItemStack.of(Items.APPLE))
                        .expected(false),
                Arguments.Builder.named("item with data does not match item without data")
                        .input(IItemStack.of(Items.APPLE)
                                .withTag(new MapData(Map.of("Custom", ExpandString.asData("Data")))))
                        .other(IItemStack.of(Items.APPLE))
                        .expected(false)
        );
    }
    
    public static Stream<Arguments.Builder> testIsEmptyArguments() {
        
        return Stream.of(
                Arguments.Builder.named("air is empty")
                        .input(IItemStack.of(Items.AIR))
                        .expected(true),
                Arguments.Builder.named("stack with 0 count is empty")
                        .input(IItemStack.of(Items.APPLE).setAmount(0))
                        .expected(true),
                Arguments.Builder.named("empty stack is empty")
                        .input(IItemStack.empty())
                        .expected(true),
                Arguments.Builder.named("empty ingredient is empty")
                        .input(IIngredientEmpty.INSTANCE)
                        .expected(true),
                Arguments.Builder.named("empty list is empty")
                        .input(new IIngredientList(new IIngredient[0]))
                        .expected(true),
                Arguments.Builder.named("item is not empty")
                        .input(IItemStack.of(Items.APPLE))
                        .expected(false),
                Arguments.Builder.named("any is not empty")
                        .input(IIngredientAny.INSTANCE)
                        .expected(false)
        );
    }
    
    public static Stream<Arguments.Builder> testContainsArguments() {
        
        return Stream.of(
                Arguments.Builder.named("any contains item")
                        .input(IIngredientAny.INSTANCE)
                        .other(IItemStack.of(Items.APPLE))
                        .expected(true),
                Arguments.Builder.named("empty ingredient does not contain item")
                        .input(IIngredientEmpty.INSTANCE)
                        .other(IItemStack.of(Items.APPLE))
                        .expected(false),
                Arguments.Builder.named("empty stack does not contain item")
                        .input(IItemStack.empty())
                        .other(IItemStack.of(Items.APPLE))
                        .expected(false),
                Arguments.Builder.named("custom condition contains")
                        .input(() -> IIngredientAny.INSTANCE.onlyIf("even",
                                stack -> stack.getRegistryName().getPath().length() % 2 == 0))
                        .other(IItemStack.of(Items.AZALEA))
                        .expected(true),
                Arguments.Builder.named("custom condition does not contain")
                        .input(() -> IIngredientAny.INSTANCE.onlyIf("even",
                                stack -> stack.getRegistryName().getPath().length() % 2 == 0))
                        .other(IItemStack.of(Items.ARROW))
                        .expected(false)
        );
    }
    
}
