package com.blamejared.crafttweaker.natives.event.entity.player;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired every time the player earns an advancement. This happens
 * after the advancement has already been earned, so it can not be prevented.
 *
 * @docParam this event
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/AdvancementEvent")
@NativeTypeRegistration(value = AdvancementEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.AdvancementEvent")
public class ExpandAdvancementEvent {
    
    /**
     * Gets the advancement being unlocked.
     *
     * @return The advancement being unlocked by the player.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("advancement")
    public static Advancement getAdvancement(AdvancementEvent internal) {
        
        return internal.getAdvancement();
    }
    
}