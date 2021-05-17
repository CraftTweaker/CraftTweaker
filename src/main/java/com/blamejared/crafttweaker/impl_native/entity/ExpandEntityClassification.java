package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.EntityClassification;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <entityclassification:monster>
 */
@ZenRegister
@Document("vanilla/api/entity/MCEntityClassification")
@NativeTypeRegistration(value = EntityClassification.class, zenCodeName = "crafttweaker.api.entity.MCEntityClassification")
public class ExpandEntityClassification {
    
    /**
     * Gets the name of this Entity Classification
     *
     * @return The name of this Entity Classification
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(EntityClassification internal) {
        
        return internal.getName();
    }
    
    /**
     * Gets how many Entities with this EntityClassification can be in the same area at the same time.
     *
     * @return How many Entities of this EntityClassification can be in the same area at the same time.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxNumberOfEntity")
    public static int getMaxNumberOfCreature(EntityClassification internal) {
        
        return internal.getMaxNumberOfCreature();
    }
    
    /**
     * Checks if this EntityClassification is peaceful.
     *
     * @return True if this EntityClassification is peaceful. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isPeaceful")
    public static boolean isPeacefulCreature(EntityClassification internal) {
        
        return internal.getPeacefulCreature();
    }
    
    /**
     * Checks if this EntityClassification is an animal.
     *
     * @return True if this EntityClassification is an animal. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isAnimal")
    public static boolean isAnimal(EntityClassification internal) {
        
        return internal.getAnimal();
    }
    
    /**
     * Gets the command string for this EntityClassification
     *
     * @return The command string for this EntityClassification
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EntityClassification internal) {
        
        return "<entityclassification:" + internal.getName() + ">";
    }
    
}
