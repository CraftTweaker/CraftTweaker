package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;
import java.util.stream.Stream;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EntityEquipmentPredicate")
@Document("vanilla/api/predicate/EntityEquipmentPredicate")
public class EntityEquipmentPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.EntityEquipmentPredicate> {
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

    @ZenCodeType.Method
    public EntityEquipmentPredicate withHeadItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate head = new ItemPredicate();
        builder.accept(head);
        this.head = head;
        return this;
    }

    @ZenCodeType.Method
    public EntityEquipmentPredicate withChestItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate chest = new ItemPredicate();
        builder.accept(chest);
        this.chest = chest;
        return this;
    }

    @ZenCodeType.Method
    public EntityEquipmentPredicate withLegsItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate legs = new ItemPredicate();
        builder.accept(legs);
        this.legs = legs;
        return this;
    }

    @ZenCodeType.Method
    public EntityEquipmentPredicate withFeetItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate feet = new ItemPredicate();
        builder.accept(feet);
        this.feet = feet;
        return this;
    }

    @ZenCodeType.Method
    public EntityEquipmentPredicate withItemInHand(final Consumer<ItemPredicate> builder) {
        return this.withItemInMainHand(builder);
    }

    @ZenCodeType.Method
    public EntityEquipmentPredicate withItemInMainHand(final Consumer<ItemPredicate> builder) {
        final ItemPredicate mainHand = new ItemPredicate();
        builder.accept(mainHand);
        this.mainHand = mainHand;
        return this;
    }

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
