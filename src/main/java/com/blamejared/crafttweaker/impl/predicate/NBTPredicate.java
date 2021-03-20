package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a predicate for all kinds of NBT data.
 *
 * The predicate will match the given NBT exactly, making partial matches not possible with this predicate.
 * Nevertheless, a predicate without any NBT specified will be considered effectively as a way of matching any NBT data
 * construct.
 *
 * By default, the predicate allows any NBT, without checking it.
 */
@ZenRegister()
@ZenCodeType.Name("crafttweaker.api.predicate.NBTPredicate")
@Document("vanilla/api/predicate/NBTPredicate")
public final class NBTPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.NBTPredicate> {
    private IData data;

    public NBTPredicate() {
        super(net.minecraft.advancements.criterion.NBTPredicate.ANY);
    }

    /**
     * Sets the NBT data that should be matched by this predicate.
     *
     * The given {@link IData} instance is required to be an instance of {@link MapData}. Any other type of data cannot
     * be checked by this predicate.
     *
     * If the NBT data had already been set, then the data is replaced with the new instance.
     *
     * @param data A {@link MapData} representing the NBT to match.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public NBTPredicate withData(final IData data) {
        if (!(data instanceof MapData)) throw new IllegalArgumentException("Data inside an 'NBTPredicate' must be an instance of MapData");
        this.data = data;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.data == null || this.data.getInternal() == null;
    }

    @Override
    public net.minecraft.advancements.criterion.NBTPredicate toVanilla() {
        assert this.data instanceof MapData;
        return new net.minecraft.advancements.criterion.NBTPredicate(((MapData) this.data).getInternal());
    }
}
