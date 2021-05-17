package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents the predicate for an {@link net.minecraft.entity.Entity}'s type.
 *
 * This predicate will check an entity's type against either a tag ({@link MCTag}) or a {@link MCEntityType} directly.
 * The first check will override the second if they are both present.
 *
 * By default, any entity type is valid for this predicate.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EntityTypePredicate")
@Document("vanilla/api/predicate/EntityTypePredicate")
public final class EntityTypePredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.EntityTypePredicate> {
    private MCEntityType rawType;
    private MCTag<MCEntityType> tag;

    public EntityTypePredicate() {
        super(net.minecraft.advancements.criterion.EntityTypePredicate.ANY);
    }

    /**
     * Sets the {@link MCEntityType} that this predicate should match exactly.
     *
     * If a tag to match against has already been set, then the tag check will override this check.
     *
     * @param type The entity type this predicate should match.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityTypePredicate withType(final MCEntityType type) {
        this.rawType = type;
        return this;
    }

    /**
     * Sets the {@link MCTag} that this predicate should use for matching the entity's type.
     *
     * The predicate will match successfully only if the given entity type is part of the given tag.
     *
     * Specifying both a tag and an entity type to check against will make the tag override the direct type comparison.
     *
     * @param tag The tag the predicate should use for matching.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityTypePredicate withTag(final MCTag<MCEntityType> tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.rawType == null && this.tag == null;
    }

    @Override
    public net.minecraft.advancements.criterion.EntityTypePredicate toVanilla() {
        if (this.rawType != null && this.tag != null) {
            CraftTweakerAPI.logWarning("'EntityTypePredicate' specifies both an entity type and a tag: the second will override the first");
        }
        if (this.tag != null) {
            return net.minecraft.advancements.criterion.EntityTypePredicate.fromTag(CraftTweakerHelper.getITag(this.tag));
        }
        assert this.rawType != null;
        return net.minecraft.advancements.criterion.EntityTypePredicate.fromType(this.rawType.getInternal());
    }
}
