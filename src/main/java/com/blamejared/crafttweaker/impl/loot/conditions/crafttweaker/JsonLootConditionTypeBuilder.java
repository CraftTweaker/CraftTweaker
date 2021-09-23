package com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.data.StringData;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.MCLootCondition;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.Gson;
import net.minecraft.loot.LootSerializers;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Builder to create an arbitrary loot condition through a JSON-based structure.
 *
 * This builder allows to create arbitrary JSON structures, which can reference other mod-added loot conditions that
 * do not provide native CraftTweaker support. The given JSON structure needs to be a valid JSON-object representation,
 * meaning it needs to be an instance of {@link MapData}. The JSON may or may not specify the type. If the type is
 * specified then it must match the one given in the builder; otherwise the type is automatically added.
 *
 * The JSON structure along with the condition type is mandatory.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.crafttweaker.Json")
@Document("vanilla/api/loot/conditions/crafttweaker/Json")
public final class JsonLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private static final Gson GSON = LootSerializers.func_237387_b_().disableHtmlEscaping().create();
    
    private ResourceLocation name;
    private IData json;
    
    JsonLootConditionTypeBuilder() {}
    
    /**
     * Creates an {@link ILootCondition} of the given <code>type</code> parsing the given <code>json</code>.
     *
     * The JSON must respect the constraints specified in the class documentation. It is suggested to use this method
     * sparingly, preferring to instead create JSON conditions as needed from within a {@link CTLootConditionBuilder}.
     *
     * If no valid condition is found, or the JSON is invalid, an error gets thrown.
     *
     * This method is equivalent to <code>makeJson</code> in {@link CTLootConditionBuilder}.
     *
     * @param type A {@link ResourceLocation} identifying the type of the loot condition to create.
     * @param json The JSON data, according to the given constraints.
     *
     * @return An {@link ILootCondition} instance built according to the given data, if possible.
     *
     * @see CTLootConditionBuilder#makeJson(ResourceLocation, IData)
     */
    @ZenCodeType.Method
    public static ILootCondition create(final ResourceLocation type, final IData json) {
        
        return CTLootConditionBuilder.makeJson(type, json);
    }
    
    /**
     * Creates an {@link ILootCondition} of the given <code>type</code> parsing the given <code>json</code>.
     *
     * The name is treated as a {@link ResourceLocation}, lacking the type safety of the bracket handler. For this
     * reason, it's suggested to prefer the method with a {@link ResourceLocation} as parameter.
     *
     * The JSON must respect the constraints specified in the class documentation. It is suggested to use this method
     * sparingly, preferring to instead create JSON conditions as needed from within a {@link CTLootConditionBuilder}.
     *
     * If no valid condition is found, or the JSON is invalid, an error gets thrown.
     *
     * This method is equivalent to <code>makeJson</code> in {@link CTLootConditionBuilder}.
     *
     * @param type A string in resource location format identifying the type of the loot condition to create.
     * @param json The JSON data, according to the given constraints.
     *
     * @return An {@link ILootCondition} instance built according to the given data, if possible.
     *
     * @see CTLootConditionBuilder#makeJson(String, IData)
     */
    @ZenCodeType.Method
    public static ILootCondition create(final String type, final IData json) {
        
        return CTLootConditionBuilder.makeJson(type, json);
    }
    
    /**
     * Sets the type of the condition that will be built along with the JSON representation that will be parsed.
     *
     * The JSON must respect the constraints specified in the class documentation.
     *
     * @param type A {@link ResourceLocation} identifying the type of the loot condition.
     * @param json The JSON data, according to the given constraints.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public JsonLootConditionTypeBuilder withJson(final ResourceLocation type, final IData json) {
        
        this.name = type;
        this.json = json;
        return this;
    }
    
    /**
     * Sets the type of the condition that will be built along with the JSON representation that will be parsed.
     *
     * The name is treated as a {@link ResourceLocation}, lacking the type safety of the bracket handler. For this
     * reason, it's suggested to prefer the method with a {@link ResourceLocation} as parameter.
     *
     * The JSON must respect the constraints specified in the class documentation.
     *
     * @param type A string in resource location format identifying the type of the loot condition.
     * @param json The JSON data, according to the given constraints.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public JsonLootConditionTypeBuilder withJson(final String type, final IData json) {
        
        return this.withJson(new ResourceLocation(type), json);
    }
    
    @Override
    public ILootCondition finish() {
        
        final IData jsonData = this.json.copyInternal(); // Better safe than sorry
        
        if(!(jsonData instanceof MapData)) {
            throw new IllegalStateException("Json loot condition IData should be a MapData");
        }
        
        final MapData data = (MapData) jsonData;
        final String type = this.name.toString();
        final IData jsonType = data.getAt("condition");
        if(jsonType != null && !(jsonType instanceof StringData)) {
            throw new IllegalStateException("Json condition type isn't a string: expected '" + type + "', but found '" + jsonType + "'");
        }
        
        if(jsonType == null) {
            data.put("condition", new StringData(type));
        } else {
            final StringData statedType = (StringData) jsonType;
            final String storedType = statedType.getInternal().getString();
            if(!storedType.equals(type)) {
                throw new IllegalStateException("Unable to override loot condition type: '" + jsonType + "' was given, but we expected '" + type + "'");
            }
        }
        
        final net.minecraft.loot.conditions.ILootCondition mcCondition;
        try {
            mcCondition = GSON.fromJson(data.toJsonString(), net.minecraft.loot.conditions.ILootCondition.class);
        } catch(final ClassCastException e) {
            throw new IllegalStateException("The returned loot condition is not valid", e);
        }
        
        return new MCLootCondition(mcCondition);
    }
    
}
