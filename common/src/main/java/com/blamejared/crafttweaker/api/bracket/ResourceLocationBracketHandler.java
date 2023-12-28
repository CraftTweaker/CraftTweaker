package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Bracket handlers specifically for resource location as it is used in multiple loaders.
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@ZenCodeType.Name("crafttweaker.api.bracket.ResourceLocationBracketHandler")
@Document("vanilla/api/ResourceLocationBracketHandler")
public class ResourceLocationBracketHandler {
    
    /**
     * Creates a Resource location based on the tokens.
     * Throws an error if the tokens are not a valid location.
     *
     * @param tokens The resource location
     *
     * @return The location
     *
     * @docParam tokens "minecraft:dirt"
     */
    @ZenCodeType.Method
    @BracketResolver("resource")
    public static ResourceLocation getResourceLocation(String tokens) {
        
        return new ResourceLocation(tokens);
    }
    
}
