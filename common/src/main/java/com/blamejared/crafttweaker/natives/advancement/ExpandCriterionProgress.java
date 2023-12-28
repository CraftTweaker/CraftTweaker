package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.CriterionProgress;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/CriterionProgress")
@NativeTypeRegistration(value = CriterionProgress.class, zenCodeName = "crafttweaker.api.advancement.CriterionProgress")
public class ExpandCriterionProgress {
    
    @ZenCodeType.Getter("done")
    public static boolean isDone(CriterionProgress internal) {
        
        return internal.isDone();
    }
    
    @ZenCodeType.Method
    public static void grant(CriterionProgress internal) {
        
        internal.grant();
    }
    
    @ZenCodeType.Method
    public static void revoke(CriterionProgress internal) {
        
        internal.revoke();
    }
    
}
