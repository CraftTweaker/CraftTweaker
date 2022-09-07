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
import com.blamejared.crafttweaker.api.recipe.function.RecipeFunctionArray;
import com.blamejared.crafttweaker.api.recipe.function.RecipeFunctionMatrix;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapedRecipeBaseSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapelessRecipeBaseSerializer;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipeBase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.platform.registry.RegistryWrapper;
import com.blamejared.crafttweaker.platform.registry.VanillaRegistryWrapper;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
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
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof Potion obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof EntityType<?> obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof RecipeType<?> obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof RecipeSerializer<?> obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof Attribute obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof Fluid obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof Enchantment obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof Block obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof MobEffect obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof VillagerProfession obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof Biome obj) {
            return Optional.of(getRegistryKey(obj));
        } else if(object instanceof SoundEvent obj) {
            return Optional.of(getRegistryKey(obj));
        }
        
        
        return Optional.empty();
    }
    
    default ResourceLocation getRegistryKey(Item item) {
        
        return items().getKey(item);
    }
    
    default ResourceLocation getRegistryKey(Potion potion) {
        
        return potions().getKey(potion);
    }
    
    default ResourceLocation getRegistryKey(EntityType<?> type) {
        
        return entityTypes().getKey(type);
    }
    
    default ResourceLocation getRegistryKey(RecipeType<?> type) {
        
        return recipeTypes().getKey(type);
    }
    
    default ResourceLocation getRegistryKey(RecipeSerializer<?> serializer) {
        
        return recipeSerializers().getKey(serializer);
    }
    
    default ResourceLocation getRegistryKey(Attribute attribute) {
        
        return attributes().getKey(attribute);
    }
    
    default ResourceLocation getRegistryKey(Fluid fluid) {
        
        return fluids().getKey(fluid);
    }
    
    default ResourceLocation getRegistryKey(Enchantment enchantment) {
        
        return enchantments().getKey(enchantment);
    }
    
    default ResourceLocation getRegistryKey(Block block) {
        
        return blocks().getKey(block);
    }
    
    default ResourceLocation getRegistryKey(MobEffect mobEffect) {
        
        return mobEffects().getKey(mobEffect);
    }
    
    default ResourceLocation getRegistryKey(VillagerProfession profession) {
        
        return villagerProfessions().getKey(profession);
    }
    
    default ResourceLocation getRegistryKey(Biome biome) {
        
        return biomes().getKey(biome);
    }
    
    default ResourceLocation getRegistryKey(SoundEvent biome) {
        
        return soundEvents().getKey(biome);
    }
    
    private <T> RegistryWrapper<T> wrap(Registry<T> registry) {
        
        return new VanillaRegistryWrapper<>(registry);
    }
    
    default RegistryWrapper<Item> items() {
        
        return wrap(Registry.ITEM);
    }
    
    default RegistryWrapper<Potion> potions() {
        
        return wrap(Registry.POTION);
    }
    
    default RegistryWrapper<RecipeType<?>> recipeTypes() {
        
        return wrap(Registry.RECIPE_TYPE);
    }
    
    default RegistryWrapper<RecipeSerializer<?>> recipeSerializers() {
        
        return wrap(Registry.RECIPE_SERIALIZER);
    }
    
    default RegistryWrapper<Attribute> attributes() {
        
        return wrap(Registry.ATTRIBUTE);
    }
    
    default RegistryWrapper<Fluid> fluids() {
        
        return wrap(Registry.FLUID);
    }
    
    default RegistryWrapper<Enchantment> enchantments() {
        
        return wrap(Registry.ENCHANTMENT);
    }
    
    default RegistryWrapper<Block> blocks() {
        
        return wrap(Registry.BLOCK);
    }
    
    default RegistryWrapper<MobEffect> mobEffects() {
        
        return wrap(Registry.MOB_EFFECT);
    }
    
    default RegistryWrapper<VillagerProfession> villagerProfessions() {
        
        return wrap(Registry.VILLAGER_PROFESSION);
    }
    
    default RegistryWrapper<SoundEvent> soundEvents() {
        
        return wrap(Registry.SOUND_EVENT);
    }
    
    default RegistryWrapper<Biome> biomes() {
        
        return wrap(CraftTweakerAPI.getAccessibleElementsProvider()
                .registryAccess()
                .registryOrThrow(Registry.BIOME_REGISTRY));
    }
    
    default RegistryWrapper<EntityType<?>> entityTypes() {
        
        return wrap(Registry.ENTITY_TYPE);
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
