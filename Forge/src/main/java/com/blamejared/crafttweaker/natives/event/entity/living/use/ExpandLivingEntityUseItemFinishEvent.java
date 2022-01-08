package com.blamejared.crafttweaker.natives.event.entity.living.use;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fired after an item has fully finished being used.
 * The item has been notified that it was used, and the item/result stacks reflect after that state.
 * This means that when this is fired for a Potion, the potion effect has already been applied.
 *
 * If you wish to cancel those effects, you should cancel one of the above events.
 *
 * The result item stack is the stack that is placed in the player's inventory in replacement of the stack that is currently being used.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/use/LivingEntityUseItemFinishEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Finish.class, zenCodeName = "crafttweaker.api.event.entity.living.use.LivingEntityUseItemFinishEvent")
public class ExpandLivingEntityUseItemFinishEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("resultStack")
    public static IItemStack getResultStack(LivingEntityUseItemEvent.Finish internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getResultStack());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("resultStack")
    public static void setResultStack(LivingEntityUseItemEvent.Finish internal, IItemStack stack) {
        
        internal.setResultStack(stack.getInternal());
    }
    
}
