package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.EntityClassification;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/MCEntityClassification")
@NativeTypeRegistration(value = EntityClassification.class, zenCodeName = "crafttweaker.api.entity.MCEntityClassification")
public class ExpandEntityClassification {
    
    
    @ZenCodeType.Getter("name")
    public static String getName(EntityClassification internal) {
        return internal.getName();
    }
    
    @ZenCodeType.Getter("maxNumberOfEntity")
    public static int getMaxNumberOfCreature(EntityClassification internal) {
        return internal.getMaxNumberOfCreature();
    }
    
    @ZenCodeType.Getter("isPeaceful")
    public static boolean isPeacefulCreature(EntityClassification internal) {
        return internal.getPeacefulCreature();
    }
    
    @ZenCodeType.Getter("isAnimal")
    public static boolean isAnimal(EntityClassification internal) {
        return internal.getAnimal();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EntityClassification internal) {
        return "<entityclassification:" + internal.getName() + ">";
    }
}
