package com.blamejared.crafttweaker.gametest.test.api.ingredient.transform.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReuseSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformReuse;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.FriendlyByteBuf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CraftTweakerGameTestHolder
public class TransformReuseSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testNetwork(GameTestHelper helper) {
        
        TransformReuseSerializer instance = TransformReuseSerializer.INSTANCE;
        TransformReuse<IIngredient> input = TransformReuse.getInstance();
        FriendlyByteBuf buffer = createBuffer();
        instance.toNetwork(buffer, input);
        assertThat(instance.fromNetwork(buffer), is(input));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        TransformReuse<IIngredient> input = TransformReuse.getInstance();
        DataResult<JsonElement> encodeResult = encode(TransformReuseSerializer.CODEC, input);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult, is(new JsonObject()));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<TransformReuse<?>, JsonElement>> decode = decode(TransformReuseSerializer.CODEC, new JsonObject());
        Pair<TransformReuse<?>, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        assertThat(decodeResult.getFirst(), is(TransformReuse.getInstance()));
    }
    
}
