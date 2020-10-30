package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.data.StringData;
import com.blamejared.crafttweaker.impl.loot.MCLootCondition;
import com.blamejared.crafttweaker.impl.util.MCResourceLocation;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.Gson;
import net.minecraft.loot.LootSerializers;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.Json")
@Document("vanilla/api/loot/condition/Json")
public class JsonLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private static final Gson GSON = LootSerializers.func_237387_b_().disableHtmlEscaping().create();

    private MCResourceLocation name;
    private IData json;

    JsonLootConditionTypeBuilder() {}

    @ZenCodeType.Method
    public static ILootCondition create(final MCResourceLocation type, final IData json) {
        return CTLootConditionBuilder.makeJson(type, json);
    }

    @ZenCodeType.Method
    public static ILootCondition create(final String type, final IData json) {
        return CTLootConditionBuilder.makeJson(type, json);
    }

    @ZenCodeType.Method
    public JsonLootConditionTypeBuilder withJson(final MCResourceLocation type, final IData json) {
        this.name = type;
        this.json = json;
        return this;
    }

    @ZenCodeType.Method
    public JsonLootConditionTypeBuilder withJson(final String type, final IData json) {
        return this.withJson(new MCResourceLocation(new ResourceLocation(type)), json);
    }

    @Override
    public ILootCondition finish() {
        final IData jsonData = this.json.copyInternal(); // Better safe than sorry

        if (!(jsonData instanceof MapData)) {
            throw new IllegalStateException("Json loot condition IData should be a MapData");
        }

        final MapData data = (MapData) jsonData;
        final String type = this.name.getInternal().toString();
        final IData jsonType = data.get("condition");
        if (jsonType != null && !(jsonType instanceof StringData)) {
            throw new IllegalStateException("Json condition type isn't a string: expected '" + type + "', but found '" + jsonType + "'");
        }

        if (jsonType == null) {
            data.put("condition", new StringData(type));
        } else {
            final StringData statedType = (StringData) jsonType;
            final String storedType = statedType.getInternal().getString();
            if (!storedType.equals(type)) {
                throw new IllegalStateException("Unable to override loot condition type: '" + jsonType + "' was given, but we expected '" + type + "'");
            }
        }

        final net.minecraft.loot.conditions.ILootCondition mcCondition;
        try {
             mcCondition = GSON.fromJson(data.toJsonString(), net.minecraft.loot.conditions.ILootCondition.class);
        } catch (final ClassCastException e) {
            throw new IllegalStateException("The returned loot condition is not valid", e);
        }

        return new MCLootCondition(mcCondition);
    }
}
