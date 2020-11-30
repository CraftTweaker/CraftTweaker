package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.BlockStateProperty")
@Document("vanilla/api/loot/conditions/BlockStateProperty")
public final class BlockStatePropertyLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    // Two options: either custom CraftTweaker or the vanilla way
    // OPTION 1: CraftTweaker
    private BlockState state;
    // OPTION 2: Vanilla
    private Block block;
    private final Map<String, BiPredicate<StateHolder<?, ?>, Property<?>>> matchers;

    BlockStatePropertyLootConditionTypeBuilder() {
        this.matchers = new LinkedHashMap<>();
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withState(final BlockState state) {
        this.state = state;
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withBlock(final Block block) {
        this.block = block;
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withProperty(final String name, final String value) {
        this.matchers.put(name, (holder, property) -> this.exactMatcher(holder, property, value));
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withProperty(final String name, final int value) {
        return this.withProperty(name, Integer.toString(value));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withProperty(final String name, final boolean value) {
        return this.withProperty(name, Boolean.toString(value));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withRangedProperty(final String name, @ZenCodeType.Nullable final String min, @ZenCodeType.Nullable final String max) {
        this.matchers.put(name, (holder, property) -> (min != null || max != null) && this.rangedMatcher(holder, property, min, max));
        return this;
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withRangedProperty(final String name, final int min, final int max) {
        return this.withRangedProperty(name, Integer.toString(min), Integer.toString(max));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withUpperBoundedProperty(final String name, final int max) {
        return this.withRangedProperty(name, null, Integer.toString(max));
    }

    @ZenCodeType.Method
    public BlockStatePropertyLootConditionTypeBuilder withLowerBoundedProperty(final String name, final int min) {
        return this.withRangedProperty(name, Integer.toString(min), null);
    }

    @Override
    public ILootCondition finish() {
        if (this.state == null && this.block == null && this.matchers.isEmpty()) {
            throw new IllegalStateException("Required options are missing: either block-state or block with properties must be specified");
        }
        if (this.state != null && this.block != null) {
            throw new IllegalStateException("You can only use either a block-state or a block + properties, not both");
        }
        if (this.state == null) {
            // vanilla way
            return context -> this.match(Objects.requireNonNull(ExpandLootContext.getBlockState(context)));
        } else {
            // CrT way
            return context -> Objects.requireNonNull(ExpandLootContext.getBlockState(context)).equals(this.state);
        }
    }

    private boolean match(final BlockState state) {
        return state.getBlock() == this.block && this.matchProperties(state.getBlock().getStateContainer(), state);
    }

    private <S extends StateHolder<?, S>> boolean matchProperties(final StateContainer<?, S> state, final S blockState) {
        return this.matchers.entrySet().stream().allMatch(entry -> {
            final Property<?> targetProperty = state.getProperty(entry.getKey());
            return targetProperty != null && entry.getValue().test(blockState, targetProperty);
        });
    }

    private <T extends Comparable<T>> boolean exactMatcher(final StateHolder<?, ?> holder, final Property<T> property, final String value) {
        final T propertyValue = holder.get(property);
        final Optional<T> targetValue = property.parseValue(value);
        return targetValue.isPresent() && propertyValue.compareTo(targetValue.get()) == 0;
    }

    private <T extends Comparable<T>> boolean rangedMatcher(final StateHolder<?, ?> holder, final Property<T> property, final String min, final String max) {
        final T propertyValue = holder.get(property);

        if (min != null) {
            final Optional<T> parsedMin = property.parseValue(min);
            if (!parsedMin.isPresent() || propertyValue.compareTo(parsedMin.get()) < 0) {
                return false;
            }
        }

        if (max != null) {
            final Optional<T> parsedMax = property.parseValue(max);
            return parsedMax.isPresent() && propertyValue.compareTo(parsedMax.get()) <= 0;
        }

        return true;
    }
}
