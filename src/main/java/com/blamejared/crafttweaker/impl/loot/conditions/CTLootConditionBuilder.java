package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.AndLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.JsonLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.OrLootConditionTypeBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Manages the creation of one or multiple {@link ILootCondition}s using the builders provided by
 * {@link ILootConditionTypeBuilder}.
 *
 * Each instance of this class may handle the presence of more than one conditions. On the other hand, some builders may
 * require the presence of at most one condition. Refer to their documentation for more information.
 *
 * This builder does not force any particular interpretation in case of multiple conditions being added: they may be
 * merged together via 'AND', 'OR', or not merged together at all. It is up to the user of the builder to decide how
 * the merges should happen.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.LootConditionBuilder")
@Document("vanilla/api/loot/conditions/LootConditionBuilder")
public final class CTLootConditionBuilder {
    private final List<ILootCondition> conditions;

    private CTLootConditionBuilder() {
        this.conditions = new ArrayList<>();
    }

    // Creation
    /**
     * Creates a new empty {@link CTLootConditionBuilder}.
     *
     * @return The newly created instance.
     */
    @ZenCodeType.Method
    public static CTLootConditionBuilder create() {
        return new CTLootConditionBuilder();
    }

    /**
     * Creates a new {@link CTLootConditionBuilder} containing an {@link AndLootConditionTypeBuilder}.
     *
     * This ensures that, no matter what the underlying implementation may assume, the various conditions will be
     * treated as part of an 'And' condition. Refer to {@link AndLootConditionTypeBuilder} for more information.
     *
     * @param lender A consumer used to configure an {@link AndLootConditionTypeBuilder} condition builder.
     * @return The newly created instance, containing the 'And' condition.
     */
    @ZenCodeType.Method
    public static CTLootConditionBuilder createInAnd(final Consumer<AndLootConditionTypeBuilder> lender) {
        return createForSingle(AndLootConditionTypeBuilder.class, lender);
    }

    /**
     * Creates a new {@link CTLootConditionBuilder} containing an {@link OrLootConditionTypeBuilder}.
     *
     * This ensures that, no matter what the underlying implementation may assume, the various conditions will be
     * treated as part of an 'Or' condition. Refer to {@link OrLootConditionTypeBuilder} for more information.
     *
     * @param lender A consumer used to configure an {@link OrLootConditionTypeBuilder} condition builder.
     * @return The newly created instance, containing the 'Or' condition.
     */
    @ZenCodeType.Method
    public static CTLootConditionBuilder createInOr(final Consumer<OrLootConditionTypeBuilder> lender) {
        return createForSingle(OrLootConditionTypeBuilder.class, lender);
    }

    /**
     * Creates a new {@link CTLootConditionBuilder} and automatically adds a single condition of the specified type.
     *
     * This is merely a helper method to avoid multiple method calls and chains when the user needs to create a builder
     * but only wants to add a single condition to it. It is effectively a call to <code>create</code> followed by one
     * to <code>add</code>.
     *
     * @param reifiedType The type of the condition to add. It must extend {@link ILootConditionTypeBuilder}.
     * @param lender A consumer that allows configuration of the given condition.
     * @param <T> The known type of the condition itself.
     * @return The newly created builder, already containing the created condition.
     */
    @ZenCodeType.Method
    public static <T extends ILootConditionTypeBuilder> CTLootConditionBuilder createForSingle(final Class<T> reifiedType, final Consumer<T> lender) {
        final CTLootConditionBuilder base = create();
        base.add(reifiedType, lender);
        return base;
    }

    /**
     * Creates a new {@link ILootCondition} of the given type, according to the parameters specified in the
     * <code>lender</code>.
     *
     * In other words, a new {@link ILootCondition} is created based on the chosen {@link ILootConditionTypeBuilder}.
     *
     * This is particularly useful if the creation of a single loot condition is required and the user wants to use one
     * of the already existing builders.
     *
     * @param reifiedType The type of the condition builder to use. It must extend {@link ILootConditionTypeBuilder}.
     * @param lender A consumer that allows configuration of the given condition.
     * @param <T> The known type of the condition itself.
     * @return The condition created by the builder itself.
     */
    @ZenCodeType.Method
    public static <T extends ILootConditionTypeBuilder> ILootCondition makeSingle(final Class<T> reifiedType, final Consumer<T> lender) {
        return createForSingle(reifiedType, lender).single();
    }

