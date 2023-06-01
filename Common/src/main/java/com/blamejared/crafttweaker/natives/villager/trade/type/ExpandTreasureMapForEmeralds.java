package com.blamejared.crafttweaker.natives.villager.trade.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/trade/type/TreasureMapForEmeralds")
@NativeTypeRegistration(value = VillagerTrades.TreasureMapForEmeralds.class, zenCodeName = "crafttweaker.api.villager.trade.type.TreasureMapForEmeralds")
public class ExpandTreasureMapForEmeralds {
    
    /**
     * Creates a new TreasureMapForEmeralds trade
     *
     * @param emeraldCost     How many emeralds should the map cost
     * @param destination     What should the map lead the player to
     * @param displayName     The name of the map
     * @param destinationType The type of destination t
     * @param maxUses         How many times this trade can be used
     * @param villagerXp      How much experience does this trade reward the villager
     *
     * @return A new TreasureMapForEmeralds
     *
     * @docParam emeraldCost 1
     * @docParam destination <resource:minecraft:ruined_portal>
     * @docParam displayName "Ruined Portal"
     * @docParam destinationType <constant:minecraft:world/map/decorationtype:mansion>
     * @docParam maxUses 16
     * @docParam villagerXp 2
     */
    @ZenCodeType.StaticExpansionMethod
    public static VillagerTrades.TreasureMapForEmeralds create(int emeraldCost, ResourceLocation destination, String displayName, MapDecoration.Type destinationType, int maxUses, int villagerXp) {
        
        return new VillagerTrades.TreasureMapForEmeralds(emeraldCost, TagKey.create(Registries.STRUCTURE, destination), displayName, destinationType, maxUses, villagerXp);
    }
    
}
