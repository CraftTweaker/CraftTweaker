package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/FillBucketEvent")
@NativeTypeRegistration(value = FillBucketEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.FillBucketEvent")
public class ExpandFillBucketEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<FillBucketEvent> BUS = IEventBus.cancelable(
            FillBucketEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("emptyBucket")
    public static IItemStack getEmptyBucket(FillBucketEvent internal) {
        
        return IItemStack.of(internal.getEmptyBucket());
    }
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(FillBucketEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("target")
    public static HitResult getTarget(FillBucketEvent internal) {
        
        return internal.getTarget();
    }
    
    @ZenCodeType.Getter("filledBucket")
    public static IItemStack getFilledBucket(FillBucketEvent internal) {
        
        return IItemStack.of(internal.getFilledBucket());
    }
    
    @ZenCodeType.Setter("filledBucket")
    public static void setFilledBucket(FillBucketEvent internal, IItemStack bucket) {
        
        internal.setFilledBucket(bucket.getInternal());
    }
    
}
