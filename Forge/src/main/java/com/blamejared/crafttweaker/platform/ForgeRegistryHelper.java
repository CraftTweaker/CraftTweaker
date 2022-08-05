package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionAnyDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.*;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformerReuseSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.*;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapelessRecipeSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapedRecipeBaseSerializer;
import com.blamejared.crafttweaker.api.recipe.serializer.ICTShapelessRecipeBaseSerializer;
import com.blamejared.crafttweaker.impl.loot.LootTableIdRegexCondition;
import com.blamejared.crafttweaker.platform.registry.ForgeRegistryWrapper;
import com.blamejared.crafttweaker.platform.registry.RegistryWrapper;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import net.minecraft.core.Registry;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.util.List;
import java.util.function.Consumer;

public class ForgeRegistryHelper implements IRegistryHelper {
    
    
    @Override
    public void init() {
        
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener((Consumer<NewRegistryEvent>) newRegistry -> {
                    CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER = registerVanillaRegistry(CraftTweakerConstants.rl("transformer_serializer"));
                    CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER = registerVanillaRegistry(CraftTweakerConstants.rl("condition_serializer"));
                    
                    registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformReplaceSerializer.INSTANCE);
                    registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformDamageSerializer.INSTANCE);
                    registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformCustomSerializer.INSTANCE);
                    registerSerializer(CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER, TransformerReuseSerializer.INSTANCE);
                    
                    registerSerializer(CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER, ConditionDamagedSerializer.INSTANCE);
                    registerSerializer(CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER, ConditionAnyDamagedSerializer.INSTANCE);
                    registerSerializer(CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER, ConditionCustomSerializer.INSTANCE);
                });
        
        // Thank you forge, very cool.
        FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer<RegisterEvent>) event -> {
            if(Registry.BLOCK_REGISTRY.equals(event.getRegistryKey())) {
                Registry.register(
                        Registry.LOOT_CONDITION_TYPE,
                        new ResourceLocation(CraftTweakerConstants.MOD_ID, "loot_table_id_regex"),
                        LootTableIdRegexCondition.LOOT_TABLE_ID_REGEX
                );
            } else if(Registry.RECIPE_SERIALIZER_REGISTRY.equals(event.getRegistryKey())) {
                CraftTweakerRegistries.RECIPE_TYPE_SCRIPTS = RecipeType.register(CraftTweakerConstants.rl("scripts")
                        .toString());
                event.register(Registry.RECIPE_SERIALIZER_REGISTRY, helper -> {
                    helper.register(CraftTweakerConstants.rl("shapeless"), CTShapelessRecipeSerializer.INSTANCE);
                    helper.register(CraftTweakerConstants.rl("shaped"), CTShapelessRecipeSerializer.INSTANCE);
                    helper.register(CraftTweakerConstants.rl("script"), CTShapelessRecipeSerializer.INSTANCE);
                });
            }
        });
        
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
    
    private <V> RegistryWrapper<V> wrap(IForgeRegistry<V> registry) {
        
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
        
        return wrap(ForgeRegistries.VILLAGER_PROFESSIONS);
    }
    
    @Override
    public RegistryWrapper<SoundEvent> soundEvents() {
        
        return wrap(ForgeRegistries.SOUND_EVENTS);
    }
    
    @Override
    public RegistryWrapper<EntityType<?>> entityTypes() {
        
        return wrap(ForgeRegistries.ENTITY_TYPES);
    }
    
}
