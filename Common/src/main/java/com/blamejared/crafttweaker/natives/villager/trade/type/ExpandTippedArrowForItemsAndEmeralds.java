package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;

@ZenRegister
@Document("vanilla/api/villager/trade/type/TippedArrowForItemsAndEmeralds")
@NativeTypeRegistration(value = VillagerTrades.TippedArrowForItemsAndEmeralds.class, zenCodeName = "crafttweaker.api.villager.trade.type.TippedArrowForItemsAndEmeralds",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = Item.class, name = "fromItem", description = "The Item that is being sold by the villager, that will have a random potion effect attached", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "fromCount", description = "The amount of the Item being sold to the villager", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = Item.class, name = "toItem", description = "The Item that is being sold by the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "toCount", description = "The amount of the item that is being sold by the villager", examples = "1"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "emeraldCost", description = "How many emeralds will be given to the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                })
        })
public class ExpandTippedArrowForItemsAndEmeralds {
}
