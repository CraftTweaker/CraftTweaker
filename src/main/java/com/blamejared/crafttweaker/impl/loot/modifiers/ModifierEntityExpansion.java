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

@Document("vanilla/api/entities/MCEntityType")
@ZenCodeType.Expansion("crafttweaker.api.entity.MCEntityType")
@ZenRegister
public final class ModifierEntityExpansion {
    @ZenCodeType.Method
    public static void addDrop(final MCEntityType internal, final String uniqueId, final IItemStack stack) {
        addLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addDrops(final MCEntityType internal, final String uniqueId, final IItemStack... stacks) {
        addLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addLootModifier(final MCEntityType internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(EntityPropertiesLootConditionTypeBuilder.class, makeForType(internal)),
                modifier
        );
    }
    
    @ZenCodeType.Method
    public static void addPlayerOnlyDrop(final MCEntityType internal, final String uniqueId, final IItemStack stack) {
        addPlayerOnlyLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addPlayerOnlyDrops(final MCEntityType internal, final String uniqueId, final IItemStack... stacks) {
        addPlayerOnlyLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addPlayerOnlyLootModifier(final MCEntityType internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(KilledByPlayerLootConditionTypeBuilder.class).add(EntityPropertiesLootConditionTypeBuilder.class, makeForType(internal)),
                modifier
        );
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyDrop(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack stack) {
        addWeaponOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyDrops(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack... stacks) {
        addWeaponOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final ILootModifier modifier) {
        addWeaponOnlyLootModifier(internal, name, weapon, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        addWeaponOnlyLootModifier(internal, name, weapon, matchDamage, false, modifier);
    }
    
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
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyDrop(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack stack) {
        addWeaponAndPlayerOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyDrops(final MCEntityType internal, final String uniqueId, final IItemStack weapon, final IItemStack... stacks) {
        addWeaponAndPlayerOnlyLootModifier(internal, uniqueId, weapon, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final ILootModifier modifier) {
        addWeaponAndPlayerOnlyLootModifier(internal, name, weapon, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType internal, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        addWeaponAndPlayerOnlyLootModifier(internal, name, weapon, matchDamage, false, modifier);
    }
    
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
