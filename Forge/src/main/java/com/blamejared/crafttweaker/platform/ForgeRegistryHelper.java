package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionAnyDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.ConditionDamagedSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientListSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientPartialTagSerializer;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformCustomSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformDamageSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformReplaceSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.TransformerReuseSerializer;
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
import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
import com.blamejared.crafttweaker.platform.services.IRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
                event.register(Registry.RECIPE_SERIALIZER_REGISTRY, helper -> {
                    helper.register(CraftTweakerConstants.rl("shapeless"), CTShapelessRecipeSerializer.INSTANCE);
                    helper.register(CraftTweakerConstants.rl("shaped"), CTShapedRecipeSerializer.INSTANCE);
                    helper.register(CraftTweakerConstants.rl("script"), ScriptSerializer.INSTANCE);
                });
            } else if(Registry.RECIPE_TYPE_REGISTRY.equals(event.getRegistryKey())) {
                event.register(Registry.RECIPE_TYPE_REGISTRY, helper -> {
                    helper.register(ScriptRecipeType.INSTANCE.id(), ScriptRecipeType.INSTANCE);
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
    
}
