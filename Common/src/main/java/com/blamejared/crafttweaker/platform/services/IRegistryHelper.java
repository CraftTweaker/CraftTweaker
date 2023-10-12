package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.*;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.mixin.common.access.registry.AccessRegistrySynchronization;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.*;
import java.util.stream.Collectors;

public interface IRegistryHelper {
    
    default Set<ResourceKey<?>> serverOnlyRegistries() {
        
        return AccessRegistrySynchronization.crafttweaker$callOwnedNetworkableRegistries(CraftTweakerAPI.getAccessibleElementsProvider()
                .registryAccess()).map(RegistryAccess.RegistryEntry::key).collect(Collectors.toSet());
    }
    
    default void registerSerializer(MappedRegistry<IIngredientTransformerSerializer<?>> registry, IIngredientTransformerSerializer<?> serializer) {
        
        registry.register(ResourceKey.create(registry.key(), serializer.getType()), serializer, Lifecycle.stable());
    }
    
    default void registerSerializer(MappedRegistry<IIngredientConditionSerializer<?>> registry, IIngredientConditionSerializer<?> serializer) {
        
        registry.register(ResourceKey.create(registry.key(), serializer.getType()), serializer, Lifecycle.stable());
    }
    
    void init();
    
    /**
     * Maybe returns the registry key of the given object if we know about its type.
     */
    default Optional<ResourceLocation> maybeGetRegistryKey(Object object) {
        
        if(object instanceof Item obj) {
            return nonDefaultKey(BuiltInRegistries.ITEM, obj);
        } else if(object instanceof Potion obj) {
            return nonDefaultKey(BuiltInRegistries.POTION, obj);
        } else if(object instanceof EntityType<?> obj) {
            return nonDefaultKey(BuiltInRegistries.ENTITY_TYPE, obj);
        } else if(object instanceof RecipeType<?> obj) {
            return nonDefaultKey(BuiltInRegistries.RECIPE_TYPE, obj);
        } else if(object instanceof RecipeSerializer<?> obj) {
            return nonDefaultKey(BuiltInRegistries.RECIPE_SERIALIZER, obj);
        } else if(object instanceof Attribute obj) {
            return nonDefaultKey(BuiltInRegistries.ATTRIBUTE, obj);
        } else if(object instanceof Fluid obj) {
            return nonDefaultKey(BuiltInRegistries.FLUID, obj);
        } else if(object instanceof Enchantment obj) {
            return nonDefaultKey(BuiltInRegistries.ENCHANTMENT, obj);
        } else if(object instanceof Block obj) {
            return nonDefaultKey(BuiltInRegistries.BLOCK, obj);
        } else if(object instanceof MobEffect obj) {
            return nonDefaultKey(BuiltInRegistries.MOB_EFFECT, obj);
        } else if(object instanceof VillagerProfession obj) {
            return nonDefaultKey(BuiltInRegistries.VILLAGER_PROFESSION, obj);
        } else if(object instanceof Biome obj) {
            return nonDefaultKey(CraftTweakerAPI.getAccessibleElementsProvider()
                    .registryAccess()
                    .registryOrThrow(Registries.BIOME), obj);
        } else if(object instanceof SoundEvent obj) {
            return nonDefaultKey(BuiltInRegistries.SOUND_EVENT, obj);
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
                .registryOrThrow(Registries.BIOME);
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
