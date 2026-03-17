import crafttweaker.api.item.IItemStack;
import crafttweaker.api.entity.attribute.AttributeModifier;
import crafttweaker.api.item.component.ItemAttributeModifiers;

// recipe itself
craftingTable.addShaped("infinite_attributes", <item:minecraft:iron_sword>, [
    [<item:minecraft:apple>],
    [<item:minecraft:apple>],
    [<item:minecraft:iron_sword>.withoutComponents()] // withoutComponents so it matches the base item
],
    (usualOut as IItemStack, inputs as IItemStack[][]) => {
            var inputSword = inputs[2][0];
            var modifierID = <resource:crafttweaker:strength>;

            // Build new attribute modifiers - copy all from input sword except our target modifier
            var builder = ItemAttributeModifiers.builder();
            var bonus = 0.5;
            if (inputSword.hasAttributeModifiers) {
                for entry in inputSword.attributeModifiers.modifiers {
                    if entry.modifier.id == modifierID {
                        // when we find out modifier, we add it to our bonus
                        bonus += entry.modifier.amount;
                    } else {
                        builder.add(entry.attribute, entry.modifier, entry.slot);
                    }
                }
            }

            // Create and add the new bonus attribute
            var newModifier = AttributeModifier.create(modifierID, bonus, <constant:minecraft:attribute/operation:add_value>);
            builder.add(<attribute:minecraft:generic.attack_damage>, newModifier, <constant:minecraft:equipmentslot/group:mainhand>);

            return inputSword.withAttributeModifiers(builder.build());
});