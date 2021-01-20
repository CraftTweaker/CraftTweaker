package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.gson.JsonObject;
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
        this.x = FloatRangePredicate.unlimited();
        this.y = FloatRangePredicate.unlimited();
        this.z = FloatRangePredicate.unlimited();
        this.aboveCampfire = TriState.UNSET;
        this.lightLevel = new LightPredicate();
        this.block = new BlockPredicate();
        this.fluid = new FluidPredicate();
    }

    @ZenCodeType.Method
    public LocationPredicate withMinimumXPosition(final float min) {
        this.x = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withMaximumXPosition(final float max) {
        this.x = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withRangedXPosition(final float min, final float max) {
        this.x = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withExactXPosition(final float x) {
        return this.withRangedXPosition(x, x);
    }

    @ZenCodeType.Method
    public LocationPredicate withMinimumYPosition(final float min) {
        this.y = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withMaximumYPosition(final float max) {
        this.y = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withRangedYPosition(final float min, final float max) {
        this.y = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withExactYPosition(final int y) {
        return this.withRangedYPosition(y, y);
    }

    @ZenCodeType.Method
    public LocationPredicate withMinimumZPosition(final float min) {
        this.z = FloatRangePredicate.lowerBounded(min);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withMaximumZPosition(final float max) {
        this.z = FloatRangePredicate.upperBounded(max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withRangedZPosition(final float min, final float max) {
        this.z = FloatRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public LocationPredicate withExactZPosition(final int z) {
        return this.withRangedZPosition(z, z);
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
    public LocationPredicate withLightPredicate(final Consumer<LightPredicate> builder) {
        final LightPredicate predicate = new LightPredicate();
        builder.accept(predicate);
        this.lightLevel = predicate;
        return this;
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

    @Override
    public boolean isAny() {
        return this.x.isAny() && this.y.isAny() && this.z.isAny() && this.dimension == null && this.feature == null
                && this.biome == null && this.aboveCampfire == TriState.UNSET && this.lightLevel.isAny() && this.block.isAny()
                && this.fluid.isAny();
    }

    @Override
    public net.minecraft.advancements.criterion.LocationPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.LocationPredicate(
                this.x.toVanillaPredicate(),
                this.y.toVanillaPredicate(),
                this.z.toVanillaPredicate(),
                this.toVanilla(this.getRegistryKey(ForgeRegistries.BIOMES), this.biome),
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
    private <T extends IForgeRegistryEntry<T>> RegistryKey<Registry<T>> getRegistryKey(final IForgeRegistry<T> dumbForge) {
        if (!(dumbForge instanceof ForgeRegistry)) throw new IllegalStateException("Not a forge registry: " + dumbForge);
        return ((ForgeRegistry<T>) dumbForge).getRegistryKey();
    }
}
