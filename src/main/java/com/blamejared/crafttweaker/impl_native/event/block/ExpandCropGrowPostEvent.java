package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */
@ZenRegister
@Document("vanilla/api/event/block/CropGrowPostEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Post.class, zenCodeName = "crafttweaker.api.event.block.CropGrowPostEvent")
public class ExpandCropGrowPostEvent {
    @ZenCodeType.Getter("originState")
    @ZenCodeType.Method
    public static BlockState getOriginState(BlockEvent.CropGrowEvent.Post internal) {
        return internal.getOriginalState();
    }
}
