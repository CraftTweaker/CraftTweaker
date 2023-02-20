package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

@ZenRegister
@Document("vanilla/api/villager/trade/type/ItemsForEmeralds")
@NativeTypeRegistration(value = VillagerTrades.ItemsForEmeralds.class, zenCodeName = "crafttweaker.api.villager.trade.type.ItemsForEmeralds",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = Block.class, name = "block", description = "The Block that is being sold by the villager", examples = "<block:minecraft:dirt>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "emeraldCost", description = "How many emeralds will be given to the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "numberOfItems", description = "The amount of the Block being sold to the villager", examples = "4"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = Item.class, name = "item", description = "The Item that is being sold by the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "emeraldCost", description = "How many emeralds will be given to the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "numberOfItems", description = "The amount of the Item being sold to the villager", examples = "4"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "itemStack", description = "The Item that is being sold by the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "emeraldCost", description = "How many emeralds will be given to the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "numberOfItems", description = "The amount of the Item being sold to the villager", examples = "4"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                }),
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemStack.class, name = "itemStack", description = "The Item that is being sold by the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "emeraldCost", description = "How many emeralds will be given to the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "numberOfItems", description = "The amount of the Item being sold to the villager", examples = "4"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                        @NativeConstructor.ConstructorParameter(type = float.class, name = "priceMultiplier", description = "How much the price is affected by demand, Hero of the Village, and village reputation", examples = "0.05"),
                })
        })
public class ExpandItemsForEmeralds {
}
