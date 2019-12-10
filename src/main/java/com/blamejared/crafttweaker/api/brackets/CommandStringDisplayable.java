package com.blamejared.crafttweaker.api.brackets;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This is A helper interface for every item that is returned by a BEP!
 *
 * @docParam this null
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.brackets.CommandStringDisplayable")
@Document("vanilla/brackets/CommandStringDisplayable")
public interface CommandStringDisplayable {
    /**
     * Returns the BEP to get this thingy
     */
    @ZenCodeType.Getter("commandString")
    String getCommandString();
}