    /**
     * Creates an {@link ILootCondition} of the given <code>type</code> parsing the given <code>json</code>.
     *
     * The JSON must respect the constraints specified in the documentation of the {@link JsonLootConditionTypeBuilder}
     * loot condition.
     *
     * If no valid condition is found, or the JSON is invalid, an error gets thrown.
     *
     * @param type A {@link ResourceLocation} identifying the type of the loot condition to create.
     * @param data The JSON data, according to the given constraints.
     * @return An {@link ILootCondition} instance built according to the given data, if possible.
     */
    @ZenCodeType.Method
    public static ILootCondition makeJson(final ResourceLocation type, final IData data) {
        return makeSingle(JsonLootConditionTypeBuilder.class, builder -> builder.withJson(type, data));
    }

    /**
     * Creates an {@link ILootCondition} of the given <code>type</code> parsing the given <code>json</code>.
     *
     * The name is treated as a {@link ResourceLocation}, lacking the type safety of the bracket handler. For this
     * reason, it's suggested to prefer the method with a {@link ResourceLocation} as parameter.
     *
     * The JSON must respect the constraints specified in the documentation of the {@link JsonLootConditionTypeBuilder}
     * loot condition.
     *
     * If no valid condition is found, or the JSON is invalid, an error gets thrown.
     *
     * @param type A string in resource location format identifying the type of the loot condition to create.
     * @param data The JSON data, according to the given constraints.
     * @return An {@link ILootCondition} instance built according to the given data, if possible.
     */
    @ZenCodeType.Method
    public static ILootCondition makeJson(final String type, final IData data) {
        return makeSingle(JsonLootConditionTypeBuilder.class, builder -> builder.withJson(type, data));
    }

    // Usage
    /**
     * Adds a new condition of the given type to the ones of this builder.
     *
     * The condition is built according to the defaults of the {@link ILootConditionTypeBuilder} specified. It is thus
     * assumed that the default values lead to a well-formed and correct loot condition. If such isn't the case, then
     * the method may behave erratically or throw an exception: refer to the two parameter version of <code>add</code>
     * for the method that allows configuration.
     *
     * @param reifiedType The type of the condition to add. It must extend {@link ILootConditionTypeBuilder}.
     * @param <T> The known type of the condition itself.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> CTLootConditionBuilder add(final Class<T> reifiedType) {
        return this.add(reifiedType, ignore -> {});
    }

    /**
     * Adds a new condition of the given type and configuration to the ones of this builder.
     *
     * The condition is built according to the specified {@link ILootConditionTypeBuilder} and configured according to
     * the details given in <code>lender</code>. If the default configuration is satisfying, then the single parameter
     * version of <code>add</code> may also be used.
     *
     * @param reifiedType The type of the condition to add. It must extend {@link ILootConditionTypeBuilder}.
     * @param lender A consumer that allows configuration of the given condition.
     * @param <T> The known type of the condition itself.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> CTLootConditionBuilder add(final Class<T> reifiedType, final Consumer<T> lender) {
        final ILootCondition built = this.make(reifiedType, "main loot builder", lender);
        if (built != null) this.conditions.add(built);
        return this;
    }

    public final <T extends ILootConditionTypeBuilder> ILootCondition make(final Class<T> type, final String id, final Consumer<T> lender) {
        final T builder;
        try {
            builder = LootConditionManager.get(this, type);
        } catch (final NullPointerException | ClassCastException e) {
            CraftTweakerAPI.logThrowing("Unable to create a loot condition builder for type '%s'", e, type.getName());
            return null;
        }

        try {
            lender.accept(builder);
        } catch (final ClassCastException e) {
            CraftTweakerAPI.logThrowing("Unable to pass a loot condition builder for type '%s' to lender", e, type.getName());
            return null;
        } catch (final Exception e) {
            CraftTweakerAPI.logThrowing("An error has occurred while populating the builder for type '%s'", e, type.getName());
            return null;
        }

        try {
            return builder.finish();
        } catch (final IllegalStateException e) {
            CraftTweakerAPI.logThrowing("Unable to add a loot condition of type '%s' to '%s' due to an invalid builder state", e, type.getName(), id);
        }
        return null;
    }

    /**
     * Builds the current builder, returning all its contents as an array of {@link ILootCondition}s.
     *
     * The builder may then be re-used for additional purposes, though this is not suggested.
     *
     * @return The current set of built conditions.
     */
    @ZenCodeType.Method
    public ILootCondition[] build() {
        return this.conditions.toArray(new ILootCondition[0]);
    }

    public ILootCondition single() {
        return this.conditions.size() != 1? null : this.conditions.get(0);
    }
}
