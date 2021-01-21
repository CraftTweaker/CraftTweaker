package com.blamejared.crafttweaker.impl.loot.modifier;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.api.loot.ILootModifier;
import com.blamejared.crafttweaker.impl.actions.loot.ActionRegisterLootModifier;
import com.blamejared.crafttweaker.impl.actions.loot.ActionRemoveLootModifier;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
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
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifier.LootModifierManager")
@Document("vanilla/api/loot/modifier/LootModifierManager")
@SuppressWarnings("unused")
public class CTLootModifierManager {
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

    @ZenCodeType.Method
    public void register(final String name, final ILootCondition[] conditions, final ILootModifier modifier) {
        CraftTweakerAPI.apply(new ActionRegisterLootModifier(this.fromName(name), conditions == null? new ILootCondition[0] : conditions, modifier, this::getLmmMap));
    }

    @ZenCodeType.Method
    public void register(final String name, final CTLootConditionBuilder builder, final ILootModifier modifier) {
        this.register(name, builder.build(), modifier);
    }

    @ZenCodeType.Method
    public void registerUnconditional(final String name, final ILootModifier modifier) {
        this.register(name, new ILootCondition[0], modifier);
    }

    @ZenCodeType.Method
    public ILootModifier getByName(final String name) {
        return new MCLootModifier(this.getLmmMap().get(this.fromName(name)));
    }

    @ZenCodeType.Method
    public List<String> getAllNames() {
        return this.getLmmMap().keySet().stream().map(ResourceLocation::toString).collect(Collectors.toList());
    }

    @ZenCodeType.Method
    public List<ILootModifier> getAll() {
        return this.getAllNames().stream().map(this::getByName).collect(Collectors.toList());
    }

    @ZenCodeType.Method
    public void removeByName(final String name) {
        final ResourceLocation id = name.contains(":")? new ResourceLocation(name) : this.fromName(name);
        CraftTweakerAPI.apply(ActionRemoveLootModifier.of(id::equals, "with name '" + id + "'", this::getLmmMap));
    }

    @ZenCodeType.Method
    public void removeByModId(final String modId) {
        CraftTweakerAPI.apply(ActionRemoveLootModifier.of(name -> modId.equals(name.getNamespace()), "registered by the mod '" + modId + "'", this::getLmmMap));
    }

    @ZenCodeType.Method
    public void removeByRegex(final String regex) {
        CraftTweakerAPI.apply(ActionRemoveLootModifier.of(name -> name.toString().matches(regex), "matching the regular expression '" + regex + "'", this::getLmmMap));
    }

    @ZenCodeType.Method
    public void removeAll() {
        CraftTweakerAPI.apply(new ActionRemoveLootModifier(ignored -> true, null, this::getLmmMap));
    }

    @SuppressWarnings("unchecked")
    private Map<ResourceLocation, IGlobalLootModifier> getLmmMap() {
        try {
            final LootModifierManager lmm = (LootModifierManager) LMM_GETTER.invokeExact();
            Map<ResourceLocation, IGlobalLootModifier> map = (Map<ResourceLocation, IGlobalLootModifier>) LMM_MAP_GETTER.invokeExact(lmm);
            if (map instanceof ImmutableMap) LMM_MAP_SETTER.invokeExact(lmm, map = new HashMap<>(map)); // Let's "mutabilize" the map
            return map;
        } catch (final IllegalStateException e) {
            // LMM_GETTER.invokeExact() throws ISE if we're on the client and playing multiplayer
            return Collections.emptyMap();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private ResourceLocation fromName(final String name) {
        StringBuilder mistakes = null;
        String fixedName = name;

        if (fixedName.indexOf(':') >= 0) {
            mistakes = new StringBuilder("- it cannot contain colons (':')");
            fixedName = fixedName.replace(':', '.');
        }
        if (fixedName.indexOf(' ') >= 0) {
            final String mistake = "- it cannot contain spaces";
            if (mistakes == null) mistakes = new StringBuilder(mistake); else mistakes.append('\n').append(mistake);
            fixedName = fixedName.replace(' ', '.');
        }
        if (!fixedName.toLowerCase(Locale.ROOT).equals(fixedName)) {
            final String mistake = "- it must be all lowercase";
            if (mistakes == null) mistakes = new StringBuilder(mistake); else mistakes.append('\n').append(mistake);
            fixedName = fixedName.toLowerCase(Locale.ROOT);
        }

        RuntimeException thrownException = null;
        ResourceLocation result = null;

        try {
            result = new ResourceLocation(CraftTweaker.MODID, fixedName);
        } catch (final ResourceLocationException | IllegalArgumentException e) {
            thrownException = e;
            if (mistakes == null) mistakes = new StringBuilder();
            mistakes.append("- ").append(e.getMessage());
        }

        if (mistakes != null) {
            CraftTweakerAPI.logWarning(
                    "The given loot modifier name '%s' isn't valid due to the following issues:%n%s%nThe loot modifier name was changed to '%s'",
                    name, mistakes.toString(), fixedName
            );
        }

        if (thrownException != null) throw thrownException;

        return result;
    }
}
