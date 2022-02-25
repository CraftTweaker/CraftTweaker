package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StaticTagHelper;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IPlatformHelper {
    
    /**
     * Fabric doesn't have a MC formatting sanitizer.
     */
    default String getLogFormat() {
        
        return "[%d{HH:mm:ss.SSS}][%level]: %msg%n%throwable";
    }
    
    String getPlatformName();
    
    boolean isModLoaded(String modId);
    
    boolean isDevelopmentEnvironment();
    
    List<Mod> getMods();
    
    Optional<Mod> getMod(String modid);
    
    IItemStack createMCItemStack(ItemStack stack);
    
    IItemStack createMCItemStackMutable(ItemStack stack);
    
    IItemStack getEmptyIItemStack();
    
    RecipeSerializer<ScriptRecipe> getScriptSerializer();
    
    Fluid getBucketContent(BucketItem item);
    
    Path getGameDirectory();
    
    default Path getPathFromGameDirectory(final Path path) {
        
        return this.getGameDirectory().resolve(path);
    }
    
    default Path getPathFromGameDirectory(final String path) {
        
        return this.getPathFromGameDirectory(Path.of(path));
    }
    
    /**
     * Finds classes with the given annotation
     *
     * @param annotationCls The annotation class to look for.
     *
     * @return A stream of classes with the annotation
     */
    default <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(Class<T> annotationCls) {
        
        return findClassesWithAnnotation(annotationCls, mod -> {}, tMapEither -> true);
    }
    
    /**
     * Finds classes with the given annotation
     *
     * @param annotationCls The annotation class to look for.
     * @param consumer      Consumer to collect the given mod that added the class if available.
     *
     * @return A stream of classes with the annotation
     */
    default <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(Class<T> annotationCls, Consumer<Mod> consumer) {
        
        return findClassesWithAnnotation(annotationCls, consumer, tMapEither -> true);
    }
    
    
    /**
     * Finds classes with the given annotation and applies a filter.
     *
     * @param annotationCls    The annotation class to look for.
     * @param consumer         Consumer to collect the given mod that added the class if available.
     * @param annotationFilter A filter to apply to the search.
     *
     * @return A stream of classes with the annotation
     */
    <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(Class<T> annotationCls, Consumer<Mod> consumer, Predicate<Either<T, Map<String, Object>>> annotationFilter);
    
    Method findMethod(@Nonnull final Class<?> clazz, @Nonnull final String methodName, @Nonnull final Class<?> returnType, @Nonnull final Class<?>... parameterTypes);
    
    <T> Field findField(@Nonnull final Class<? super T> clazz, @Nonnull final String fieldName, @Nonnull final String fieldDescription);
    
    // Don't ask, this makes the compiler shut up
    default <T> Class<? super T> castToSuperExplicitly(final Class<T> t) {
        
        return t;
    }
    
    Map<ResourceLocation, TagCollection<?>> getCustomTags();
    
    Collection<StaticTagHelper<?>> getStaticTagHelpers();
    
    void registerCustomTags();
    
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
    
    
    boolean doCraftingTableRecipesConflict(final IRecipeManager manager, final Recipe<?> first, final Recipe<?> second);
    
}
