package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.loot.LootModifierManager;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerWrapper;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistryData;
import com.blamejared.crafttweaker.api.util.HandleHelper;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessBucketItem;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessStaticTags;
import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import com.blamejared.crafttweaker.platform.helper.world.inventory.TAInventoryWrapper;
import com.blamejared.crafttweaker.platform.services.IPlatformHelper;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.impl.tag.extension.TagFactoryImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StaticTagHelper;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FabricPlatformHelper implements IPlatformHelper {
    
    public Supplier<List<Mod>> modList = Suppliers.memoize(() -> FabricLoader.getInstance()
            .getAllMods()
            .stream()
            .map(ModContainer::getMetadata)
            .map(metadata -> new Mod(metadata.getId(), metadata.getName(), metadata.getVersion()
                    .getFriendlyString()))
            .toList());
    
    public Function<String, Optional<Mod>> modFinder = Util.memoize(modid -> modList.get()
            .stream()
            .filter(modObject -> modObject.id().equals(modid))
            .findFirst());
    
    private static final Supplier<Reflections> REFLECTIONS = Suppliers.memoize(Reflections::new);
    
    
    @Override
    public String getPlatformName() {
        
        return "Fabric";
    }
    
    @Override
    public boolean isModLoaded(String modId) {
        
        return FabricLoader.getInstance().isModLoaded(modId);
    }
    
    @Override
    public boolean isDevelopmentEnvironment() {
        
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
    
    @Override
    public List<Mod> getMods() {
        
        return modList.get();
    }
    
    @Override
    public Optional<Mod> getMod(String modid) {
        
        return modFinder.apply(modid);
    }
    
    @Override
    public IItemStack createMCItemStack(ItemStack stack) {
        
        return new MCItemStack(stack);
    }
    
    @Override
    public IItemStack createMCItemStackMutable(ItemStack stack) {
        
        return new MCItemStackMutable(stack);
    }
    
    @Override
    public IItemStack getEmptyIItemStack() {
        
        return MCItemStack.EMPTY.get();
    }
    
    @Override
    public RecipeSerializer<ScriptRecipe> getScriptSerializer() {
        
        return ScriptSerializer.INSTANCE;
    }
    
    @Override
    public Fluid getBucketContent(BucketItem item) {
        
        return ((AccessBucketItem) item).getContent();
    }
    
    
    @Override
    public <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(Class<T> annotationCls, Consumer<Mod> consumer, Predicate<Either<T, Map<String, Object>>> annotationFilter) {
        
        Set<Class<?>> typesAnnotatedWith = REFLECTIONS.get().getTypesAnnotatedWith(annotationCls);
        typesAnnotatedWith.stream().map(this::getModsForClass).flatMap(Collection::stream).forEach(consumer);
        return typesAnnotatedWith.stream();
    }
    
    private List<Mod> getModsForClass(Class<?> clazz) {
        
        File classFile = new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        List<Mod> mods = new ArrayList<>();
        // This doesn't work for the current mod in dev.
        // The origin paths just include build/resources/main, not build/classes/main, but otherwise works great
        FabricLoader.getInstance().getAllMods().forEach(modContainer -> {
            for(Path rootPath : modContainer.getOrigin().getPaths()) {
                if(rootPath.toFile().equals(classFile)) {
                    mods.add(new Mod(modContainer.getMetadata().getId(), modContainer.getMetadata()
                            .getName(), modContainer.getMetadata().getVersion().getFriendlyString()));
                }
            }
        });
        return mods;
    }
    
    @Override
    public Method findMethod(@NotNull Class<?> clazz, @NotNull String methodName, final Class<?> returnType, @NotNull Class<?>... parameterTypes) {
        
        // This is untested, so anyone running into issues using findMethod, yes the issue is probably here.
        
        final String mappedName = FabricLoader.getInstance()
                .getMappingResolver()
                .mapMethodName(FabricLoader.getInstance()
                        .getMappingResolver()
                        .getCurrentRuntimeNamespace(), clazz.getName(), methodName, "(%s)%s".formatted(Arrays.stream(parameterTypes)
                        .map(Class::descriptorString)
                        .collect(Collectors.joining()), returnType.descriptorString()));
        try {
            Method method = clazz.getDeclaredMethod(mappedName);
            method.setAccessible(true);
            return method;
        } catch(NoSuchMethodException e) {
            throw new HandleHelper.UnableToLinkHandleException("Method %s was not found inside class %s".formatted(StringUtils.quoteAndEscape(methodName), clazz.getName()), e);
        }
    }
    
    @Override
    public <T> Field findField(@NotNull Class<? super T> clazz, @NotNull String fieldName, @NotNull final String fieldDescription) {
        
        final String mappedName = FabricLoader.getInstance()
                .getMappingResolver()
                .mapFieldName(FabricLoader.getInstance()
                        .getMappingResolver()
                        .getCurrentRuntimeNamespace(), clazz.getName(), fieldName, fieldDescription);
        
        try {
            Field field = clazz.getDeclaredField(mappedName);
            field.setAccessible(true);
            return field;
        } catch(NoSuchFieldException e) {
            throw new HandleHelper.UnableToLinkHandleException("Field %s was not found inside class %s".formatted(StringUtils.quoteAndEscape(fieldName), clazz.getName()), e);
        }
    }
    
    public Map<ResourceLocation, TagCollection<?>> getCustomTags() {
        
        return TagFactoryImpl.TAG_LISTS.values()
                .stream()
                .collect(Collectors.toMap(helper -> new ResourceLocation(removeTagsDirectory(helper.getDirectory())), StaticTagHelper::getAllTags));
    }
    
    private String removeTagsDirectory(String directory) {
        
        if(directory.startsWith("tags/")) {
            return directory.substring("tags/".length());
        }
        CraftTweakerAPI.LOGGER.warn("Custom tag directory does not start with 'tags/'! Please report this to the CraftTweaker issue tracker!");
        return directory;
    }
    
    @Override
    public Collection<StaticTagHelper<?>> getStaticTagHelpers() {
        
        return AccessStaticTags.getHELPERS();
    }
    
    @Override
    public IInventoryWrapper getPlayerInventory(Player player) {
        
        return new TAInventoryWrapper(PlayerInventoryStorage.of(player));
    }
    
    @Override
    public boolean doCraftingTableRecipesConflict(IRecipeManager manager, Recipe<?> first, Recipe<?> second) {
        
        return CraftingTableRecipeConflictChecker.checkConflicts(manager, first, second);
    }
    
    @Override
    public void registerCustomTags() {
        
        // Fabric's tag system doesn't let us get the type...
        CrTTagRegistryData.INSTANCE.register(new TagManagerWrapper<>(Biome.class, Registry.BIOME_REGISTRY.location(), "biomes"));
    }
    
    @Override
    public Map<ResourceLocation, ILootModifier> getLootModifiersMap() {
        
        return LootModifierManager.INSTANCE.modifiers();
    }
    
}
