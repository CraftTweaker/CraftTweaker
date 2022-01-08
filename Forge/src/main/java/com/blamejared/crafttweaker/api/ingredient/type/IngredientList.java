package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class IngredientList extends CompoundIngredient {
    
    public IngredientList(List<Ingredient> children) {
        
        super(children);
    }
    
    @Override
    public JsonElement toJson() {
        
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", CraftingHelper.getID(IngredientListSerializer.INSTANCE).toString());
        jsonObject.add("ingredients", super.toJson());
        return jsonObject;
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientListSerializer.INSTANCE;
    }
    
    
}
