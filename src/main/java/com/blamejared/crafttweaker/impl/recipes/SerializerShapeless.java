package com.blamejared.crafttweaker.impl.recipes;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SerializerShapeless extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CTRecipeShapeless> {
    
    //    private final T recipe;
    
    public SerializerShapeless() {
        //        this.recipe = recipe;
    }
    
    private static NonNullList<Ingredient> readIngredients(JsonArray p_199568_0_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        
        for(int i = 0; i < p_199568_0_.size(); ++i) {
            Ingredient ingredient = Ingredient.deserialize(p_199568_0_.get(i));
            if(!ingredient.hasNoMatchingItems()) {
                nonnulllist.add(ingredient);
            }
        }
        
        return nonnulllist;
    }
    
    @Override
    public CTRecipeShapeless read(ResourceLocation recipeId, JsonObject json) {
        NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
        IIngredient[] ingredients = new IIngredient[nonnulllist.size()];
        for(int i = 0; i < nonnulllist.size(); i++) {
            ingredients[i] = new MCItemStack(nonnulllist.get(i).getMatchingStacks()[0]);
        }
        if(nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
        } else if(nonnulllist.size() > 3 * 3) {
            throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (3 * 3));
        } else {
            ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new CTRecipeShapeless(recipeId.getPath(), new MCItemStack(itemstack), ingredients, null);
        }
        
    }
    
    @Override
    public CTRecipeShapeless read(ResourceLocation recipeId, PacketBuffer buffer) {
        int i = buffer.readVarInt();
        IIngredient[] ingredients = new IIngredient[i];
        
        for(int j = 0; j < ingredients.length; ++j) {
            ingredients[j] = IIngredient.fromIngredient(Ingredient.read(buffer));
        }
        
        ItemStack itemstack = buffer.readItemStack();
        return new CTRecipeShapeless(recipeId.getPath(), new MCItemStack(itemstack), ingredients, null);
    }
    
    @Override
    public void write(PacketBuffer buffer, CTRecipeShapeless recipe) {
        buffer.writeVarInt(recipe.getIngredients().size());
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buffer);
        }
        buffer.writeItemStack(recipe.getRecipeOutput());
    }
    
    
}
