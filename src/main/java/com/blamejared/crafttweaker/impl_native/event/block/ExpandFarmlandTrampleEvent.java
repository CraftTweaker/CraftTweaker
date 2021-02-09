package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */
@ZenRegister
@Document("vanilla/api/event/block/MCFarmlandTrampleEvent")
@NativeTypeRegistration(value = BlockEvent.FarmlandTrampleEvent.class, zenCodeName = "crafttweaker.api.event.block.MCFarmlandTrampleEvent")
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
