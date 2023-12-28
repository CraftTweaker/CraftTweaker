package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;

@ZenRegister
@Document("vanilla/api/villager/trade/type/EnchantedItemForEmeralds")
@NativeTypeRegistration(value = VillagerTrades.EnchantedItemForEmeralds.class, zenCodeName = "crafttweaker.api.villager.trade.type.EnchantedItemForEmeralds",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = Item.class, name = "item", description = "The Item that is being sold by the villager", examples = "<item:minecraft:diamond_axe>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "baseEmeraldCost", description = "The base cost of the trade before any multipliers are applied", examples = "5"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = Item.class, name = "item", description = "The Item that is being sold by the villager", examples = "<item:minecraft:diamond_axe>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "baseEmeraldCost", description = "The base cost of the trade before any multipliers are applied", examples = "5"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = float.class, name = "priceMultiplier", description = "How much the price is affected by demand, Hero of the Village, and village reputation", examples = "0.05"),
                })
        })
public class ExpandEnchantedItemForEmeralds {
}
