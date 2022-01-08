package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the experience will not drop.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/LivingExperienceDropEvent")
@NativeTypeRegistration(value = LivingExperienceDropEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingExperienceDropEvent")
public class ExpandLivingExperienceDropEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("attackingPlayer")
    public static Player getAttackingPlayer(LivingExperienceDropEvent internal) {
        
        return internal.getAttackingPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("originalExperiencePoints")
    public static int getOriginalExperiencePoints(LivingExperienceDropEvent internal) {
        
        return internal.getOriginalExperience();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("droppedExperience")
    public static int getDroppedExperience(LivingExperienceDropEvent internal) {
        
        return internal.getDroppedExperience();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("droppedExperience")
    public static void getDroppedExperience(LivingExperienceDropEvent internal, int value) {
        
        internal.setDroppedExperience(value);
    }
    
}
