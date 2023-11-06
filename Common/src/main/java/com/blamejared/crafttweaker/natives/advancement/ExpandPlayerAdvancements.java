package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/PlayerAdvancements")
@NativeTypeRegistration(value = PlayerAdvancements.class, zenCodeName = "crafttweaker.api.advancement.PlayerAdvancements")
public class ExpandPlayerAdvancements {
    
    @ZenCodeType.Method
    public static boolean award(PlayerAdvancements internal, Advancement advancement, String criteria) {
        
        return internal.award(advancement, criteria);
    }
    
    @ZenCodeType.Method
    public static boolean revoke(PlayerAdvancements internal, Advancement advancement, String criteria) {
        
        return internal.revoke(advancement, criteria);
    }
    
    @ZenCodeType.Method
    public static void flushDirty(PlayerAdvancements internal, ServerPlayer player) {
        
        internal.flushDirty(player);
    }
    
    @ZenCodeType.Method
    public static AdvancementProgress getOrStartProgress(PlayerAdvancements internal, Advancement advancement) {
        
        return internal.getOrStartProgress(advancement);
    }
    
}
