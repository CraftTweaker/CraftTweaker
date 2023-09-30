package com.blamejared.crafttweaker.impl.loot;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.resources.ResourceLocation;

public interface ILootTableIdHolder {
    interface Mutable extends ILootTableIdHolder {
        void crafttweaker$tableId(final ResourceLocation id);
    }
    
    ResourceLocation CRAFTTWEAKER$UNKNOWN_TABLE_ID = CraftTweakerConstants.rl("table_id/unknown.table");
    
    ResourceLocation crafttweaker$tableId();
}
