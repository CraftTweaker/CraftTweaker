package com.blamejared.crafttweaker.natives.entity.type.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.npc.AbstractVillager;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/villager/AbstractVillager")
@NativeTypeRegistration(value = AbstractVillager.class, zenCodeName = "crafttweaker.api.entity.type.villager.AbstractVillager")
public class ExpandAbstractVillager {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("unhappyCounter")
    public static int getUnhappyCounter(AbstractVillager internal) {
        
        return internal.getUnhappyCounter();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("unhappyCounter")
    public static void setUnhappyCounter(AbstractVillager internal, int unhappyCounter) {
        
        internal.setUnhappyCounter(unhappyCounter);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isTrading")
    public static boolean isTrading(AbstractVillager internal) {
        
        return internal.isTrading();
    }
    
    @ZenCodeType.Method
    public static void playCelebrateSound(AbstractVillager internal) {
        
        internal.playCelebrateSound();
    }
    
}
