package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.actions.loot.ActionRegisterLootModifier;
import com.blamejared.crafttweaker.impl.actions.loot.ActionRemoveLootModifier;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.util.NameUtils;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeInternalHandler;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifierManager;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manager for loot modifiers.
 *
 * An instance of this manager can be obtained via the {@link com.blamejared.crafttweaker.impl.loot.CTLootManager}.
 *
 * The main usage of this manager is for registering "global loot modifiers", also known as "loot modifiers" for short.
 * A global loot modifier runs on the loot drop of every loot table (unless otherwise specified by conditions) and is
 * as such able to modify it according either to predetermined parameters (e.g. replacing items) or via completely
 * customized code that leverages the dropping context.
 *
 * For more information, refer to {@link ILootModifier}.
 *
 * @docParam this loot.modifiers
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifiers.LootModifierManager")
@Document("vanilla/api/loot/modifiers/LootModifierManager")
@SuppressWarnings("unused")
public final class CTLootModifierManager {
    public static final CTLootModifierManager LOOT_MODIFIER_MANAGER = new CTLootModifierManager();

    private static final MethodHandle LMM_GETTER;
    private static final MethodHandle LMM_MAP_GETTER;
    private static final MethodHandle LMM_MAP_SETTER;

    private CTLootModifierManager() {}

