package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the farmland won't turn to dirt
 */
@ZenRegister
@Document("forge/api/event/block/FarmlandTrampleEvent")
@NativeTypeRegistration(value = BlockEvent.FarmlandTrampleEvent.class, zenCodeName = "crafttweaker.api.event.block.FarmlandTrampleEvent")
public class ExpandFarmlandTrampleEvent {
    
    @ZenCodeType.Getter("entity")
    @ZenCodeType.Method
    public static Entity getEntity(BlockEvent.FarmlandTrampleEvent internal) {
        
        return internal.getEntity();
    }
    
    @ZenCodeType.Getter("fallDistance")
    @ZenCodeType.Method
    public static float getFallDistance(BlockEvent.FarmlandTrampleEvent internal) {
        
        return internal.getFallDistance();
    }
    
}
