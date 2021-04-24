package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;

@ZenRegister
@Document("vanilla/api/event/block/MCCropGrowEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.class, zenCodeName = "crafttweaker.api.event.block.MCCropGrowEvent")
public class ExpandCropGrowEvent {
}
