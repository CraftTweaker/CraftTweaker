package com.blamejared.crafttweaker.natives.loot.table;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;

/**
 * Manages all the loot tables for the server.
 *
 * <p>You can get this by calling '{@link net.minecraft.server.MinecraftServer}.lootTables'
 *
 * @docParam this server.lootTables
 */
@ZenRegister
@Document("vanilla/api/loot/LootTables")
@NativeTypeRegistration(value = LootTables.class, zenCodeName = "crafttweaker.api.loot.LootTables")
public final class ExpandLootTables {
    
    /**
     * Gets a table with the given name.
     *
     * <p>If no table is registered with the name, an empty table will be returned.</p>
     *
     * @param name The name of the table to get.
     *
     * @return The found table or an empty table if not found.
     *
     * @docParam name
     */
    @ZenCodeType.Method
    public static LootTable getTable(LootTables internal, ResourceLocation name) {
        
        return internal.get(name);
    }
    
    /**
     * Gets the ids of all registered loot tables.
     *
     * @return The ids of all registered loot tables.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("ids")
    public static Set<ResourceLocation> getIds(LootTables internal) {
        
        return internal.getIds();
    }
    
}
