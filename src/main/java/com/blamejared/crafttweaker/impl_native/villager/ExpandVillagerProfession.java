package com.blamejared.crafttweaker.impl_native.villager;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this <profession:minecraft:fletcher>
 */
@ZenRegister
@Document("vanilla/api/villager/MCVillagerProfession")
@NativeTypeRegistration(value = VillagerProfession.class, zenCodeName = "crafttweaker.api.villager.MCVillagerProfession")
public class ExpandVillagerProfession {
    
    @ZenCodeType.Getter("name")
    @ZenCodeType.Method
    public static String getName(VillagerProfession internal) {
        
        return internal.toString();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(VillagerProfession internal) {
        
        return "<profession:" + internal.getRegistryName() + ">";
    }
    
}
