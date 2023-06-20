package com.blamejared.crafttweaker.natives.event.entity.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/use/LivingEntityUseItemFinishEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Finish.class, zenCodeName = "crafttweaker.forge.api.event.entity.use.LivingEntityUseItemFinishEvent")
public class ExpandLivingEntityUseItemFinishEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingEntityUseItemEvent.Finish> BUS = IEventBus.direct(
            LivingEntityUseItemEvent.Finish.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("resultStack")
    public static IItemStack getResultStack(LivingEntityUseItemEvent.Finish internal) {
        
        return IItemStack.of(internal.getResultStack());
    }
    
    @ZenCodeType.Setter("resultStack")
    public static void setResultStack(LivingEntityUseItemEvent.Finish internal, IItemStack stack) {
        
        internal.setResultStack(stack.getInternal());
    }
    
}
