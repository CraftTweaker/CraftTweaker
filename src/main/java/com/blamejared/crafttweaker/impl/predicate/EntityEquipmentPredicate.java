package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Represents the predicate for an {@link net.minecraft.entity.Entity}'s equipment.
 *
 * The predicate can be used to check any number and combination of items that are being used by the entity as armor or
 * held as weapons, either in the main hand or in the off-hand, with the help of {@link ItemPredicate}s.
 *
 * By default, the entity passes the checks no matter its current equipment.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EntityEquipmentPredicate")
@Document("vanilla/api/predicate/EntityEquipmentPredicate")
public final class EntityEquipmentPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.EntityEquipmentPredicate> {
    private ItemPredicate head;
    private ItemPredicate chest;
    private ItemPredicate legs;
    private ItemPredicate feet;
    private ItemPredicate mainHand;
    private ItemPredicate offHand;

    public EntityEquipmentPredicate() {
        super(net.minecraft.advancements.criterion.EntityEquipmentPredicate.ANY);
        this.head = new ItemPredicate();
        this.chest = new ItemPredicate();
        this.legs = new ItemPredicate();
        this.feet = new ItemPredicate();
        this.mainHand = new ItemPredicate();
        this.offHand = new ItemPredicate();
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be used to match the entity's head armor slot.
     *
     * Any changes that have been made previously to the item predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityEquipmentPredicate withHeadItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate head = new ItemPredicate();
        builder.accept(head);
        this.head = head;
        return this;
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be used to match the entity's chest armor slot.
     *
     * Any changes that have been made previously to the item predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityEquipmentPredicate withChestItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate chest = new ItemPredicate();
        builder.accept(chest);
        this.chest = chest;
        return this;
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be used to match the entity's legs armor slot.
     *
     * Any changes that have been made previously to the item predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityEquipmentPredicate withLegsItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate legs = new ItemPredicate();
        builder.accept(legs);
        this.legs = legs;
        return this;
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be used to match the entity's feet armor slot.
     *
     * Any changes that have been made previously to the item predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityEquipmentPredicate withFeetItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate feet = new ItemPredicate();
        builder.accept(feet);
        this.feet = feet;
        return this;
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be used to match the item held in the entity's hand.
     *
     * It is assumed that the entity can carry items only in its main hand.
     *
     * Any changes that have been made previously to the item predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityEquipmentPredicate withItemInHand(final Consumer<ItemPredicate> builder) {
        return this.withItemInMainHand(builder);
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be used to match the item held in the entity's main hand.
     *
     * Any changes that have been made previously to the item predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityEquipmentPredicate withItemInMainHand(final Consumer<ItemPredicate> builder) {
        final ItemPredicate mainHand = new ItemPredicate();
        builder.accept(mainHand);
        this.mainHand = mainHand;
        return this;
    }

    /**
     * Creates and sets the {@link ItemPredicate} that will be used to match the item held in the entity's off hand.
     *
     * Any changes that have been made previously to the item predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link ItemPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityEquipmentPredicate withItemInOffHand(final Consumer<ItemPredicate> builder) {
        final ItemPredicate offHand = new ItemPredicate();
        builder.accept(offHand);
        this.offHand = offHand;
        return this;
    }

    @Override
    public boolean isAny() {
        return Stream.of(this.head, this.chest, this.legs, this.feet, this.mainHand, this.offHand).allMatch(ItemPredicate::isAny);
    }

    @Override
    public net.minecraft.advancements.criterion.EntityEquipmentPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.EntityEquipmentPredicate(
                this.head.toVanillaPredicate(),
                this.chest.toVanillaPredicate(),
                this.legs.toVanillaPredicate(),
                this.feet.toVanillaPredicate(),
                this.mainHand.toVanillaPredicate(),
                this.offHand.toVanillaPredicate()
        );
    }
}
