package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.npc.VillagerTrades;

@ZenRegister
@Document("vanilla/api/villager/trade/type/SuspiciousStewForEmerald")
@NativeTypeRegistration(value = VillagerTrades.SuspiciousStewForEmerald.class, zenCodeName = "crafttweaker.api.villager.trade.type.SuspiciousStewForEmerald",
        constructors = {
                @NativeConstructor({
                        @NativeConstructor.ConstructorParameter(type = MobEffect.class, name = "effect", description = "The mob effect that the stew will give.", examples = "<effect:minecraft:haste>"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "duration", description = "How long will the effect last in ticks", examples = "200"),
                        @NativeConstructor.ConstructorParameter(type = int.class, name = "xp", description = "How much experience does this trade reward the villager", examples = "2"),
                })
        })
public class ExpandSuspiciousStewForEmerald {
}
