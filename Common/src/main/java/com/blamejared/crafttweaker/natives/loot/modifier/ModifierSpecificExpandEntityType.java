package com.blamejared.crafttweaker.natives.loot.modifier;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.loot.condition.LootConditions;
import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandLootItemEntityPropertyCondition;
import com.blamejared.crafttweaker.natives.loot.condition.ExpandLootItemKilledByPlayerCondition;
import com.blamejared.crafttweaker.natives.predicate.ExpandEntityEquipmentPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandEntityPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandItemPredicate;
import com.blamejared.crafttweaker.natives.predicate.ExpandMinMaxBoundsInts;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Additional methods for easier modification of entity-related loot tables.
 */
@Document("vanilla/api/loot/modifier/EntityLootModifiers")
@SuppressWarnings("rawtypes")
@ZenCodeType.Expansion("crafttweaker.api.entity.EntityType")
@ZenRegister
public class ModifierSpecificExpandEntityType {
    
    /**
     * Adds an {@link ILootModifier} to the current entity.
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addLootModifier(final EntityType internal, final String name, final ILootModifier modifier) {
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.only(makeForType(internal)),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player.
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addPlayerOnlyLootModifier(final EntityType internal, final String name, final ILootModifier modifier) {
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.allOf(makeForType(internal), ExpandLootItemKilledByPlayerCondition.create()),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed with the given
     * weapon.
     *
     * <p>Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.</p>
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final EntityType internal, final String name, final IItemStack weapon, final ILootModifier modifier) {
        
        addWeaponOnlyLootModifier(internal, name, weapon, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed with the given
     * weapon, optionally considering its damage.
     *
     * <p>Additional parameters that further specify the weapon, such as NBT, or count, are ignored.</p>
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final EntityType internal, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        
        addWeaponOnlyLootModifier(internal, name, weapon, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed with the given
     * weapon, optionally considering its damage and NBT data.
     *
     * <p>Additional parameters that further specify the weapon, such as count, are ignored.</p>
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param matchNbt    Whether to consider NBT data or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponOnlyLootModifier(final EntityType internal, final String name, final IItemStack weapon,
                                                 final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.allOf(makeForType(internal), makeForWeapon(weapon, matchDamage, matchNbt)),
                modifier
        );
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player with the
     * given weapon.
     *
     * <p>Additional parameters that further specify the weapon, such as NBT, count, or damage, are ignored.</p>
     *
     * @param internal The entity to add the loot modifier to.
     * @param name     The name of the loot modifier.
     * @param weapon   The weapon that needs to be used to kill the entity.
     * @param modifier The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final EntityType internal, final String name, final IItemStack weapon, final ILootModifier modifier) {
        
        addWeaponAndPlayerOnlyLootModifier(internal, name, weapon, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player with the
     * given weapon, optionally considering its damage.
     *
     * <p>Additional parameters that further specify the weapon, such as NBT, or count, are ignored.</p>
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final EntityType internal, final String name, final IItemStack weapon, final boolean matchDamage, final ILootModifier modifier) {
        
        addWeaponAndPlayerOnlyLootModifier(internal, name, weapon, matchDamage, false, modifier);
    }
    
    /**
     * Adds an {@link ILootModifier} to the current entity that fires only if the entity was killed by a player with the
     * given weapon, optionally considering its damage and NBT data.
     *
     * <p>Additional parameters that further specify the weapon, such as count, are ignored.</p>
     *
     * @param internal    The entity to add the loot modifier to.
     * @param name        The name of the loot modifier.
     * @param weapon      The weapon that needs to be used to kill the entity.
     * @param matchDamage Whether to consider damage or not when identifying the weapon.
     * @param matchNbt    Whether to consider NBT data or not when identifying the weapon.
     * @param modifier    The loot modifier to add to the entity.
     */
    @ZenCodeType.Method
    public static void addWeaponAndPlayerOnlyLootModifier(final EntityType internal, final String name, final IItemStack weapon,
                                                          final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        
        LootManager.INSTANCE.getModifierManager().register(
                name,
                LootConditions.allOf(
                        makeForType(internal),
                        makeForWeapon(weapon, matchDamage, matchNbt),
                        ExpandLootItemKilledByPlayerCondition.create()
                ),
                modifier
        );
    }
    
    private static LootItemCondition.Builder makeForType(final EntityType type) {
        
        return ExpandLootItemEntityPropertyCondition.create(
                LootContext.EntityTarget.THIS,
                ExpandEntityPredicate.create(type)
        );
    }
    
    private static LootItemCondition.Builder makeForWeapon(final IItemStack weapon, final boolean matchDamage, final boolean matchNbt) {
        
        final ItemPredicate.Builder item = ExpandItemPredicate.create(weapon);
        
        if(matchDamage) {
            item.hasDurability(ExpandMinMaxBoundsInts.exactly(weapon.getDamage()));
        }
        
        if(matchNbt) {
            final CompoundTag tag = weapon.getInternal().getTag();
            
            if(tag != null) {
                item.hasNbt(tag);
            }
        }
        
        return ExpandLootItemEntityPropertyCondition.create(
                LootContext.EntityTarget.KILLER,
                ExpandEntityPredicate.create()
                        .equipment(ExpandEntityEquipmentPredicate.create().mainhand(item.build()).build())
        );
    }
    
}
