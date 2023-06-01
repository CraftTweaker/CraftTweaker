package com.blamejared.crafttweaker.platform;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStack;
import com.blamejared.crafttweaker.api.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.recipe.handler.helper.CraftingTableRecipeConflictChecker;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.HandleUtil;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.impl.loot.CraftTweakerPrivilegedLootModifierMap;
import com.blamejared.crafttweaker.impl.loot.ForgeLootModifierMapAdapter;
import com.blamejared.crafttweaker.mixin.common.access.food.AccessFoodPropertiesForge;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessBasicTrade;
import com.blamejared.crafttweaker.platform.helper.inventory.IItemHandlerWrapper;
import com.blamejared.crafttweaker.platform.services.IPlatformHelper;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import cpw.mods.modlauncher.api.INameMappingService;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.common.ForgeInternalHandler;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifierManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ForgePlatformHelper implements IPlatformHelper {

    private static final class Handles {

        private static final MethodHandle LMM_GETTER = HandleUtil.linkMethod(
                ForgeInternalHandler.class,
                HandleUtil.AccessType.STATIC,
                "getLootModifierManager",
                LootModifierManager.class);

        private static final VarHandle LMM_MAP = HandleUtil.linkField(
                LootModifierManager.class,
                HandleUtil.AccessType.VIRTUAL,
                "registeredLootModifiers",
                Map.class);

    }
    
    public final Supplier<List<Mod>> modList = Suppliers.memoize(() -> ModList.get()
            .getMods()
            .stream()
            .map(iModInfo -> new Mod(iModInfo.getModId(), iModInfo.getDisplayName(), iModInfo.getVersion().toString()))
            .toList());
    
    public final Function<String, Optional<Mod>> modFinder = Util.memoize(modid -> modList.get()
            .stream()
            .filter(modObject -> modObject.id().equals(modid))
            .findFirst());

    @Override
    public String getLogFormat() {
        
        return "[%d{HH:mm:ss.SSS}][%level][%markerSimpleName]: %minecraftFormatting{%msg}{strip}%n%throwable";
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

        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isDataGen() {

        return DatagenModLoader.isRunningDataGen();
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
    public IItemStack createItemStack(ItemStack stack) {

        return new MCItemStack(stack);
    }

    @Override
    public IItemStack createItemStackMutable(ItemStack stack) {

        return new MCItemStackMutable(stack);
    }

    @Override
    public IFluidStack createFluidStack(Fluid fluid, long amount, @Nullable CompoundTag tag) {

        return new MCFluidStack(new FluidStack(fluid, Math.toIntExact(amount), tag));
    }

    @Override
    public IFluidStack createFluidStackMutable(Fluid fluid, long amount, @Nullable CompoundTag tag) {

        return new MCFluidStackMutable(new FluidStack(fluid, Math.toIntExact(amount), tag));
    }

    @Override
    public <T> IFluidStack createFluidStack(T stack) {

        if (stack instanceof FluidStack fluidStack) {
            return new MCFluidStack(fluidStack);
        }
        throw new IllegalArgumentException("Unable to create IFluidStack from '" + stack + "'!");
    }

    @Override
    public <T> IFluidStack createFluidStackMutable(T stack) {

        if (stack instanceof FluidStack fluidStack) {
            return new MCFluidStackMutable(fluidStack);
        }
        throw new IllegalArgumentException("Unable to create IFluidStack from '" + stack + "'!");
    }

    @Override
    public Fluid getBucketContent(BucketItem item) {

        return item.getFluid();
    }

    @Override
    public Path getGameDirectory() {

        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public <T extends Annotation> Stream<? extends Class<?>> findClassesWithAnnotation(
            final Class<T> annotationClass,
            final Consumer<Mod> classProviderConsumer,
            final Predicate<Either<T, Map<String, Object>>> annotationFilter) {

        final Type annotationType = Type.getType(annotationClass);
        return ModList.get()
                .getAllScanData()
                .stream()
                .flatMap(it -> this.fromScanData(annotationType, classProviderConsumer, annotationFilter, it))
                .map(ForgePlatformHelper::getClassFromType)
                .filter(Objects::nonNull);
    }

    @Override
    public String findMappedMethodName(final Class<?> clazz, final String methodName, final Class<?> returnType,
            final Class<?>... parameterTypes) {

        return ObfuscationReflectionHelper.remapName(INameMappingService.Domain.METHOD, methodName);
    }

    @Override
    public String findMappedFieldName(final Class<?> clazz, final String fieldName, final Class<?> fieldType) {

        return ObfuscationReflectionHelper.remapName(INameMappingService.Domain.FIELD, fieldName);
    }

    @Override
    public void registerCustomTradeConverters(
            Map<Class<? extends VillagerTrades.ItemListing>, Function<VillagerTrades.ItemListing, CTTradeObject>> classFunctionMap) {

        classFunctionMap.put(BasicItemListing.class, iTrade -> {
            if (iTrade instanceof BasicItemListing) {
                return new CTTradeObject(
                        IItemStack.ofMutable(((AccessBasicTrade) iTrade).crafttweaker$getPrice()),
                        IItemStack.ofMutable(((AccessBasicTrade) iTrade).crafttweaker$getPrice2()),
                        IItemStack.ofMutable(((AccessBasicTrade) iTrade).crafttweaker$getForSale()));
            }
            throw new IllegalArgumentException("Invalid trade passed to trade function! Given: " + iTrade.getClass());
        });
    }

    @Override
    public Map<ResourceLocation, ILootModifier> getLootModifiersMap() {

        try {
            LootModifierManager manager = HandleUtil
                    .invoke(() -> (LootModifierManager) Handles.LMM_GETTER.invokeExact());
            @SuppressWarnings("unchecked")
            Map<ResourceLocation, IGlobalLootModifier> map = (Map<ResourceLocation, IGlobalLootModifier>) Handles.LMM_MAP
                    .get(manager);

            // Someone else may make the map mutable, but I explicitly want CT stuff to go
            // last
            if (!(map instanceof CraftTweakerPrivilegedLootModifierMap)) {
                final Map<ResourceLocation, IGlobalLootModifier> finalMap = CraftTweakerPrivilegedLootModifierMap
                        .of(map);
                map = finalMap;
                Handles.LMM_MAP.set(manager, finalMap);
            }

            return ForgeLootModifierMapAdapter.adapt(map);
        } catch (final IllegalStateException e) {

            // LMM_GETTER.invokeExact() throws ISE if we're on the client and playing
            // multiplayer
            return Collections.emptyMap();
        }
    }

    @Override
    public IItemHandlerWrapper getPlayerInventory(Player player) {

        // First try getting the forge one, if that fails use the default vanilla one
        return player.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .map(IItemHandlerWrapper::new)
                .orElseThrow(() -> new RuntimeException(
                        "Player does not have the Item Handler capability, this is probably wrong!"));
    }

    @Override
    public boolean canItemStacksStack(ItemStack first, ItemStack second) {

        if (first.isEmpty() || !first.sameItem(second) || first.hasTag() != second.hasTag()) {
            return false;
        }

        return (!first.hasTag() || first.getTag().equals(second.getTag())) && first.areCapsCompatible(second);
    }

    @Override
    public boolean doCraftingTableRecipesConflict(IRecipeManager<?> manager, Recipe<?> first, Recipe<?> second) {

        return CraftingTableRecipeConflictChecker.checkConflicts(manager, first, second);
    }

    @Override
    public Set<MutableComponent> getFluidsForDump(ItemStack stack, Player player, InteractionHand hand) {

        LazyOptional<IFluidHandlerItem> cap = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
        if (!cap.isPresent()) {
            return Set.of();
        }
        Set<MutableComponent> components = new HashSet<>();
        cap.ifPresent(handler -> {
            int tanks = handler.getTanks();
            for (int i = 0; i < tanks; i++) {
                components.add(Component.literal(IFluidStack.of(handler.getFluidInTank(i)).getCommandString()));
            }
        });
        return components;
    }

    private <T extends Annotation> Stream<Type> fromScanData(
            final Type annotationType,
            final Consumer<Mod> classProviderConsumer,
            final Predicate<Either<T, Map<String, Object>>> annotationFilter,
            final ModFileScanData data) {

        return data.getAnnotations()
                .stream()
                .filter(it -> annotationType.equals(it.annotationType()))
                .filter(it -> annotationFilter.test(Either.right(it.annotationData())))
                .peek(ignored -> data.getIModInfoData()
                        .stream()
                        .flatMap(it -> it.getMods().stream())
                        .map(it -> new Mod(it.getModId(), it.getDisplayName(), it.getVersion().toString()))
                        .forEach(classProviderConsumer))
                .map(ModFileScanData.AnnotationData::clazz);
    }

    private static Class<?> getClassFromType(Type type) {

        try {
            return Class.forName(type.getClassName(), false, CraftTweakerCommon.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CompoundTag getCustomData(Entity entity) {

        return entity.getPersistentData();
    }

    @Override
    public CompoundTag getPersistentData(ServerPlayer player) {

        return player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
    }

    @Override
    public void addFoodPropertiesEffect(FoodProperties internal, MobEffectInstance effect, float probability) {

        ((AccessFoodPropertiesForge) internal).crafttweaker$getEffects().add(Pair.of(() -> effect, probability));
    }

    @Override
    public void removeFoodPropertiesEffect(FoodProperties internal, MobEffectInstance effect) {

        ((AccessFoodPropertiesForge) internal).crafttweaker$getEffects()
                .removeIf(pair -> pair.getFirst() != null && pair.getFirst().get().equals(effect));
    }

    @Override
    public void removeFoodPropertiesEffect(FoodProperties internal, MobEffect effect) {

        ((AccessFoodPropertiesForge) internal).crafttweaker$getEffects()
                .removeIf(pair -> pair.getFirst() != null && pair.getFirst().get().getEffect() == effect);
    }

    @Override
    public void invalidateIngredients(List<Ingredient> ingredients) {

        Ingredient.invalidateAll();
        for (Ingredient ingredient : ingredients) {
            // This is dumb, it should never be null, but I have multiple logs saying it is
            // null
            if (ingredient != null) {
                ingredient.checkInvalidation();
            }
        }
        ingredients.clear();
    }

}
