package com.blamejared.crafttweaker.api.bracket;


import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.natives.attachment.ExpandAttachmentType;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.tool.ExpandToolAction;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.NeoForgeBracketDumpers")
@Document("neoforge/api/NeoForgeBracketDumpers")
public final class NeoForgeBracketDumpers {
    
    @ZenCodeType.StaticExpansionMethod
    @BracketDumper("toolaction")
    public static Collection<String> getToolActionDump() {
        
        return ToolAction.getActions().stream().map(ExpandToolAction::getCommandString).toList();
    }
    
    @ZenCodeType.Method
    @BracketDumper("attachment_type")
    public static Collection<String> getBlockDump() {
        
        return NeoForgeRegistries.ATTACHMENT_TYPES
                .stream()
                .map(ExpandAttachmentType::getCommandString)
                .collect(Collectors.toSet());
    }
}