    static {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();

            final Class<?> forgeInternalHandlerClass = ForgeInternalHandler.class;
            final Method lmmGetterMethod = forgeInternalHandlerClass.getDeclaredMethod("getLootModifierManager");
            lmmGetterMethod.setAccessible(true);

            final Class<?> lmmClass = LootModifierManager.class;
            final Field registeredLootModifiersField = lmmClass.getDeclaredField("registeredLootModifiers");
            registeredLootModifiersField.setAccessible(true);

            LMM_GETTER = lookup.unreflect(lmmGetterMethod);
            LMM_MAP_GETTER = lookup.unreflectGetter(registeredLootModifiersField);
            LMM_MAP_SETTER = lookup.unreflectSetter(registeredLootModifiersField);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Unable to reflect into the loot modifier manager", e);
        }
    }

    /**
     * Registers a new global loot modifier with the given name.
     *
     * The loot modifier will be run only when all conditions pass, effectively as if they were merged with an 'AND'
     * condition. An empty condition array (or a <code>null</code> one) indicates a lack of conditions, meaning that the
     * loot modifier will be run on every loot table.
     *
     * It is nevertheless suggested to provide a list of conditions that match as closely as possible the desired
     * conditions rather than checking them manually in the modifier itself, since the game may perform additional
     * optimizations.
     *
     * @param name The unique identifier for the loot modifier. It must be all lowercase and devoid of both spaces and
     *             colons.
     * @param conditions A set of conditions that restrict the context in which the loot modifier applies. It can be
     *                   empty or <code>null</code>, which indicates a lack of conditions. The conditions are all merged
     *                   together with an 'AND' connector.
     * @param modifier The loot modifier itself. It may be created via {@link CommonLootModifiers}.
     */
    @ZenCodeType.Method
    public void register(final String name, @ZenCodeType.Nullable final ILootCondition[] conditions, final ILootModifier modifier) {
        CraftTweakerAPI.apply(new ActionRegisterLootModifier(this.fromName(name), conditions == null? new ILootCondition[0] : conditions, modifier, this::getLmmMap));
    }

    /**
     * Registers a new global loot modifier with the given name.
     *
     * The loot modifier will be run only when all conditions registered within the {@link CTLootConditionBuilder} pass,
     * effectively as if they were merged with an 'AND' condition.
     *
     * This method is to be preferred instead of the method that takes a raw {@link ILootCondition} array.
     *
     * It is suggested to provide a list of conditions that match as closely as possible the desired conditions rather
     * than checking them manually in the modifier itself, since the game may perform additional optimizations.
     *
     * @param name The unique identifier for the loot modifier. It must be all lowercase and devoid of both spaces and
     *             colons.
     * @param builder A {@link CTLootConditionBuilder} representing a list of conditions that should be merged together
     *                via 'AND'.
     * @param modifier The loot modifier itself. It may be created via {@link CommonLootModifiers}.
     */
    @ZenCodeType.Method
    public void register(final String name, final CTLootConditionBuilder builder, final ILootModifier modifier) {
        this.register(name, builder.build(), modifier);
    }

    /**
     * Registers a new global loot modifier with the given name without providing any conditions.
     *
     * The loot modifier will be run for every loot table rolled in the entire game. It is thus suggested to use this
     * method sparingly and to implement a very fast loot modifier.
     *
     * @param name The unique identifier for the loot modifier. It must be all lowercase and devoid of both spaces and
     *             colons.
     * @param modifier The loot modifier itself. It may be created via {@link CommonLootModifiers}.
     */
    @ZenCodeType.Method
    public void registerUnconditional(final String name, final ILootModifier modifier) {
        this.register(name, new ILootCondition[0], modifier);
    }

    /**
     * Gets the loot modifier with the given name, if it exists.
     *
     * If no loot modifier with that name exists, a default no-op instance is returned.
     *
     * @param name The name of the loot modifier.
     * @return The {@link ILootModifier} with the given name, or a default one if no such instance exists.
     */
    @ZenCodeType.Method
    public ILootModifier getByName(final String name) {
        return new MCLootModifier(this.getLmmMap().get(new ResourceLocation(name)));
    }

    /**
     * Gets a list of all the names of the currently registered loot modifiers.
     *
     * @return A list with all the names of the currently registered loot modifiers.
     */
    @ZenCodeType.Method
    public List<String> getAllNames() {
        return this.getLmmMap().keySet().stream().map(ResourceLocation::toString).collect(Collectors.toList());
    }

    /**
     * Gets a list containing all currently registered loot modifiers.
     *
     * @return A list containing all currently registered loot modifiers.
     */
    @ZenCodeType.Method
    public List<ILootModifier> getAll() {
        return this.getAllNames().stream().map(this::getByName).collect(Collectors.toList());
    }

    /**
     * Removes the loot modifier with the given name.
     *
     * The name may either contain a colon or not. If no colon is present, it is assumed that the loot modifier name is
     * one of the modifiers that have been already registered in a script.
     *
     * @param name The name of the loot modifier to remove.
     */
    @ZenCodeType.Method
    public void removeByName(final String name) {
        final ResourceLocation id = name.contains(":")? new ResourceLocation(name) : new ResourceLocation(CraftTweaker.MODID, name);
        CraftTweakerAPI.apply(ActionRemoveLootModifier.of(id::equals, "with name '" + id + "'", this::getLmmMap));
    }

    /**
     * Removes all loot modifiers that have been registered by the mod with the given ID.
     *
     * @param modId The mod ID.
     */
    @ZenCodeType.Method
    public void removeByModId(final String modId) {
        CraftTweakerAPI.apply(ActionRemoveLootModifier.of(name -> modId.equals(name.getNamespace()), "registered by the mod '" + modId + "'", this::getLmmMap));
    }

    /**
     * Removes all loot modifiers whose name matches the given regular expression.
     *
     * The entire name is taken into consideration for the match, effectively matching the format of a
     * {@link ResourceLocation}.
     *
     * @param regex The regular expression to match.
     */
    @ZenCodeType.Method
    public void removeByRegex(final String regex) {
        CraftTweakerAPI.apply(ActionRemoveLootModifier.of(name -> name.toString().matches(regex), "matching the regular expression '" + regex + "'", this::getLmmMap));
    }

    /**
     * Removes all loot modifiers that have been registered up to this point.
     */
    @ZenCodeType.Method
    public void removeAll() {
        CraftTweakerAPI.apply(new ActionRemoveLootModifier(ignored -> true, null, this::getLmmMap));
    }

    @SuppressWarnings("unchecked")
    private Map<ResourceLocation, IGlobalLootModifier> getLmmMap() {
        try {
            final LootModifierManager lmm = (LootModifierManager) LMM_GETTER.invokeExact();
            Map<ResourceLocation, IGlobalLootModifier> map = (Map<ResourceLocation, IGlobalLootModifier>) LMM_MAP_GETTER.invokeExact(lmm);
            if (map instanceof ImmutableMap) {
                map = new HashMap<>(map);
                LMM_MAP_SETTER.invokeExact(lmm, map); // Let's "mutabilize" the map
            }
            return map;
        } catch (final IllegalStateException e) {
            // LMM_GETTER.invokeExact() throws ISE if we're on the client and playing multiplayer
            return Collections.emptyMap();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ResourceLocation fromName(final String name) {
        return NameUtils.fromFixedName(
                name,
                (fixed, mistakes) -> CraftTweakerAPI.logWarning(
                        "The given loot modifier name '%s' isn't valid due to:\n%s\nThe name was changed to '%s'",
                        name,
                        String.join("\n", mistakes),
                        fixed
                )
        );
    }
}
