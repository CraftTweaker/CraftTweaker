package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventHasResult;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;

@ZenRegister
@Document("vanilla/api/event/block/CropGrowPreEvent")
@EventHasResult(
        defaultDescription = "will pass on to the vanilla growth mechanics.",
        allowDescription = "will force the plant to advance a growth stage.",
        denyDescription = "will prevent the plant from advancing a growth stage."
)
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Pre.class, zenCodeName = "crafttweaker.api.event.block.CropGrowPreEvent")
public class ExpandCropGrowPreEvent {
}
