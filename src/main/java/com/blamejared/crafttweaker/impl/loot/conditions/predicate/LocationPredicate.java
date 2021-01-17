package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.conditions.FloatRange;
import com.blamejared.crafttweaker.impl.loot.conditions.IntRange;
import com.blamejared.crafttweaker.impl.loot.conditions.TriState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.LightPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
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

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.LocationPredicate")
@Document("vanilla/api/loot/conditions/predicate/LocationPredicate")
public final class LocationPredicate {
    private FloatRange x;
    private FloatRange y;
    private FloatRange z;
    private ResourceLocation dimension;
    private String feature;
    @SuppressWarnings("SpellCheckingInspection") private ResourceLocation biome;
    private TriState aboveCampfire;
    private IntRange lightLevel;
    private BlockPredicate block;
    private FluidPredicate fluid;

    public LocationPredicate() {
        this.aboveCampfire = TriState.UNSET;
        this.block = new BlockPredicate();
    }

    @ZenCodeType.Method
    public LocationPredicate withXPositionRange(final int min, final int max) {
        this.x = new FloatRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withXPosition(final int x) {
        return this.withXPositionRange(x, x);
    }

    @ZenCodeType.Method
    public LocationPredicate withYPositionRange(final int min, final int max) {
        this.y = new FloatRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withYPosition(final int y) {
        return this.withXPositionRange(y, y);
    }

    @ZenCodeType.Method
    public LocationPredicate withZPositionRange(final int min, final int max) {
        this.z = new FloatRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withZPosition(final int z) {
        return this.withXPositionRange(z, z);
    }

    @ZenCodeType.Method
    public LocationPredicate withDimension(final ResourceLocation dimension) {
        this.dimension = dimension;
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withFeatureName(final String feature) {
        this.feature = feature;
        return this;
    }

    @SuppressWarnings("SpellCheckingInspection")
    @ZenCodeType.Method
    public LocationPredicate withBiomeName(final ResourceLocation biome) {
        this.biome = biome;
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withCampfireBelow() {
        this.aboveCampfire = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withoutCampfireBelow() {
        this.aboveCampfire = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withLightLevel(final int min, final int max) {
        this.lightLevel = new IntRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withLightLevel(final int value) {
        return this.withLightLevel(value, value);
    }

    @ZenCodeType.Method
    public LocationPredicate withBlockPredicate(final Consumer<BlockPredicate> builder) {
        final BlockPredicate predicate = new BlockPredicate();
        builder.accept(predicate);
        this.block = predicate;
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withFluidPredicate(final Consumer<FluidPredicate> builder) {
        final FluidPredicate predicate = new FluidPredicate();
        builder.accept(predicate);
        this.fluid = predicate;
        return this;
    }

    boolean isAny() {
        return this.x == null && this.y == null && this.z == null && this.dimension == null && this.feature == null
                && this.biome == null && this.aboveCampfire == TriState.UNSET && this.lightLevel == null && this.block.isAny()
                && this.fluid.isAny();
    }

    public net.minecraft.advancements.criterion.LocationPredicate toVanilla() {
        if (this.isAny()) return net.minecraft.advancements.criterion.LocationPredicate.ANY;
        return new net.minecraft.advancements.criterion.LocationPredicate(
                this.toVanilla(this.x),
                this.toVanilla(this.y),
                this.toVanilla(this.z),
                this.toVanilla(this.getRegistryKey(ForgeRegistries.BIOMES), this.biome),
                this.toVanilla(this.feature),
                this.toVanilla(Registry.WORLD_KEY, this.dimension),
                this.aboveCampfire.toBoolean(),
                this.toVanilla(this.lightLevel),
                this.block.toVanilla(),
                this.fluid.toVanilla()
        );
    }

    private MinMaxBounds.FloatBound toVanilla(final FloatRange range) {
        return range == null? MinMaxBounds.FloatBound.UNBOUNDED : range.toVanillaFloatBound();
    }

    private <T> RegistryKey<T> toVanilla(final RegistryKey<? extends Registry<T>> registry, final ResourceLocation name) {
        if (name == null) return null;
        return RegistryKey.getOrCreateKey(registry, name);
    }

    private Structure<?> toVanilla(final String name) {
        return name == null? null : Structure.NAME_STRUCTURE_BIMAP.get(name);
    }

    private LightPredicate toVanilla(final IntRange range) {
        final MinMaxBounds.IntBound bounds = range == null? MinMaxBounds.IntBound.UNBOUNDED : range.toVanillaIntBound();
        final JsonObject object = new JsonObject();
        object.add("light", bounds.serialize());
        // Round trip part two
        return LightPredicate.deserialize(object);
    }

    @SuppressWarnings("SameParameterValue")
    private <T extends IForgeRegistryEntry<T>> RegistryKey<Registry<T>> getRegistryKey(final IForgeRegistry<T> dumbForge) {
        if (!(dumbForge instanceof ForgeRegistry)) throw new IllegalStateException("Not a forge registry: " + dumbForge);
        return ((ForgeRegistry<T>) dumbForge).getRegistryKey();
    }
}
