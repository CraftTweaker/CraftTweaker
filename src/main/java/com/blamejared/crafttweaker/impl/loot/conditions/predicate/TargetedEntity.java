package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntity;
import com.blamejared.crafttweaker.impl.loot.MCLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.TargetedEntity")
@Document("vanilla/api/loot/conditions/predicate/TargetedEntity")
public enum TargetedEntity {
    ACTOR(MCLootContext::getThisEntity),
    KILLER(MCLootContext::getKillerEntity),
    DIRECT_KILLER(MCLootContext::getDirectKillerEntity),
    PLAYER_KILLER(context -> new MCEntity(context.getLastDamagePlayer().getInternal()));

    private final Function<MCLootContext, ? extends MCEntity> discriminator;

    TargetedEntity(final Function<MCLootContext, ? extends MCEntity> discriminator) {
        this.discriminator = discriminator;
    }

    public Function<MCLootContext, ? extends MCEntity> getDiscriminator() {
        return this.discriminator;
    }
}
