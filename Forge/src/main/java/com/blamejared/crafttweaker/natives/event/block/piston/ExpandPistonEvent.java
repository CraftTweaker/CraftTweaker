package com.blamejared.crafttweaker.natives.event.block.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraftforge.event.level.PistonEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/block/piston/PistonEvent")
@NativeTypeRegistration(value = PistonEvent.class, zenCodeName = "crafttweaker.forge.api.event.block.piston.PistonEvent")
public class ExpandPistonEvent {
    
    /**
     * Gets the direction that the piston is facing.
     *
     * @return the direction tha the piston is facing.
     */
    @ZenCodeType.Getter("direction")
    public static Direction getDirection(PistonEvent internal) {
        
        return internal.getDirection();
    }
    
    /**
     * Gets the position that the piston is facing towards.
     *
     * @return The position that the piston is facing towards.
     */
    @ZenCodeType.Getter("faceOffsetPos")
    public static BlockPos getFaceOffsetPos(PistonEvent internal) {
        
        return internal.getFaceOffsetPos();
    }
    
    /**
     * Gets the move type of the piston (is it extending or retracting).
     *
     * @return The move type of the piston.
     */
    @ZenCodeType.Getter("pistonMoveType")
    public static PistonEvent.PistonMoveType getPistonMoveType(PistonEvent internal) {
        
        return internal.getPistonMoveType();
    }
    
    /**
     * Gets a **nullable** structure resolver that can be used to get all the blocks that will be affected by the piston.
     *
     * <p>Be sure to call the `resolve()` method on the structure resolver</p>
     *
     * @return A structure resolver.
     */
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("structureHelper")
    public static PistonStructureResolver getStructureHelper(PistonEvent internal) {
        
        return internal.getStructureHelper();
    }
    
    @ZenRegister
    @ZenEvent
    @Document("forge/api/event/block/piston/PrePistonEvent")
    @NativeTypeRegistration(value = PistonEvent.Pre.class, zenCodeName = "crafttweaker.forge.api.event.block.piston.PrePistonEvent")
    public static class ExpandPistonEventPreEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<PistonEvent.Pre> BUS = IEventBus.cancelable(
                PistonEvent.Pre.class,
                ForgeEventBusWire.of(),
                ForgeEventCancellationCarrier.of()
        );
        
    }
    
    @ZenRegister
    @ZenEvent
    @Document("forge/api/event/block/piston/PostPistonEvent")
    @NativeTypeRegistration(value = PistonEvent.Post.class, zenCodeName = "crafttweaker.forge.api.event.block.piston.PostPistonEvent")
    public static class ExpandPistonEventPostEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<PistonEvent.Post> BUS = IEventBus.direct(
                PistonEvent.Post.class,
                ForgeEventBusWire.of()
        );
        
    }
    
}
