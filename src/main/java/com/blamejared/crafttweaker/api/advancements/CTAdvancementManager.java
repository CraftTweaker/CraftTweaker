package com.blamejared.crafttweaker.api.advancements;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.advancement.AdvancementManager")
public final class CTAdvancementManager {
    
    public static AdvancementManager manager;
    
    @ZenCodeGlobals.Global("advancements")
    public static final CTAdvancementManager INSTANCE = new CTAdvancementManager();
    
    private CTAdvancementManager() {}
    
    @ZenCodeType.Getter("all")
    @ZenCodeType.Method
    public Collection<Advancement> getAllAdvancements(){
        return manager.getAllAdvancements();
    }
    
}

