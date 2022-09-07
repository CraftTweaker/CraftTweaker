package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.func.RecipeFunctionArray;
import com.blamejared.crafttweaker.api.recipe.func.RecipeFunctionMatrix;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapedRecipeBaseSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapelessRecipeBaseSerializer;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipeBase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface IRegistryHelper {
    
    default Set<ResourceKey<?>> serverOnlyRegistries() {
        
        return StreamSupport.stream(RegistryAccess.knownRegistries().spliterator(), false)
                .filter(registryData -> !registryData.sendToClient())
                .map(RegistryAccess.RegistryData::key)
                .collect(Collectors.toSet());
    }
    
    default void registerSerializer(MappedRegistry<IIngredientTransformerSerializer<?>> registry, IIngredientTransformerSerializer<?> serializer) {
        
        registry.register(ResourceKey.create(registry.key(), serializer.getType()), serializer, Lifecycle.stable());
    }
    
    default void registerSerializer(MappedRegistry<IIngredientConditionSerializer<?>> registry, IIngredientConditionSerializer<?> serializer) {
        
        registry.register(ResourceKey.create(registry.key(), serializer.getType()), serializer, Lifecycle.stable());
    }
    
    default <T> MappedRegistry<T> registerVanillaRegistry(ResourceLocation location) {
        
        WritableRegistry registry = (WritableRegistry) Registry.REGISTRY;
        ResourceKey<Registry<T>> regKey = ResourceKey.createRegistryKey(location);
        Lifecycle stable = Lifecycle.stable();
        MappedRegistry<T> mappedReg = new MappedRegistry<>(regKey, stable, null);
        registry.register(regKey, mappedReg, stable);
        return mappedReg;
    }
    
    void init();
    
    ICTShapedRecipeBaseSerializer getCTShapedRecipeSerializer();
    
    ICTShapelessRecipeBaseSerializer getCTShapelessRecipeSerializer();
    
    default CTShapedRecipeBase createCTShapedRecipe(String name, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunctionMatrix function) {
        
        return getCTShapedRecipeSerializer().makeRecipe(CraftTweakerConstants.rl(name), output, ingredients, mirrorAxis, function);
    }
    
    default CTShapelessRecipeBase createCTShapelessRecipe(String name, IItemStack output, IIngredient[] ingredients, @javax.annotation.Nullable RecipeFunctionArray function) {
        
        return getCTShapelessRecipeSerializer().makeRecipe(CraftTweakerConstants.rl(name), output, ingredients, function);
    }
    
    Ingredient getIngredientAny();
    
    Ingredient getIngredientList(List<Ingredient> children);
    
    <T extends IIngredient> Ingredient getIngredientConditioned(IIngredientConditioned<T> conditioned);
    
    <T extends IIngredient> Ingredient getIngredientTransformed(IIngredientTransformed<T> transformed);
    
    Ingredient getIngredientPartialTag(ItemStack stack);
    
    /**
     * Maybe returns the registry key of the given object if we know about its type.
     */
    default Optional<ResourceLocation> maybeGetRegistryKey(Object object) {
        
        if(object instanceof Item obj) {
            return nonDefaultKey(Registry.ITEM, obj);
        } else if(object instanceof Potion obj) {
            return nonDefaultKey(Registry.POTION, obj);
        } else if(object instanceof EntityType<?> obj) {
            return nonDefaultKey(Registry.ENTITY_TYPE, obj);
        } else if(object instanceof RecipeType<?> obj) {
            return nonDefaultKey(Registry.RECIPE_TYPE, obj);
        } else if(object instanceof RecipeSerializer<?> obj) {
            return nonDefaultKey(Registry.RECIPE_SERIALIZER, obj);
        } else if(object instanceof Attribute obj) {
            return nonDefaultKey(Registry.ATTRIBUTE, obj);
        } else if(object instanceof Fluid obj) {
            return nonDefaultKey(Registry.FLUID, obj);
        } else if(object instanceof Enchantment obj) {
            return nonDefaultKey(Registry.ENCHANTMENT, obj);
        } else if(object instanceof Block obj) {
            return nonDefaultKey(Registry.BLOCK, obj);
        } else if(object instanceof MobEffect obj) {
            return nonDefaultKey(Registry.MOB_EFFECT, obj);
        } else if(object instanceof VillagerProfession obj) {
            return nonDefaultKey(Registry.VILLAGER_PROFESSION, obj);
        } else if(object instanceof Biome obj) {
            return nonDefaultKey(CraftTweakerAPI.getAccessibleElementsProvider()
                    .registryAccess()
                    .registryOrThrow(Registry.BIOME_REGISTRY), obj);
        } else if(object instanceof SoundEvent obj) {
            return nonDefaultKey(Registry.SOUND_EVENT, obj);
        }
        
        return Optional.empty();
    }
    
    private <T> Optional<ResourceLocation> nonDefaultKey(Registry<T> registry, T obj) {
        
        ResourceLocation key = registry.getKey(obj);
        if(registry instanceof DefaultedRegistry<T> def) {
            if(def.getDefaultKey().equals(key) && !def.get(key).equals(obj)) {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(key);
    }
    
    default Registry<Biome> biomes() {
        
        return CraftTweakerAPI.getAccessibleElementsProvider()
                .registryAccess()
                .registryOrThrow(Registry.BIOME_REGISTRY);
    }
    
    default <T> Holder<T> makeHolder(ResourceKey<?> resourceKey, Either<T, ResourceLocation> objectOrKey) {
        
        return objectOrKey.map(t -> makeHolder(resourceKey, t), key -> makeHolder(resourceKey, key));
    }
    
    default <T> Holder<T> makeHolder(ResourceKey<?> resourceKey, T object) {
        
        Registry<T> registry = CraftTweakerAPI.getAccessibleElementsProvider()
                .registryAccess()
                .registryOrThrow(GenericUtil.uncheck(resourceKey));
        return registry.getResourceKey(object)
                .flatMap(registry::getHolder)
                .orElseThrow(() -> new RuntimeException("Unable to make holder for registry: " + registry + " and object: " + object));
    }
    
    default <T> Holder<T> makeHolder(ResourceKey<?> resourceKey, ResourceLocation key) {
        
        Registry<T> registry = CraftTweakerAPI.getAccessibleElementsProvider()
                .registryAccess()
                .registryOrThrow(GenericUtil.uncheck(resourceKey));
        
        if(!registry.containsKey(key)) {
            throw new IllegalArgumentException("Registry does not contain key: '" + key + "'");
        }
        return registry.getHolder(ResourceKey.create(registry.key(), key))
                .orElseThrow(() -> new RuntimeException("Unable to make holder for registry: " + registry + " and id: " + key));
    }
    
}
