package com.blamejared.crafttweaker.natives.event.entity.arrow;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.ArrowNockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/arrow/ArrowNockEvent")
@NativeTypeRegistration(value = ArrowNockEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.arrow.ArrowNockEvent")
public class ExpandArrowNockEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ArrowNockEvent> BUS = IEventBus.cancelable(
            ArrowNockEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("bow")
    public static IItemStack getBow(ArrowNockEvent internal) {
        
        return IItemStack.of(internal.getBow());
    }
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(ArrowNockEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("hand")
    public static InteractionHand getHand(ArrowNockEvent internal) {
        
        return internal.getHand();
    }
    
    @ZenCodeType.Getter("ammo")
    public static boolean hasAmmo(ArrowNockEvent internal) {
        
        return internal.hasAmmo();
    }
    
    @ZenCodeType.Getter("action")
    public static InteractionResultHolder<IItemStack> getAction(ArrowNockEvent internal) {
        
        return new InteractionResultHolder<>(internal.getAction().getResult(), IItemStack.of(internal.getAction()
                .getObject()));
    }
    
    @ZenCodeType.Setter("action")
    public static void setAction(ArrowNockEvent internal, InteractionResultHolder<IItemStack> action) {
        
        internal.setAction(new InteractionResultHolder<>(action.getResult(), action.getObject().getInternal()));
    }
    
}
