package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IngredientList extends CompoundIngredient {
    
    public IngredientList(List<Ingredient> children) {
        
        super(children);
    }
    
    @Override
    public JsonElement serialize() {
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
        jsonObject.add("ingredients", super.serialize());
        return jsonObject;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientListSerializer.INSTANCE;
    }
    
    
}
