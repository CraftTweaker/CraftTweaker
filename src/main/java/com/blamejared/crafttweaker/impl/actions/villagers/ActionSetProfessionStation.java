package com.blamejared.crafttweaker.impl.actions.villagers;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlockState;
import net.minecraft.block.BlockState;
import net.minecraft.entity.merchant.villager.VillagerProfession;

import java.util.Set;
import java.util.stream.Collectors;

public class ActionSetProfessionStation implements IUndoableAction {
    private final VillagerProfession profession;
    private final Set<BlockState> oldStations;
    private final Set<BlockState> newStations;

    public ActionSetProfessionStation(VillagerProfession profession, Set<BlockState> oldStations, Set<BlockState> newStations) {
        this.profession = profession;
        this.oldStations = oldStations;
        this.newStations = newStations;
    }

    @Override
    public void apply() {
        profession.pointOfInterest.blockStates = newStations;
    }

    @Override
    public String describe() {
        String stationsCommandString = newStations.stream().map(ExpandBlockState::getCommandString).collect(Collectors.joining(", ", "[", "]"));
        return "Setting <profession:" + profession.getRegistryName() + ">'s stations to " + stationsCommandString;
    }

    @Override
    public void undo() {
        profession.pointOfInterest.blockStates = oldStations;
    }

    @Override
    public String describeUndo() {
        return "Undoing change stations for <profession:" + profession.getRegistryName() + ">";
    }
}
