package com.blamejared.crafttweaker.natives.event.block.crop;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.level.BlockEvent;

/**
 * @docEvent default the default vanilla growth mechanics will run.
 * @docEvent allow it will force the plant to advance a growth stage.
 * @docEvent deny it will prevent the plant from advancing a growth stage
 */
@ZenRegister
@Document("forge/api/event/block/crop/CropGrowPreEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Pre.class, zenCodeName = "crafttweaker.api.event.block.crop.CropGrowPreEvent")
public class ExpandCropGrowPreEvent {
}
