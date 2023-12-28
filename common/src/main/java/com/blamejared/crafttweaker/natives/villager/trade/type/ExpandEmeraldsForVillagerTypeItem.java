package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/villager/trade/type/EmeraldsForVillagerTypeItem")
@NativeTypeRegistration(value = VillagerTrades.EmeraldsForVillagerTypeItem.class, zenCodeName = "crafttweaker.api.villager.trade.type.EmeraldsForVillagerTypeItem")
public class ExpandEmeraldsForVillagerTypeItem {
    
    /**
     * Creates a new EmeraldsForVillagerTypeItem trade
     *
     * @param cost       How many emeralds should the map cost
     * @param maxUses    How many times this trade can be used
     * @param villagerXp How much experience does this trade reward the villager
     * @param trades     A map of {@link VillagerType} to {@link Item} trades
     *
     * @return A new EmeraldsForVillagerTypeItem
     *
     * @docParam cost 1
     * @docParam maxUses 16
     * @docParam villagerXp 2
     * @docParam trades {<villagertype:minecraft:desert>: <item:minecraft:sand>, <villagertype:minecraft:plains>: <item:minecraft:dirt>}
     */
    @ZenCodeType.StaticExpansionMethod
    public static VillagerTrades.EmeraldsForVillagerTypeItem create(int cost, int maxUses, int villagerXp, Map<VillagerType, IItemStack> trades) {
        
        return new VillagerTrades.EmeraldsForVillagerTypeItem(cost, maxUses, villagerXp, trades.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, o -> o.getValue()
                        .getDefinition(), (o, o2) -> o.asItem())));
    }
    
}
