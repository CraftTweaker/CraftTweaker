package com.blamejared.crafttweaker.natives.command;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.commands.CommandSource;
import net.minecraft.network.chat.Component;
import org.openzen.zencode.java.ZenCodeType;

import java.util.UUID;

@ZenRegister
@Document("vanilla/api/command/CommandSource")
@NativeTypeRegistration(value = CommandSource.class, zenCodeName = "crafttweaker.api.command.CommandSource")
public class ExpandCommandSource {
    
    @ZenCodeType.Method
    public static void sendMessage(CommandSource internal, Component component) {
        
        internal.sendSystemMessage(component);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("acceptsSuccess")
    public static boolean acceptsSuccess(CommandSource internal) {
        
        return internal.acceptsSuccess();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("acceptsFailure")
    public static boolean acceptsFailure(CommandSource internal) {
        
        return internal.acceptsFailure();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldInformAdmins")
    public static boolean shouldInformAdmins(CommandSource internal) {
        
        return internal.shouldInformAdmins();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("alwaysAccepts")
    public static boolean alwaysAccepts(CommandSource internal) {
        
        return internal.alwaysAccepts();
    }
    
}
