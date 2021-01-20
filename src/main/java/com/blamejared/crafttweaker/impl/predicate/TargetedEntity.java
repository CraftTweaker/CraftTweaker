package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.TargetedEntity")
@Document("vanilla/api/predicate/TargetedEntity")
public enum TargetedEntity {
    ACTOR(ExpandLootContext::getThisEntity),
    KILLER(ExpandLootContext::getKillerEntity),
    DIRECT_KILLER(ExpandLootContext::getDirectKillerEntity),
    PLAYER_KILLER(ExpandLootContext::getLastDamagePlayer);

    private final Function<LootContext, ? extends Entity> lootContextDiscriminator;

    TargetedEntity(final Function<LootContext, ? extends Entity> lootContextDiscriminator) {
        this.lootContextDiscriminator = lootContextDiscriminator;
    }

    public Function<LootContext, ? extends Entity> getLootContextDiscriminator() {
        return this.lootContextDiscriminator;
    }
}
