package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientAny;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientList;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientPartialTag;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientTransformed;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapedRecipeBaseSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapelessRecipeBaseSerializer;
import com.blamejared.crafttweaker.impl.loot.LootTableIdRegexCondition;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
import com.blamejared.crafttweaker.platform.registry.ForgeRegistryWrapper;
import com.blamejared.crafttweaker.platform.registry.RegistryWrapper;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;
import java.util.Optional;

public class ForgeRegistryHelper implements IRegistryHelper {
    
    @Override
    public void initRegistries() {
        
        ForgeRegistries.RECIPE_SERIALIZERS.register(CTShapelessRecipeSerializer.INSTANCE);
        ForgeRegistries.RECIPE_SERIALIZERS.register(CTShapedRecipeSerializer.INSTANCE);
        ForgeRegistries.RECIPE_SERIALIZERS.register(ScriptSerializer.INSTANCE);
        
        Registry.register(
                Registry.LOOT_CONDITION_TYPE,
                new ResourceLocation(CraftTweakerConstants.MOD_ID, "loot_table_id_regex"),
                LootTableIdRegexCondition.LOOT_TABLE_ID_REGEX
        );
        
        CraftingHelper.register(CraftTweakerConstants.rl("any"), IngredientAnySerializer.INSTANCE);
        CraftingHelper.register(CraftTweakerConstants.rl("list"), IngredientListSerializer.INSTANCE);
        CraftingHelper.register(CraftTweakerConstants.rl("transformed"), IngredientTransformedSerializer.INSTANCE);
        CraftingHelper.register(CraftTweakerConstants.rl("conditioned"), IngredientConditionedSerializer.INSTANCE);
        CraftingHelper.register(CraftTweakerConstants.rl("partial_tag"), IngredientPartialTagSerializer.INSTANCE);
    }
    
    @Override
    public ICTShapedRecipeBaseSerializer getCTShapedRecipeSerializer() {
        
        return CTShapedRecipeSerializer.INSTANCE;
    }
    
    @Override
    public ICTShapelessRecipeBaseSerializer getCTShapelessRecipeSerializer() {
        
        return CTShapelessRecipeSerializer.INSTANCE;
    }
    
    @Override
    public Ingredient getIngredientAny() {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public Ingredient getIngredientList(List<Ingredient> children) {
        
        return new IngredientList(children);
    }
    
    @Override
    public <T extends IIngredient> Ingredient getIngredientConditioned(IIngredientConditioned<T> conditioned) {
        
        return new IngredientConditioned<>(conditioned);
    }
    
    @Override
    public <T extends IIngredient> Ingredient getIngredientTransformed(IIngredientTransformed<T> transformed) {
        
        return new IngredientTransformed<>(transformed);
    }
    
    @Override
    public Ingredient getIngredientPartialTag(ItemStack stack) {
        
        return new IngredientPartialTag(stack);
    }
    
    @Override
    public Optional<ResourceLocation> maybeGetRegistryKey(Object object) {
        
        if(object instanceof IForgeRegistryEntry<?>) {
            return Optional.ofNullable(((IForgeRegistryEntry<?>) object).getRegistryName());
        }
        
        return Optional.empty();
    }
    
    private <V extends IForgeRegistryEntry<V>> RegistryWrapper<V> wrap(IForgeRegistry<V> registry) {
        
        return new ForgeRegistryWrapper<>(registry);
    }
    
    @Override
    public RegistryWrapper<Item> items() {
        
        return wrap(ForgeRegistries.ITEMS);
    }
    
    @Override
    public RegistryWrapper<Potion> potions() {
        
        return wrap(ForgeRegistries.POTIONS);
    }
    
    @Override
    public RegistryWrapper<RecipeSerializer<?>> recipeSerializers() {
        
        return wrap(ForgeRegistries.RECIPE_SERIALIZERS);
    }
    
    @Override
    public RegistryWrapper<Attribute> attributes() {
        
        return wrap(ForgeRegistries.ATTRIBUTES);
    }
    
    @Override
    public RegistryWrapper<Fluid> fluids() {
        
        return wrap(ForgeRegistries.FLUIDS);
    }
    
    @Override
    public RegistryWrapper<Enchantment> enchantments() {
        
        return wrap(ForgeRegistries.ENCHANTMENTS);
    }
    
    @Override
    public RegistryWrapper<Block> blocks() {
        
        return wrap(ForgeRegistries.BLOCKS);
    }
    
    @Override
    public RegistryWrapper<MobEffect> mobEffects() {
        
        return wrap(ForgeRegistries.MOB_EFFECTS);
    }
    
    @Override
    public RegistryWrapper<VillagerProfession> villagerProfessions() {
        
        return wrap(ForgeRegistries.PROFESSIONS);
    }
    
    @Override
    public RegistryWrapper<Biome> biomes() {
        
        return wrap(ForgeRegistries.BIOMES);
    }
    
    @Override
    public RegistryWrapper<EntityType<?>> entityTypes() {
        
        return wrap(ForgeRegistries.ENTITIES);
    }
    
}
