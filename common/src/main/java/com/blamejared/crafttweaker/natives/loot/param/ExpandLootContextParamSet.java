package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;


/**
 * A set of {@link net.minecraft.world.level.storage.loot.parameters.LootContextParam} that are required for a specific loot table to roll.
 *
 * <p>See {@link net.minecraft.world.level.storage.loot.parameters.LootContextParamSets} for a list of build-in sets.</p>
 */
@ZenRegister
@Document("vanilla/api/loot/param/LootContextParamSet")
@NativeTypeRegistration(value = LootContextParamSet.class, zenCodeName = "crafttweaker.api.loot.param.LootContextParamSet")
public class ExpandLootContextParamSet {
    // All methods in this class that could be exposed use a wildcard generic.
}
