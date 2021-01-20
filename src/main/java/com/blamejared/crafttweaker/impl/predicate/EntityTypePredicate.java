package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EntityTypePredicate")
@Document("vanilla/api/predicate/EntityTypePredicate")
public final class EntityTypePredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.EntityTypePredicate> {
    private MCEntityType rawType;
    private MCTag<MCEntityType> tag;

    public EntityTypePredicate() {
        super(net.minecraft.advancements.criterion.EntityTypePredicate.ANY);
    }

    @ZenCodeType.Method
    public EntityTypePredicate withType(final MCEntityType type) {
        this.rawType = type;
        return this;
    }

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
            CraftTweakerAPI.logWarning("'EntityTypePredicate' specifies both an entity type and a tag: the second will take precedence");
        }
        if (this.tag != null) {
            return net.minecraft.advancements.criterion.EntityTypePredicate.fromTag(CraftTweakerHelper.getITag(this.tag));
        }
        assert this.rawType != null;
        return net.minecraft.advancements.criterion.EntityTypePredicate.fromType(this.rawType.getInternal());
    }
}
