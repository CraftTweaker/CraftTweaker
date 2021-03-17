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

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.LootConditionBuilder")
@Document("vanilla/api/loot/conditions/LootConditionBuilder")
public final class CTLootConditionBuilder {
    private final List<ILootCondition> conditions;

    private CTLootConditionBuilder() {
        this.conditions = new ArrayList<>();
    }

    // Creation
    @ZenCodeType.Method
    public static CTLootConditionBuilder create() {
        return new CTLootConditionBuilder();
    }

    @ZenCodeType.Method
    public static CTLootConditionBuilder createInAnd(final Consumer<AndLootConditionTypeBuilder> lender) {
        return createForSingle(AndLootConditionTypeBuilder.class, lender);
    }

    @ZenCodeType.Method
    public static CTLootConditionBuilder createInOr(final Consumer<OrLootConditionTypeBuilder> lender) {
        return createForSingle(OrLootConditionTypeBuilder.class, lender);
    }

    @ZenCodeType.Method
    public static <T extends ILootConditionTypeBuilder> CTLootConditionBuilder createForSingle(final Class<T> reifiedType, final Consumer<T> lender) {
        final CTLootConditionBuilder base = create();
        base.add(reifiedType, lender);
        return base;
    }

    @ZenCodeType.Method
    public static <T extends ILootConditionTypeBuilder> ILootCondition makeSingle(final Class<T> reifiedType, final Consumer<T> lender) {
        return createForSingle(reifiedType, lender).single();
    }

    @ZenCodeType.Method
    public static ILootCondition makeJson(final ResourceLocation type, final IData data) {
        return makeSingle(JsonLootConditionTypeBuilder.class, builder -> builder.withJson(type, data));
    }

    @ZenCodeType.Method
    public static ILootCondition makeJson(final String type, final IData data) {
        return makeSingle(JsonLootConditionTypeBuilder.class, builder -> builder.withJson(type, data));
    }

    // Usage
    @ZenCodeType.Method
    public <T extends ILootConditionTypeBuilder> CTLootConditionBuilder add(final Class<T> reifiedType) {
        return this.add(reifiedType, ignore -> {});
    }

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

    @ZenCodeType.Method
    public ILootCondition[] build() {
        return this.conditions.toArray(new ILootCondition[0]);
    }

    public ILootCondition single() {
        return this.conditions.size() != 1? null : this.conditions.get(0);
    }
}
