package com.blamejared.crafttweaker.api.loot.modifier;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.loot.ActionRegisterLootModifier;
import com.blamejared.crafttweaker.api.action.loot.ActionRemoveLootModifier;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.util.NameUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Manager for loot modifiers.
 *
 * <p>An instance of this manager can be obtained via the {@link com.blamejared.crafttweaker.api.loot.LootManager}.</p>
 *
 * <p>The main usage of this manager is for registering "global loot modifiers", also known as "loot modifiers" for
 * short. A global loot modifier runs on the loot drop of every loot table (unless otherwise specified by conditions)
 * and is as such able to modify it according either to predetermined parameters (e.g. replacing items) or via
 * completely customized code that leverages the dropping context.</p>
 *
 * <p>For more information, refer to {@link ILootModifier}.</p>
 *
 * @docParam this loot.modifiers
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifier.LootModifierManager")
@Document("vanilla/api/loot/modifier/LootModifierManager")
public enum LootModifierManager {
    INSTANCE;
    
    private final Supplier<Map<ResourceLocation, ILootModifier>> lootMap = Services.PLATFORM::getLootModifiersMap;
    
    /**
     * Registers a new global loot modifier with the given name.
     *
     * <p>The loot modifier will be run only when the given set of conditions is satisfied.</p>
     *
     * @param name       The unique identifier for the loot modifier. It must be all lowercase and devoid of both spaces and
     *                   colons.
     * @param conditions A set of conditions that restrict the context in which the loot modifier applies.
     * @param modifier   The loot modifier itself. It may be created via {@link CommonLootModifiers}.
     */
    @ZenCodeType.Method
    public void register(final String name, final LootConditions conditions, final ILootModifier modifier) {
        
        CraftTweakerAPI.apply(new ActionRegisterLootModifier(this.fromName(name), this.buildModifierCreator(conditions, modifier), this.lootMap));
    }
    
    /**
     * Gets the loot modifier with the given name, if it exists.
     *
     * <p>If no loot modifier with that name exists, a default no-op instance is returned.</p>
     *
     * @param name The name of the loot modifier.
     *
     * @return The {@link ILootModifier} with the given name, or a default one if no such instance exists.
     */
    @ZenCodeType.Method
    public ILootModifier getByName(final String name) {
        
        return this.lootMap.get().getOrDefault(new ResourceLocation(name), ILootModifier.DEFAULT);
    }
    
    /**
     * Gets a list of all the names of the currently registered loot modifiers.
     *
     * @return A list with all the names of the currently registered loot modifiers.
     */
    @ZenCodeType.Method
    public List<ResourceLocation> getAllNames() {
        
        return List.copyOf(this.lootMap.get().keySet());
    }
    
    /**
     * Gets a list containing all currently registered loot modifiers.
     *
     * @return A list containing all currently registered loot modifiers.
     */
    @ZenCodeType.Method
    public List<ILootModifier> getAll() {
        
        return List.copyOf(this.lootMap.get().values());
    }
    
    /**
     * Removes the loot modifier with the given name.
     *
     * <p>The name may either contain a colon or not. If no colon is present, it is assumed that the loot modifier name
     * is one of the modifiers that have been already registered in a script.</p>
     *
     * @param name The name of the loot modifier to remove.
     */
    @ZenCodeType.Method
    public void removeByName(final String name) {
        
        final ResourceLocation id = name.contains(":") ? new ResourceLocation(name) : new ResourceLocation(CraftTweakerConstants.MOD_ID, name);
        CraftTweakerAPI.apply(new ActionRemoveLootModifier("with name '" + id + "'", entry -> entry.getKey()
                .equals(id), this.lootMap));
    }
    
    /**
     * Removes all loot modifiers that have been registered by the mod with the given ID.
     *
     * @param modId The mod ID.
     */
    @ZenCodeType.Method
    public void removeByModId(final String modId) {
        
        CraftTweakerAPI.apply(new ActionRemoveLootModifier("registered by the mod '" + modId + "'", entry -> modId.equals(entry.getKey()
                .getNamespace()), this.lootMap));
    }
    
    /**
     * Removes all loot modifiers whose name matches the given regular expression.
     *
     * <p>The entire name is taken into consideration for the match, effectively matching the format of a
     * {@link ResourceLocation}.</p>
     *
     * @param regex The regular expression to match.
     */
    @ZenCodeType.Method
    public void removeByRegex(final String regex) {
        
        final Pattern pattern = Pattern.compile(regex);
        CraftTweakerAPI.apply(new ActionRemoveLootModifier(
                "matching the regular expression '" + regex + "'",
                entry -> pattern.matcher(entry.getKey().toString()).matches(),
                this.lootMap)
        );
    }
    
    /**
     * Removes all loot modifiers that have been registered up to this point.
     */
    @ZenCodeType.Method
    public void removeAll() {
        
        CraftTweakerAPI.apply(new ActionRemoveLootModifier(null, entry -> true, this.lootMap));
    }
    
    private Supplier<ILootModifier> buildModifierCreator(final LootConditions conditions, final ILootModifier modifier) {
        
        final Predicate<LootContext> condition = conditions.gather();
        return () -> (loot, context) -> condition.test(context) ? modifier.modify(loot, context) : loot;
    }
    
    private ResourceLocation fromName(final String name) {
        
        return NameUtil.fromFixedName(
                name,
                // TODO("Or CommonLoggers.zenCode()?")
                (fixed, mistakes) -> CommonLoggers.own().warn(
                        "The given loot modifier name '{}' isn't valid due to:\n{}\nThe name was changed to '{}'",
                        name,
                        String.join("\n", mistakes),
                        fixed
                )
        );
    }
    
}
