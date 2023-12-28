package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Clearable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/Clearable")
@NativeTypeRegistration(value = Clearable.class, zenCodeName = "crafttweaker.api.world.Clearable")
public class ExpandClearable {
    
    /**
     * Clears the contents of this Clearable.
     */
    @ZenCodeType.Method
    public static void clearContent(Clearable internal) {
        
        internal.clearContent();
    }
    
}
