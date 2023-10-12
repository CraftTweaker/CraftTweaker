package com.blamejared.crafttweaker.gametest.test.api.ingredient.transform.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformDamage;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.FriendlyByteBuf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class TransformDamageSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testNetwork(GameTestHelper helper) {
        
        TransformDamageSerializer instance = TransformDamageSerializer.INSTANCE;
        TransformDamage<IIngredient> input = new TransformDamage<>(2);
        FriendlyByteBuf buffer = createBuffer();
        instance.toNetwork(buffer, input);
        assertThat(instance.fromNetwork(buffer), is(input));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        TransformDamage<IIngredient> input = new TransformDamage<>(2);
        DataResult<JsonElement> encodeResult = encode(TransformDamageSerializer.CODEC, input);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.getAsInt(), is(2));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<TransformDamage<?>, JsonElement>> decode = decode(TransformDamageSerializer.CODEC, parseJson("2"));
        Pair<TransformDamage<?>, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        assertThat(decodeResult.getFirst(), is(new TransformDamage<>(2)));
    }
    
}
