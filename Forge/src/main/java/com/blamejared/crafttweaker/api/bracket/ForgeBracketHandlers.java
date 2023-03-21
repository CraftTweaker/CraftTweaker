package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.ToolAction;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This class contains the "simple" Forge Bracket handlers from CraftTweaker.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.ForgeBracketHandlers")
@Document("forge/api/BracketHandlers")
public final class ForgeBracketHandlers {

    @ZenCodeType.Method
    @BracketResolver("toolaction")
    public static ToolAction getToolType(String tokens) {

        return ToolAction.get(tokens);
    }

}
