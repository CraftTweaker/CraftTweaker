package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Represents a predicate for a location an entity or a block may be in.
 *
 * This predicate firstly ensures that the given location is inside the coordinate bounds. If the check passes, then
 * the predicate ensures that the dimension, biome, and feature that are at the specified coordinates correctly match
 * the values that have been specified if any. Moreover, the predicate is also able to check if the block is situated
 * above a campfire, or validate either the block or fluid at the current location, using respectively either a
 * {@link BlockPredicate} or a {@link FluidPredicate}. It is also able to check the light level of the current location
 * with a {@link LightPredicate}.
 *
 * By default, any location is valid for this predicate.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.LocationPredicate")
@Document("vanilla/api/predicate/LocationPredicate")
public final class LocationPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.LocationPredicate> {
    private FloatRangePredicate x;
    private FloatRangePredicate y;
    private FloatRangePredicate z;
    private ResourceLocation dimension;
    private String feature;
    @SuppressWarnings("SpellCheckingInspection") private ResourceLocation biome;
    private TriState aboveCampfire;
    private LightPredicate lightLevel;
    private BlockPredicate block;
    private FluidPredicate fluid;

    public LocationPredicate() {
        super(net.minecraft.advancements.criterion.LocationPredicate.ANY);
        this.x = FloatRangePredicate.unbounded();
        this.y = FloatRangePredicate.unbounded();
        this.z = FloatRangePredicate.unbounded();
        this.aboveCampfire = TriState.UNSET;
        this.lightLevel = new LightPredicate();
        this.block = new BlockPredicate();
        this.fluid = new FluidPredicate();
    }

