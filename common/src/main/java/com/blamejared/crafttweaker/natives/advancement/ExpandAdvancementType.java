package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/AdvancementType")
@NativeTypeRegistration(value = AdvancementType.class, zenCodeName = "crafttweaker.api.advancement.AdvancementType")
@BracketEnum("minecraft:advancement/type")
public class ExpandAdvancementType {
    
    @ZenCodeType.Getter("name")
    public static String getName(AdvancementType internal) {
        
        return internal.name();
    }
    
    @ZenCodeType.Getter("chatColor")
    public static ChatFormatting getChatColor(AdvancementType internal) {
        
        return internal.getChatColor();
    }
    
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(AdvancementType internal) {
        
        return internal.getDisplayName();
    }
    
    @ZenCodeType.Method
    public static MutableComponent createAnnouncement(AdvancementType internal, AdvancementHolder advancement, ServerPlayer serverPlayer) {
        
        return internal.createAnnouncement(advancement, serverPlayer);
    }
    
}
