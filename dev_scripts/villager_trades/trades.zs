import crafttweaker.api.villager.trade.type.SuspiciousStewForEmerald;
import crafttweaker.api.item.component.SuspiciousStewEffects;
import crafttweaker.api.item.component.SuspiciousStewEffectsEntry;
villagerTrades.addTrade(<profession:minecraft:farmer>, 1, SuspiciousStewForEmerald.of(SuspiciousStewEffects.of([SuspiciousStewEffectsEntry.of(<mobeffect:minecraft:haste>, 300)]), 1, 0.05));
villagerTrades.addTrade(<profession:minecraft:farmer>, 1, <item:minecraft:diamond> * 2, <item:minecraft:emerald> * 2, 5, 2, 0.05);

// Weaponsmiths will no longer offer an iron axe for emeralds
villagerTrades.removeItemsForEmeraldsTrade(<profession:minecraft:weaponsmith>, 1, <item:minecraft:iron_axe>);