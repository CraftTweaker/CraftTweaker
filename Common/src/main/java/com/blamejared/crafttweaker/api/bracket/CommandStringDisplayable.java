package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This is a helper interface for every object that is returned by a BEP!
 *
 * @docParam this null
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.CommandStringDisplayable")
@Document("vanilla/api/bracket/CommandStringDisplayable")
public interface CommandStringDisplayable {
    
    /**
     * Returns the BEP to get this thingy
     */
    @ZenCodeType.Getter("commandString")
    String getCommandString();
    
}
