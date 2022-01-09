package com.blamejared.crafttweaker.natives.event.block.crop;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/block/crop/CropGrowPostEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Post.class, zenCodeName = "crafttweaker.api.event.block.crop.CropGrowPostEvent")
public class ExpandCropGrowPostEvent {
    
    @ZenCodeType.Getter("originState")
    @ZenCodeType.Method
    public static BlockState getOriginState(BlockEvent.CropGrowEvent.Post internal) {
        
        return internal.getOriginalState();
    }
    
}
