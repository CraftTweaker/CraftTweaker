package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;

/**
 * @author youyihj
 */
@ZenRegister
@Document("vanilla/api/event/block/CropGrowPreEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Pre.class, zenCodeName = "crafttweaker.api.event.block.CropGrowPreEvent")
public class ExpandCropGrowPreEvent {
}
