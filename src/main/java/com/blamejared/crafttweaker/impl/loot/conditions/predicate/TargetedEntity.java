package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.TargetedEntity")
@Document("vanilla/api/loot/conditions/predicate/TargetedEntity")
public enum TargetedEntity {
    ACTOR(ExpandLootContext::getThisEntity),
    KILLER(ExpandLootContext::getKillerEntity),
    DIRECT_KILLER(ExpandLootContext::getDirectKillerEntity),
    PLAYER_KILLER(ExpandLootContext::getLastDamagePlayer);

    private final Function<LootContext, ? extends Entity> discriminator;

    TargetedEntity(final Function<LootContext, ? extends Entity> discriminator) {
        this.discriminator = discriminator;
    }

    public Function<LootContext, ? extends Entity> getDiscriminator() {
        return this.discriminator;
    }
}
