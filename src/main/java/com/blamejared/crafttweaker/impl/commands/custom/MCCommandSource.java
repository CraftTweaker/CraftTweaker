package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCCommandSource")
@Document("vanilla/api/commands/custom/MCCommandSource")
@ZenWrapper(wrappedClass = "net.minecraft.command.CommandSource", displayStringFormat = "%s.toString()")
public class MCCommandSource {
    
    private final CommandSource internal;
    
    public MCCommandSource(CommandSource internal) {
        this.internal = internal;
    }
    
    public CommandSource getInternal() {
        return this.internal;
    }
    
    @ZenCodeType.Method
    public Collection<String> getTeamNames() {
        return internal.getTeamNames();
    }
    
    
    @ZenCodeType.Method
    public MCCommandSource withFeedbackDisabled() {
        return new MCCommandSource(internal.withFeedbackDisabled());
    }
    
    
    @ZenCodeType.Method
    public boolean hasPermissionLevel(int p_197034_1_) {
        return internal.hasPermissionLevel(p_197034_1_);
    }
    
    
    @ZenCodeType.Method
    public Collection<String> getPlayerNames() {
        return internal.getPlayerNames();
    }
    
    
    @ZenCodeType.Method
    public MCCommandSource withPermissionLevel(int p_197033_1_) {
        return new MCCommandSource(internal.withPermissionLevel(p_197033_1_));
    }
    
    
    @ZenCodeType.Method
    public String getName() {
        return internal.getName();
    }
    
    
    @ZenCodeType.Method
    public Collection<String> getTargetedEntity() {
        return internal.getTargetedEntity();
    }
    
    @ZenCodeType.Method
    public MCCommandSource withMinPermissionLevel(int p_197026_1_) {
        return new MCCommandSource(internal.withMinPermissionLevel(p_197026_1_));
    }
    
    @ZenCodeType.Method
    public boolean isPlayer() {
        return internal.getEntity() instanceof ServerPlayerEntity;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public PlayerEntity getPlayer() {
        try {
            return internal.asPlayer();
        } catch(CommandSyntaxException e) {
            CraftTweakerAPI.logError("Could not get Player: %s", e);
            return null;
        }
    }
    
    @ZenCodeType.Method
    public void sendErrorMessage(String message) {
        internal.sendErrorMessage(new StringTextComponent(message));
    }
    
    @ZenCodeType.Method
    public void sendFeedback(String feedback, boolean allowLogging) {
        internal.sendFeedback(new StringTextComponent(feedback), allowLogging);
    }
}
