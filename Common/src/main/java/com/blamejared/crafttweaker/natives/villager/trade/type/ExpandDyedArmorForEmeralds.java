package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;

@ZenRegister
@Document("vanilla/api/villager/trade/type/DyedArmorForEmeralds")
@NativeTypeRegistration(value = VillagerTrades.DyedArmorForEmeralds.class, zenCodeName = "crafttweaker.api.villager.trade.type.DyedArmorForEmeralds",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = Item.class, name = "item", description = "The Item that is being sold by the villager, dyed a random color", examples = "<item:minecraft:leather_chestplate>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "value", description = "How many emeralds will this trade cost", examples = "16"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = Item.class, name = "item", description = "The Item that is being sold by the villager, dyed a random color", examples = "<item:minecraft:leather_chestplate>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "value", description = "How many emeralds will this trade cost", examples = "16"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                }),
        })
public class ExpandDyedArmorForEmeralds {
}
