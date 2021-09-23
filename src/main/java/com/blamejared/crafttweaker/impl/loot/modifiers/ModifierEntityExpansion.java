package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.EntityPropertiesLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.KilledByPlayerLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.TargetedEntity;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Additional methods for easier modification of entity-related loot tables.
 */
@Document("vanilla/api/loot/modifiers/EntityTypeExpansion")
@ZenCodeType.Expansion("crafttweaker.api.entity.MCEntityType")
@ZenRegister
public final class ModifierEntityExpansion {
    
    /**
     * Adds an {@link IItemStack} to the drops of this entity.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param stack    The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use {@link #addLootModifier(MCEntityType, String, ILootModifier)}
     * instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addDrop(final MCEntityType internal, final String uniqueId, final IItemStack stack) {
        
        addLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops of this entity.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param stacks   The stacks to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use {@link #addLootModifier(MCEntityType, String, ILootModifier)}
     * instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addDrops(final MCEntityType internal, final String uniqueId, final IItemStack... stacks) {
        
        addLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity.
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addLootModifier(final MCEntityType internal, final String name, final ILootModifier modifier) {
        
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForType(internal)),
                modifier
        );
    }
    
    /**
     * Adds an {@link IItemStack} to the drops of this entity, but only if the entity was killed by a player.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param stack    The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addPlayerOnlyLootModifier(MCEntityType, String, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addPlayerOnlyDrop(final MCEntityType internal, final String uniqueId, final IItemStack stack) {
        
        addPlayerOnlyLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops of this entity, but only if the entity was killed by a player.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param stacks   The stacks to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addPlayerOnlyLootModifier(MCEntityType, String, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addPlayerOnlyDrops(final MCEntityType internal, final String uniqueId, final IItemStack... stacks) {
        
        addPlayerOnlyLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player.
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addPlayerOnlyLootModifier(final MCEntityType internal, final String name, final ILootModifier modifier) {
        
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(KilledByPlayerLootConditionTypeBuilder.class)
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForType(internal)),
                modifier
        );
    }
    
    /**
     * Adds an {@link IItemStack} to the drops of this entity, but only if the entity was killed with the given weapon.
     *
     * Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param stack    The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addWeaponOnlyLootModifier(MCEntityType, String, IItemStack, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addWeaponOnlyDrop(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack stack) {
        
        addWeaponOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops of this entity, but only if the entity was killed with the given
     * weapon.
     *
     * Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param stacks   The stacks to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addWeaponOnlyLootModifier(MCEntityType, String, IItemStack, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addWeaponOnlyDrops(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack... stacks) {
        
        addWeaponOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed with the given
     * weapon.
     *
     * Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final ILootModifier modifier) {
        
        addWeaponOnlyLootModifier(internal, name, weapon, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed with the given
     * weapon, optionally considering its damage.
     *
     * Additional parameters that further specify the weapon, such as NBT, or count, are ignored.
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        
        addWeaponOnlyLootModifier(internal, name, weapon, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed with the given
     * weapon, optionally considering its damage and NBT data.
     *
     * Additional parameters that further specify the weapon, such as count, are ignored.
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param matchNbt    Whether to consider NBT data or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon,
                                                 final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForType(internal))
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForWeapon(weapon, matchDamage, matchNbt)),
                modifier
        );
    }
    
    /**
     * Adds an {@link IItemStack} to the drops of this entity, but only if the entity was killed by a player with the
     * given weapon.
     *
     * Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param stack    The stack to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addWeaponAndPlayerOnlyLootModifier(MCEntityType, String, IItemStack, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyDrop(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack stack) {
        
        addWeaponAndPlayerOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.add(stack));
    }
    
    /**
     * Adds a list of {@link IItemStack}s to the drops of this entity, but only if the entity was killed by a player
     * with the given weapon.
     *
     * Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.
     *
     * @param internal The entity to add drops to.
     * @param uniqueId A unique id for the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param stacks   The stacks to add to the drops.
     *
     * @deprecated Scheduled for removal in 1.17: use
     * {@link #addWeaponAndPlayerOnlyLootModifier(MCEntityType, String, IItemStack, ILootModifier)} instead.
     */
    @Deprecated
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyDrops(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack... stacks) {
        
        addWeaponAndPlayerOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.addAll(stacks));
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player with the
     * given weapon.
     *
     * Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final ILootModifier modifier) {
        
        addWeaponAndPlayerOnlyLootModifier(internal, name, weapon, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player with the
     * given weapon, optionally considering its damage.
     *
     * Additional parameters that further specify the weapon, such as NBT, or count, are ignored.
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        
        addWeaponAndPlayerOnlyLootModifier(internal, name, weapon, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player with the
     * given weapon, optionally considering its damage and NBT data.
     *
     * Additional parameters that further specify the weapon, such as count, are ignored.
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param matchNbt    Whether to consider NBT data or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon,
                                                          final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(KilledByPlayerLootConditionTypeBuilder.class)
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForType(internal))
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForWeapon(weapon, matchDamage, matchNbt)),
                modifier
        );
    }
    
    private static Consumer<EntityPropertiesLootConditionTypeBuilder> makeForType(final MCEntityType type) {
        
        return builder -> builder.withTargetedEntity(TargetedEntity.ACTOR)
                .withPredicate(entity -> entity.withEntityTypePredicate(entityType -> entityType.withType(type)));
    }
    
    private static Consumer<EntityPropertiesLootConditionTypeBuilder> makeForWeapon(final IItemStack weapon, final boolean matchDamage, final boolean matchNbt) {
        
        return builder -> builder.withTargetedEntity(TargetedEntity.KILLER)
                .withPredicate(entity -> entity.withEquipmentPredicate(equipment ->
                        equipment.withItemInHand(item -> item.matching(weapon, matchDamage, false, matchNbt))));
    }
    
}
