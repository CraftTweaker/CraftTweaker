package com.blamejared.crafttweaker.natives.event.block.crop;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;

@ZenRegister
@Document("vanilla/api/event/block/crop/CropGrowEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.class, zenCodeName = "crafttweaker.api.event.block.crop.CropGrowEvent")
public class ExpandCropGrowEvent {
}
