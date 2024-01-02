package com.blamejared.crafttweaker.api.bracket;


import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.natives.tool.ExpandToolAction;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.neoforged.neoforge.common.ToolAction;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.NeoForgeBracketDumpers")
@Document("neoforge/api/NeoForgeBracketDumpers")
public final class NeoForgeBracketDumpers {
    
    @ZenCodeType.StaticExpansionMethod
    @BracketDumper("toolaction")
    public static Collection<String> getToolActionDump() {
        
        return ToolAction.getActions().stream().map(ExpandToolAction::getCommandString).toList();
    }
    
}
