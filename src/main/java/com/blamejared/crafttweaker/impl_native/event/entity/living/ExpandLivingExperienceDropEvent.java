package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the experience will not drop.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingExperienceDropEvent")
@NativeTypeRegistration(value = LivingExperienceDropEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingExperienceDropEvent")
public class ExpandLivingExperienceDropEvent {
    
    @ZenCodeType.Getter("attackingPlayer")
    public static PlayerEntity getAttackingPlayer(LivingExperienceDropEvent internal) {
        
        return internal.getAttackingPlayer();
    }
    
    @ZenCodeType.Getter("originalExperiencePoints")
    public static int getOriginalExperiencePoints(LivingExperienceDropEvent internal) {
        
        return internal.getOriginalExperience();
    }
    
    @ZenCodeType.Getter("droppedExperience")
    public static int getDroppedExperience(LivingExperienceDropEvent internal) {
        
        return internal.getDroppedExperience();
    }
    
    @ZenCodeType.Setter("droppedExperience")
    public static void getDroppedExperience(LivingExperienceDropEvent internal, int value) {
        
        internal.setDroppedExperience(value);
    }
    
}
