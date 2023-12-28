package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.ItemLike;

@ZenRegister
@Document("vanilla/api/villager/trade/type/EmeraldForItems")
@NativeTypeRegistration(value = VillagerTrades.EmeraldForItems.class, zenCodeName = "crafttweaker.api.villager.trade.type.EmeraldForItems",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = ItemLike.class, name = "item", description = "The Item that is being given to the villager", examples = "<item:minecraft:diamond>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "cost", description = "How many emeralds does this trade cost", examples = "1"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "maxUses", description = "How many times can this trade be used", examples = "8"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "villagerXp", description = "How much experience does this trade reward the villager", examples = "2"),
                })
        })
public class ExpandEmeraldForItems {
}
