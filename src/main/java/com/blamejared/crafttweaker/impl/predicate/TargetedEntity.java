package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.loot.LootContext;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

/**
 * Indicates which entity should be targeted inside a predicate.
 *
 * Refer to the documentation of the various entries for a more detailed description.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.TargetedEntity")
@Document("vanilla/api/predicate/TargetedEntity")
public enum TargetedEntity {
    /**
     * The actor that caused the loot table roll or otherwise acted.
     *
     * Examples of an actor can be the creeper that exploded or the player that gained an advancement.
     */
    @ZenCodeType.Field ACTOR(ExpandLootContext::getThisEntity),
    /**
     * The entity that caused the death of the actor.
     *
     * This entity is the actual killer, instead of the entity that actually dealt the final blow to the actor. As an
     * example, if a skeleton killed a creeper by shooting an arrow, the killer will be the skeleton.
     */
    @ZenCodeType.Field KILLER(ExpandLootContext::getKillerEntity),
    /**
     * The entity that directly caused the death of the actor.
     *
     * The entity is the entity that dealt the final blow to the actor, rather than its actual killer. As an example,
     * if a skeleton killed a creeper by shooting an arrow, the direct killer will be the arrow.
     */
    @ZenCodeType.Field DIRECT_KILLER(ExpandLootContext::getDirectKillerEntity),
    /**
     * The player that caused the death of the actor, if applicable.
     *
     * This entity is exactly the same as the killer, except it is only available if the final blow was dealt by a
     * player.
     */
    @ZenCodeType.Field PLAYER_KILLER(ExpandLootContext::getLastDamagePlayer);
    
    private final Function<LootContext, ? extends Entity> lootContextDiscriminator;
    
    TargetedEntity(final Function<LootContext, ? extends Entity> lootContextDiscriminator) {
        
        this.lootContextDiscriminator = lootContextDiscriminator;
    }
    
    public Function<LootContext, ? extends Entity> getLootContextDiscriminator() {
        
        return this.lootContextDiscriminator;
    }
}
