package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@Document("vanilla/api/advancement/ServerAdvancementManager")
@NativeTypeRegistration(value = ServerAdvancementManager.class, zenCodeName = "crafttweaker.api.advancement.ServerAdvancementManager")
public class ExpandServerAdvancementManager {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    public static Advancement getAdvancement(ServerAdvancementManager internal, ResourceLocation id) {
        
        return internal.getAdvancement(id);
    }
    
    @ZenCodeType.Getter("allAdvancements")
    public static Collection<Advancement> getAllAdvancements(ServerAdvancementManager internal) {
        
        return internal.getAllAdvancements();
    }
    
}