    /**
     * Sets the minimum value the X position can assume to <code>min</code>.
     *
     * If the position had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the position didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the X position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withMinimumXPosition(final float min) {
        this.x = FloatRangePredicate.mergeLowerBound(this.x, min);
        return this;
    }

    /**
     * Sets the maximum value the X position can assume to <code>max</code>.
     *
     * If the position had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the position didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param max The maximum value the X position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withMaximumXPosition(final float max) {
        this.x = FloatRangePredicate.mergeUpperBound(this.x, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum values the X position can assume to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the position had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the X position can assume.
     * @param max The maximum value the X position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withRangedXPosition(final float min, final float max) {
        this.x = FloatRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the value of the X position to exactly match the given <code>value</code>.
     *
     * If the position had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param x The exact value the X position should assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withExactXPosition(final float x) {
        return this.withRangedXPosition(x, x);
    }

    /**
     * Sets the minimum value the Y position can assume to <code>min</code>.
     *
     * If the position had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the position didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the Y position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withMinimumYPosition(final float min) {
        this.y = FloatRangePredicate.mergeLowerBound(this.y, min);
        return this;
    }

    /**
     * Sets the maximum value the Y position can assume to <code>max</code>.
     *
     * If the position had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the position didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param max The maximum value the Y position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withMaximumYPosition(final float max) {
        this.y = FloatRangePredicate.mergeUpperBound(this.y, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum values the Y position can assume to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the position had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the Y position can assume.
     * @param max The maximum value the Y position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withRangedYPosition(final float min, final float max) {
        this.y = FloatRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the value of the Y position to exactly match the given <code>value</code>.
     *
     * If the position had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param y The exact value the Y position should assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withExactYPosition(final int y) {
        return this.withRangedYPosition(y, y);
    }

    /**
     * Sets the minimum value the Z position can assume to <code>min</code>.
     *
     * If the position had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the position didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the Z position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withMinimumZPosition(final float min) {
        this.z = FloatRangePredicate.mergeLowerBound(this.z, min);
        return this;
    }

    /**
     * Sets the maximum value the Z position can assume to <code>max</code>.
     *
     * If the position had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the position didn't have any bounds set, the
     * maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param max The maximum value the Z position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withMaximumZPosition(final float max) {
        this.z = FloatRangePredicate.mergeUpperBound(this.z, max);
        return this;
    }

    /**
     * Sets both the minimum and maximum values the Z position can assume to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the position had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the Z position can assume.
     * @param max The maximum value the Z position can assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withRangedZPosition(final float min, final float max) {
        this.z = FloatRangePredicate.bounded(min, max);
        return this;
    }

    /**
     * Sets the value of the Z position to exactly match the given <code>value</code>.
     *
     * If the position had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param z The exact value the Z position should assume.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withExactZPosition(final int z) {
        return this.withRangedZPosition(z, z);
    }

    /**
     * Sets the dimension where the location should be located in.
     *
     * @param dimension The name of the dimension.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withDimension(final ResourceLocation dimension) {
        this.dimension = dimension;
        return this;
    }

    /**
     * Sets the name of the feature this location should be located in.
     *
     * @param feature The name of the feature.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withFeatureName(final String feature) {
        this.feature = feature;
        return this;
    }

    /**
     * Sets the biome in which the location should be.
     *
     * @param biome The name of the biome.
     * @return This predicate for chaining.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @ZenCodeType.Method
    public LocationPredicate withBiomeName(final ResourceLocation biome) {
        this.biome = biome;
        return this;
    }

    /**
     * Indicates that the location must be on top of a campfire.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withCampfireBelow() {
        this.aboveCampfire = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the location must not be on top of a campfire.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withoutCampfireBelow() {
        this.aboveCampfire = TriState.FALSE;
        return this;
    }

    /**
     * Creates and sets the {@link LightPredicate} that will be used to match the light level at the location.
     *
     * Any changes that have been made previously to the light level predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link LightPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withLightPredicate(final Consumer<LightPredicate> builder) {
        final LightPredicate predicate = new LightPredicate();
        builder.accept(predicate);
        this.lightLevel = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link BlockPredicate} that will be used to match the block at the location.
     *
     * Any changes that have been made previously to the block predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link BlockPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withBlockPredicate(final Consumer<BlockPredicate> builder) {
        final BlockPredicate predicate = new BlockPredicate();
        builder.accept(predicate);
        this.block = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link FluidPredicate} that will be used to match the block at the location, if present.
     *
     * Any changes that have been made previously to the fluid predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link FluidPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public LocationPredicate withFluidPredicate(final Consumer<FluidPredicate> builder) {
        final FluidPredicate predicate = new FluidPredicate();
        builder.accept(predicate);
        this.fluid = predicate;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.x.isAny() && this.y.isAny() && this.z.isAny() && this.dimension == null && this.feature == null
                && this.biome == null && this.aboveCampfire.isUnset() && this.lightLevel.isAny() && this.block.isAny()
                && this.fluid.isAny();
    }

    @Override
    public net.minecraft.advancements.criterion.LocationPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.LocationPredicate(
                this.x.toVanillaPredicate(),
                this.y.toVanillaPredicate(),
                this.z.toVanillaPredicate(),
                this.toVanilla(ForgeRegistries.BIOMES, this.biome),
                this.feature == null? null : Structure.NAME_STRUCTURE_BIMAP.get(this.feature),
                this.toVanilla(Registry.WORLD_KEY, this.dimension),
                this.aboveCampfire.toBoolean(),
                this.lightLevel.toVanillaPredicate(),
                this.block.toVanillaPredicate(),
                this.fluid.toVanillaPredicate()
        );
    }

    private <T> RegistryKey<T> toVanilla(final RegistryKey<? extends Registry<T>> registry, final ResourceLocation name) {
        if (name == null) return null;
        return RegistryKey.getOrCreateKey(registry, name);
    }

    @SuppressWarnings("SameParameterValue")
    private <T extends IForgeRegistryEntry<T>> RegistryKey<T> toVanilla(final IForgeRegistry<T> dumbForge, final ResourceLocation name) {
        if (!(dumbForge instanceof ForgeRegistry)) throw new IllegalStateException("Not a forge registry: '" + dumbForge.getRegistryName() + "' (was " + dumbForge + ")");
        return this.toVanilla(((ForgeRegistry<T>) dumbForge).getRegistryKey(), name);
    }
}
