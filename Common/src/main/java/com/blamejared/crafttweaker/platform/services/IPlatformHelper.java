package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessIngredient;
import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;

import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IPlatformHelper {
    
    /**
     * Fabric doesn't have a MC formatting sanitizer.
     */
    default String getLogFormat() {
        
        return "[%d{HH:mm:ss.SSS}][%level][%markerSimpleName]: %msg%n%throwable";
    }
    
    String getPlatformName();
    
    boolean isModLoaded(String modId);
    
    boolean isDevelopmentEnvironment();
    
    boolean isDataGen();
    
    List<Mod> getMods();
    
    Optional<Mod> getMod(String modid);
    
    IItemStack createItemStack(ItemStack stack);
    
    IItemStack createItemStackMutable(ItemStack stack);
    
    IItemStack getEmptyItemStack();
    
    
    Fluid getBucketContent(BucketItem item);
    
    Path getGameDirectory();
    
    /**
     * Finds classes with the given annotation and applies a filter.
     *
     * @param annotationCls    The annotation class to look for.
     * @param consumer         Consumer to collect the given mod that added the class if available.
     * @param annotationFilter A filter to apply to the search.
     *
     * @return A stream of classes with the annotation
     */
    <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
            final Class<T> annotationClass,
            final Consumer<Mod> classProviderConsumer,
            final Predicate<Either<T, Map<String, Object>>> annotationFilter
    );
    
    String findMappedMethodName(final Class<?> clazz, final String methodName, final Class<?> returnType, final Class<?>... parameterTypes);
    
    String findMappedFieldName(final Class<?> clazz, final String fieldName, final Class<?> fieldType);
    
    default void registerCustomTradeConverters(Map<Class<? extends VillagerTrades.ItemListing>, Function<VillagerTrades.ItemListing, CTTradeObject>> classFunctionMap) {
        // no-op
    }
    
    Map<ResourceLocation, ILootModifier> getLootModifiersMap();
    
    IInventoryWrapper getPlayerInventory(Player player);
    
    default boolean canItemStacksStack(ItemStack first, ItemStack second) {
        
        if(first.isEmpty() || !first.sameItem(second) || first.hasTag() != second.hasTag()) {
            return false;
        }
        
        return (!first.hasTag() || first.getTag().equals(second.getTag()));
    }
    
    boolean doCraftingTableRecipesConflict(final IRecipeManager<?> manager, final Recipe<?> first, final Recipe<?> second);
    
    Set<MutableComponent> getFluidsForDump(ItemStack stack, Player player, InteractionHand hand);
    
    CompoundTag getCustomData(Entity entity);
    
    CompoundTag getPersistentData(ServerPlayer player);
    
    default void addFoodPropertiesEffect(FoodProperties internal, MobEffectInstance effect, float probability) {
        
        internal.getEffects().add(Pair.of(effect, probability));
    }
    
    default void removeFoodPropertiesEffect(FoodProperties internal, MobEffectInstance effect) {
        
        internal.getEffects().removeIf(pair -> pair.getFirst().equals(effect));
    }
    
    default void removeFoodPropertiesEffect(FoodProperties internal, MobEffect effect) {
        
        internal.getEffects().removeIf(pair -> pair.getFirst().getEffect() == effect);
    }
    
    default void invalidateIngredients(List<Ingredient> ingredients) {
        
        ingredients.forEach(ingredient -> {
            ((AccessIngredient) (Object) ingredient).crafttweaker$setItemStacks(null);
        });
        ingredients.clear();
    }
    
}
