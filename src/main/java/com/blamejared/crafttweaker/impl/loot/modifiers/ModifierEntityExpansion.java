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
    public static void addDrop(final MCEntityType $this, final String uniqueId, final IItemStack stack) {
        addLootModifier($this, uniqueId, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addDrops(final MCEntityType $this, final String uniqueId, final IItemStack... stacks) {
        addLootModifier($this, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addLootModifier(final MCEntityType $this, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(EntityPropertiesLootConditionTypeBuilder.class, makeForType($this)),
                modifier
        );
    }
    
    @ZenCodeType.Method
    public static void addPlayerOnlyDrop(final MCEntityType $this, final String uniqueId, final IItemStack stack) {
        addPlayerOnlyLootModifier($this, uniqueId, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addPlayerOnlyDrops(final MCEntityType $this, final String uniqueId, final IItemStack... stacks) {
        addPlayerOnlyLootModifier($this, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addPlayerOnlyLootModifier(final MCEntityType $this, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(KilledByPlayerLootConditionTypeBuilder.class).add(EntityPropertiesLootConditionTypeBuilder.class, makeForType($this)),
                modifier
        );
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyDrop(final MCEntityType $this, final String uniqueId, final IItemStack weapon, final IItemStack stack) {
        addWeaponOnlyLootModifier($this, uniqueId, weapon, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyDrops(final MCEntityType $this, final String uniqueId, final IItemStack weapon, final IItemStack... stacks) {
        addWeaponOnlyLootModifier($this, uniqueId, weapon, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType $this, final String name, final IItemStack weapon, final ILootModifier modifier) {
        addWeaponOnlyLootModifier($this, name, weapon, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType $this, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        addWeaponOnlyLootModifier($this, name, weapon, matchDamage, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final MCEntityType $this, final String name, final IItemStack weapon,
                                                 final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForType($this))
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForWeapon(weapon, matchDamage, matchNbt)),
                modifier
        );
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyDrop(final MCEntityType $this, final String uniqueId, final IItemStack weapon, final IItemStack stack) {
        addWeaponAndPlayerOnlyLootModifier($this, uniqueId, weapon, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyDrops(final MCEntityType $this, final String uniqueId, final IItemStack weapon, final IItemStack... stacks) {
        addWeaponAndPlayerOnlyLootModifier($this, uniqueId, weapon, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType $this, final String name, final IItemStack weapon, final ILootModifier modifier) {
        addWeaponAndPlayerOnlyLootModifier($this, name, weapon, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType $this, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        addWeaponAndPlayerOnlyLootModifier($this, name, weapon, matchDamage, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final MCEntityType $this, final String name, final IItemStack weapon,
                                                          final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(KilledByPlayerLootConditionTypeBuilder.class)
                        .add(EntityPropertiesLootConditionTypeBuilder.class, makeForType($this))
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
