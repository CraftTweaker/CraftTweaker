package com.blamejared.crafttweaker.impl_native.villager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.villagers.ActionSetProfessionStation;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import org.openzen.zencode.java.ZenCodeType;

/**
 *
 * @docParam this <profession:minecraft:fletcher>
 */
@ZenRegister
@Document("vanilla/api/villager/MCVillagerProfession")
@NativeTypeRegistration(value = VillagerProfession.class, zenCodeName = "crafttweaker.api.villager.MCVillagerProfession")
public class ExpandVillagerProfession {
    
    @ZenCodeType.Getter("name")
    @ZenCodeType.Method
    public static String getName(VillagerProfession internal) {
        return internal.toString();
    }

    /**
     * Sets villager profession's station
     *
     * @param stations The block states to set. Can be one or more blocks.
     * @docParam stations <blockstate:minecraft:crafting_table>
     */
    @ZenCodeType.Method
    public static void setStations(VillagerProfession internal, BlockState... stations) {
        CraftTweakerAPI.apply(new ActionSetProfessionStation(internal, internal.pointOfInterest.blockStates, Sets.newHashSet(stations)));
    }
}
