import crafttweaker.api.entity.attribute.AttributeModifier;
import crafttweaker.api.item.ItemDefinition;
import crafttweaker.api.entity.attribute.AttributeOperation;
import crafttweaker.api.entity.attribute.Attribute;
import crafttweaker.api.entity.equipment.EquipmentSlotGroup;

function applyModifier(itemDef as ItemDefinition, attribute as Attribute, value as double, operation as AttributeOperation, slot as EquipmentSlotGroup) as void {
    val modifier = AttributeModifier.create(<resource:reverie:modification>, value, operation);
    itemDef.addAttributeModifier(attribute, modifier, slot);
}

applyModifier(<item:minecraft:netherite_chestplate>, <attribute:minecraft:generic.armor>, 2.0, <constant:minecraft:attribute/operation:add_value>, <constant:minecraft:equipmentslot/group:chest>);