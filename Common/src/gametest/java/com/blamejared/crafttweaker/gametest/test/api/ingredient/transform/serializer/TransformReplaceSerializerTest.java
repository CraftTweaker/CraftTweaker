package com.blamejared.crafttweaker.gametest.test.api.ingredient.transform.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformReplace;
import com.blamejared.crafttweaker.api.item.IItemStack;
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
public class TransformReplaceSerializerTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testNetwork(GameTestHelper helper) {
        
        TransformReplaceSerializer instance = TransformReplaceSerializer.INSTANCE;
        TransformReplace<IIngredient> input = new TransformReplace<>(IItemStack.empty());
        FriendlyByteBuf buffer = createBuffer();
        instance.toNetwork(buffer, input);
        assertThat(instance.fromNetwork(buffer), is(input));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecEncode(GameTestHelper helper) {
        
        TransformReplace<IIngredient> input = new TransformReplace<>(IItemStack.empty());
        DataResult<JsonElement> encodeResult = encode(TransformReplaceSerializer.CODEC, input);
        JsonElement jsonResult = encodeResult.getOrThrow(false, this::fail);
        assertThat(jsonResult.isJsonObject(), is(true));
        JsonObject obj = jsonResult.getAsJsonObject();
        assertThat(obj.get("item").isJsonObject(), is(true));
        JsonObject item = obj.get("item").getAsJsonObject();
        assertThat(item.get("id"), is(new JsonPrimitive("minecraft:air")));
        assertThat(item.get("Count"), is(new JsonPrimitive(0)));
        assertThat(obj.get("mutable"), is(new JsonPrimitive(false)));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testCodecDecode(GameTestHelper helper) {
        
        DataResult<Pair<TransformReplace<?>, JsonElement>> decode = decode(TransformReplaceSerializer.CODEC, parseJson("""
                {
                    "item": {
                        "id": "minecraft:air",
                        "Count": 0
                    },
                    "mutable": false
                }
                """));
        Pair<TransformReplace<?>, JsonElement> decodeResult = decode.getOrThrow(false, this::fail);
        assertThat(decodeResult.getFirst(), is(new TransformReplace<>(IItemStack.empty())));
    }
    
}
