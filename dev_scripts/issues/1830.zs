import crafttweaker.api.item.IItemStack;
import crafttweaker.api.loot.condition.LootConditions;
import crafttweaker.api.loot.condition.LootTableIdLootCondition;
import crafttweaker.api.loot.modifier.LootModifierManager;
import crafttweaker.api.loot.table.LootTableManager;
import crafttweaker.api.data.IData;

import stdlib.List;

// /give @s minecraft:chest[minecraft:container_loot={loot_table:"minecraft:chests/village/village_mason"}]
loot.modifiers.register(
	"minecraft_chest_village_mason",
	LootConditions.only(LootTableIdLootCondition.create(<resource:minecraft:chests/village/village_mason>)),
	(stacks, context) => {
		val random = context.random;
		var loot = new List<IItemStack>();

		loot.add(<item:minecraft:red_wool> * 64);

		val item = <item:minecraft:shears>
			.withJsonComponent(
				<componenttype:minecraft:custom_name>,
				'{"bold":true,"color":"dark_purple","italic":false,"obfuscated":false,"strikethrough":false,"text":"Shears","underlined":false}'
			);
		loot.add(item);

		return loot;
	}
);