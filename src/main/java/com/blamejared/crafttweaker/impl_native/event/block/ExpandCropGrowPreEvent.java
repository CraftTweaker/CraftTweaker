package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;

/**
 * @docEvent default it will pass on to the vanilla growth mechanics
 * @docEvent allow it will force the plant to advance a growth stage.
 * @docEvent deny it will prevent the plant from advancing a growth stage
 */
@ZenRegister
@Document("vanilla/api/event/block/CropGrowPreEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Pre.class, zenCodeName = "crafttweaker.api.event.block.CropGrowPreEvent")
public class ExpandCropGrowPreEvent {
}
