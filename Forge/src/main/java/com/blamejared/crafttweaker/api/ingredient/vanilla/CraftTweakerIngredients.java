package com.blamejared.crafttweaker.api.ingredient.vanilla;


import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.CraftTweakerVanillaIngredientSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientAny;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientList;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientPartialTag;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientTransformed;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;

import java.util.List;

public class CraftTweakerIngredients {
    
    private static final BiMap<CraftTweakerVanillaIngredientSerializer<?>, DelegatingCustomIngredientSerializer<?>> SERIALIZER_CACHE = HashBiMap.create();
    private static final BiMap<CraftTweakerVanillaIngredient, DelegatingCustomIngredient<?>> SINGLETON_CACHE = HashBiMap.create();
    
    public static class Ingredients {
        
        public static Ingredient any() {
            
            return SINGLETON_CACHE.computeIfAbsent(IngredientAny.of(), DelegatingCustomIngredient::new);
        }
        
        public static <I extends IIngredient, T extends IIngredientConditioned<I>> Ingredient conditioned(T condition) {
            
            return new DelegatingCustomIngredient<>(IngredientConditioned.of(condition));
        }
        
        public static <I extends IIngredient, T extends IIngredientTransformed<I>> Ingredient transformed(T transformed) {
            
            return new DelegatingCustomIngredient<>(IngredientTransformed.of(transformed));
        }
        
        public static Ingredient list(List<Ingredient> children) {
            
            return new DelegatingCustomIngredient<>(IngredientList.of(children));
        }
        
        public static Ingredient partialTag(ItemStack stack) {
            
            return new DelegatingCustomIngredient<>(new IngredientPartialTag(stack));
        }
        
        static <T extends CraftTweakerVanillaIngredient> DelegatingCustomIngredient<T> of(T ingredient) {
            
            if(ingredient.singleton()) {
                return GenericUtil.uncheck(SINGLETON_CACHE.computeIfAbsent(ingredient, DelegatingCustomIngredient::new));
            }
            return new DelegatingCustomIngredient<>(ingredient);
        }
        
    }
    
    public static class Serializers {
        
        public static final IIngredientSerializer<?> ANY = SERIALIZER_CACHE.computeIfAbsent(IngredientAnySerializer.INSTANCE, DelegatingCustomIngredientSerializer::new);
        public static final IIngredientSerializer<?> CONDITIONED = SERIALIZER_CACHE.computeIfAbsent(IngredientConditionedSerializer.INSTANCE, DelegatingCustomIngredientSerializer::new);
        public static final IIngredientSerializer<?> TRANSFORMED = SERIALIZER_CACHE.computeIfAbsent(IngredientTransformedSerializer.INSTANCE, DelegatingCustomIngredientSerializer::new);
        public static final IIngredientSerializer<?> LIST = SERIALIZER_CACHE.computeIfAbsent(IngredientListSerializer.INSTANCE, DelegatingCustomIngredientSerializer::new);
        public static final IIngredientSerializer<?> PARTIAL_TAG = SERIALIZER_CACHE.computeIfAbsent(IngredientPartialTagSerializer.INSTANCE, DelegatingCustomIngredientSerializer::new);
        
        static <T extends CraftTweakerVanillaIngredient> IIngredientSerializer<DelegatingCustomIngredient<T>> of(CraftTweakerVanillaIngredientSerializer<T> ingredient) {
            
            return GenericUtil.uncheck(SERIALIZER_CACHE.computeIfAbsent(ingredient, DelegatingCustomIngredientSerializer::new));
        }
        
    }
    
}
