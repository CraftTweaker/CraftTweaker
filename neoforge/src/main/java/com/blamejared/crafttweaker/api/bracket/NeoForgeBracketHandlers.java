package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This class contains the "simple" NeoForge Bracket handlers from CraftTweaker.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.NeoForgeBracketHandlers")
@Document("neoforge/api/NeoForgeBracketHandlers")
public final class NeoForgeBracketHandlers {
    
    @ZenCodeType.Method
    @BracketResolver("toolaction")
    public static ToolAction getToolType(String tokens) {
        
        return ToolAction.get(tokens);
    }
    
    @ZenCodeType.Method
    @BracketResolver("attachment_type")
    public static AttachmentType<?> getAttachmentType(String tokens) {
        
        return BracketHandlers.getRegistry(tokens, NeoForgeRegistries.ATTACHMENT_TYPES);
    }
    
}
