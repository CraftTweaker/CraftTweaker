package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerWrapper;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistryData;
import com.blamejared.crafttweaker.api.util.HandleHelper;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.impl.loot.CraftTweakerPrivilegedLootModifierMap;
import com.blamejared.crafttweaker.impl.loot.ForgeLootModifierMapAdapter;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.blamejared.crafttweaker.impl.script.ScriptSerializer;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessStaticTags;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessBasicTrade;
import com.blamejared.crafttweaker.platform.helper.inventory.IItemHandlerWrapper;
import com.blamejared.crafttweaker.platform.services.IPlatformHelper;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.StaticTagHelper;
import net.minecraft.tags.StaticTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.common.ForgeInternalHandler;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifierManager;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.forgespi.language.ModFileScanData;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import org.antlr.v4.runtime.misc.NotNull;
import org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ForgePlatformHelper implements IPlatformHelper {
    
    private static final class Handles {
        
        private static final MethodHandle LMM_GETTER = HandleHelper.linkMethod(ForgeInternalHandler.class, "getLootModifierManager", LootModifierManager.class);
        private static final VarHandle LMM_MAP = HandleHelper.linkField(LootModifierManager.class, "registeredLootModifiers", "()Ljava/util/Map;");
        
    }
    
    public Supplier<List<Mod>> modList = Suppliers.memoize(() -> ModList.get()
            .getMods()
            .stream()
            .map(iModInfo -> new Mod(iModInfo.getModId(), iModInfo.getDisplayName(), iModInfo.getVersion()
                    .toString()))
            .toList());
    
    public Function<String, Optional<Mod>> modFinder = Util.memoize(modid -> modList.get()
            .stream()
            .filter(modObject -> modObject.id().equals(modid))
            .findFirst());
    
    
    @Override
    public String getLogFormat() {
        
        return "[%d{HH:mm:ss.SSS}][%level]: %minecraftFormatting{%msg}{strip}%n%throwable";
    }
    
    @Override
    public String getPlatformName() {
        
        return "Forge";
    }
    
    @Override
    public boolean isModLoaded(String modId) {
        
        return ModList.get().isLoaded(modId);
    }
    
    @Override
    public boolean isDevelopmentEnvironment() {
        
        return FMLLoader.isProduction();
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
        
        return item.getFluid();
    }
    
    
    @Override
    public <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(Class<T> annotationCls, Consumer<Mod> consumer, Predicate<Either<T, Map<String, Object>>> annotationFilter) {
        
        final Type annotationType = Type.getType(annotationCls);
        return ModList.get()
                .getAllScanData()
                .stream()
                .flatMap(scanData -> scanData.getAnnotations()
                        .stream()
                        .filter(a -> annotationType.equals(a.annotationType()))
                        .filter(annotationData -> annotationFilter.test(Either.right(annotationData.annotationData())))
                        .peek(annotationData -> scanData.getIModInfoData()
                                .stream()
                                .flatMap(iModFileInfo -> iModFileInfo.getMods()
                                        .stream())
                                .map(iModInfo -> new Mod(iModInfo.getModId(), iModInfo.getDisplayName(), iModInfo.getVersion()
                                        .toString())).forEach(consumer))
                        .map(ModFileScanData.AnnotationData::clazz))
                .map(CraftTweakerRegistry::getClassFromType)
                .filter(Objects::nonNull);
    }
    
    @Override
    public Method findMethod(Class<?> type, String methodName, final Class<?> returnType, Class<?>... arguments) {
        
        try {
            return ObfuscationReflectionHelper.findMethod(type, methodName, arguments);
        } catch(final ObfuscationReflectionHelper.UnableToFindMethodException e) {
            throw new HandleHelper.UnableToLinkHandleException("Method %s was not found inside class %s".formatted(StringUtils.quoteAndEscape(methodName), type.getName()), e);
        }
    }
    
    @Override
    public <T> Field findField(@NotNull final Class<? super T> clazz, @NotNull final String fieldName, @NotNull final String fieldDescription) {
        
        try {
            return ObfuscationReflectionHelper.findField(castToSuperExplicitly(clazz), fieldName);
        } catch(final ObfuscationReflectionHelper.UnableToFindFieldException e) {
            throw new HandleHelper.UnableToLinkHandleException("Field %s was not found inside class %s".formatted(StringUtils.quoteAndEscape(fieldName), clazz.getName()), e);
        }
    }
    
    @Override
    public Map<ResourceLocation, TagCollection<?>> getCustomTags() {
        
        Map<ResourceLocation, TagCollection<?>> customTags = new HashMap<>();
        for(ResourceLocation customTagTypeName : ForgeTagHandler.getCustomTagTypeNames()) {
            StaticTagHelper<?> helper = StaticTags.get(customTagTypeName);
            if(helper != null) {
                customTags.put(customTagTypeName, SerializationTags.getInstance().getOrEmpty(helper.getKey()));
            }
        }
        
        return customTags;
    }
    
    @Override
    public Collection<StaticTagHelper<?>> getStaticTagHelpers() {
        
        return AccessStaticTags.getHELPERS();
    }
    
    public void registerCustomTags() {
        
        final RegistryManager registryManager = RegistryManager.ACTIVE;
        for(final ResourceLocation key : ForgeTagHandler.getCustomTagTypeNames()) {
            if(registryManager.getRegistry(key) == null) {
                CraftTweakerAPI.LOGGER.warn("Unsupported TagCollection without registry: " + key);
                continue;
            }
            
            final ForgeRegistry<?> registry = registryManager.getRegistry(key);
            String tagFolder = registry.getTagFolder();
            if(tagFolder == null) {
                if(key.getNamespace().equals("minecraft")) {
                    tagFolder = key.getPath();
                } else {
                    CraftTweakerAPI.LOGGER.warn("Could not find tagFolder for registry '{}'", key);
                    continue;
                }
            }
            
            if(CrTTagRegistryData.INSTANCE.hasTagManager(tagFolder)) {
                //We already have a custom ITagManager for this.
                continue;
            }
            CraftTweakerAPI.LOGGER.debug("Creating Wrapper ITagManager for type '{}' with tag folder '{}'", key, tagFolder);
            registerTagManagerFromRegistry(key, registry, tagFolder);
        }
    }
    
    @SuppressWarnings({"rawtypes"})
    public void registerTagManagerFromRegistry(ResourceLocation name, ForgeRegistry<?> registry, String tagFolder) {
        
        final Class<?> registrySuperType = registry.getRegistrySuperType();
        final Optional<String> s = CraftTweakerRegistry.tryGetZenClassNameFor(registrySuperType);
        if(s.isEmpty()) {
            CraftTweakerAPI.LOGGER.debug("Could not register tag manager for " + tagFolder);
            return;
        }
        
        CrTTagRegistryData.INSTANCE.register(new TagManagerWrapper(registrySuperType, name, tagFolder));
    }
    
    
    @Override
    public void registerCustomTradeConverters(Map<Class<? extends VillagerTrades.ItemListing>, Function<VillagerTrades.ItemListing, CTTradeObject>> classFunctionMap) {
        
        classFunctionMap.put(BasicTrade.class, iTrade -> {
            if(iTrade instanceof BasicTrade) {
                return new CTTradeObject(
                        createMCItemStackMutable(((AccessBasicTrade) iTrade).getPrice()),
                        createMCItemStackMutable(((AccessBasicTrade) iTrade).getPrice2()),
                        createMCItemStackMutable(((AccessBasicTrade) iTrade).getForSale()));
            }
            throw new IllegalArgumentException("Invalid trade passed to trade function! Given: " + iTrade.getClass());
        });
    }
    
    @Override
    public Map<ResourceLocation, ILootModifier> getLootModifiersMap() {
        
        try {
            LootModifierManager manager = HandleHelper.invoke(() -> (LootModifierManager) Handles.LMM_GETTER.invokeExact());
            @SuppressWarnings("unchecked")
            Map<ResourceLocation, IGlobalLootModifier> map = (Map<ResourceLocation, IGlobalLootModifier>) Handles.LMM_MAP.get(manager);
            
            // Someone else may make the map mutable, but I explicitly want CT stuff to go last
            if(!(map instanceof CraftTweakerPrivilegedLootModifierMap)) {
                final Map<ResourceLocation, IGlobalLootModifier> finalMap = CraftTweakerPrivilegedLootModifierMap.of(map);
                map = finalMap;
                Handles.LMM_MAP.set(manager, finalMap);
            }
            
            return ForgeLootModifierMapAdapter.adapt(map);
        } catch(final IllegalStateException e) {
            
            // LMM_GETTER.invokeExact() throws ISE if we're on the client and playing multiplayer
            return Collections.emptyMap();
        }
    }
    
    @Override
    public IItemHandlerWrapper getPlayerInventory(Player player) {
        
        //First try getting the forge one, if that fails use the default vanilla one
        return player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .map(IItemHandlerWrapper::new)
                .orElseThrow(() -> new RuntimeException("Player does not have the Item Handler capability, this is probably wrong!"));
    }
    
    @Override
    public boolean canItemStacksStack(ItemStack first, ItemStack second) {
        
        if(first.isEmpty() || !first.sameItem(second) || first.hasTag() != second.hasTag()) {
            return false;
        }
        
        return (!first.hasTag() || first.getTag().equals(second.getTag())) && first.areCapsCompatible(second);
    }
    
    @Override
    public boolean doCraftingTableRecipesConflict(IRecipeManager manager, Recipe<?> first, Recipe<?> second) {
        
        return CraftingTableRecipeConflictChecker.checkConflicts(manager, first, second);
    }
    
    
}
