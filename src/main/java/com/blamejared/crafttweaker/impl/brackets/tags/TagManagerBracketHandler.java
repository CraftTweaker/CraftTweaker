package com.blamejared.crafttweaker.impl.brackets.tags;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.impl.brackets.util.*;
import com.blamejared.crafttweaker.impl.tag.registry.*;
import net.minecraftforge.fml.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.lexer.*;
import org.openzen.zenscript.parser.*;
import org.openzen.zenscript.parser.expression.*;
import org.openzen.zenscript.parser.type.*;

import javax.annotation.*;
import java.util.*;

public class TagManagerBracketHandler implements BracketExpressionParser {
    
    private final CrTTagRegistryData tagRegistry;
    
    public TagManagerBracketHandler(CrTTagRegistryData tagRegistry) {
        this.tagRegistry = tagRegistry;
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        final String tagFolder = ParseUtil.readContent(tokens);
        return getParsedExpression(position, tagFolder);
    }
    
    @Nonnull
    ParsedExpression getParsedExpression(CodePosition position, String tagFolder) throws ParseException {
        confirmTagFolderExists(tagFolder, position);
        
        if(tagRegistry.isSynthetic(tagFolder)) {
            return createCallSynthetic(tagFolder, position);
        }
        return createCallImplementation(tagFolder, position);
    }
    
    void confirmTagFolderExists(String tagFolder, CodePosition position) throws ParseException {
        if(!tagRegistry.hasTagManager(tagFolder)) {
            if(ModList.get().isLoaded(tagFolder)) {
                //User _probably_ used <tag:minecraft:bedrock> instead of <tag:item:minecraft:bedrock>
                //Logging a warning hopefully reduces the amount of Issues we receive.
                CraftTweakerAPI.logWarning("Used ModID as tagFolder. The Tag BEP changed from an older version, read the changelog!");
            }
            throw new ParseException(position, "Could not find tag manager with folder '" + tagFolder + "'. Make sure it exists!");
        }
    }
    
    private ParsedExpression createCallImplementation(String tagFolder, CodePosition position) {
        final ParsedExpressionVariable tags = new ParsedExpressionVariable(position, CrTTagRegistry.GLOBAL_NAME, null);
        final ParsedExpressionMember member = new ParsedExpressionMember(position, tags, "getByImplementation", null);
        
        
        final String implementationZCTypeFor = tagRegistry.getImplementationZCTypeFor(tagFolder);
        final List<IParsedType> typeArguments = Collections.singletonList(ParseUtil.readParsedType(implementationZCTypeFor, position));
        final ParsedCallArguments arguments = new ParsedCallArguments(typeArguments, Collections.emptyList());
        
        return new ParsedExpressionCall(position, member, arguments);
    }
    
    private ParsedExpression createCallSynthetic(String tagFolder, CodePosition position) {
        final ParsedExpressionVariable tags = new ParsedExpressionVariable(position, CrTTagRegistry.GLOBAL_NAME, null);
        final ParsedExpressionMember member = new ParsedExpressionMember(position, tags, "getForElementType", null);
        final String type = tagRegistry.getElementZCTypeFor(tagFolder);
        final IParsedType elementType = ParseUtil.readParsedType(type, position);
        final List<IParsedType> typeParam = Collections.singletonList(elementType);
        
        return new ParsedExpressionCall(position, member, new ParsedCallArguments(typeParam, Collections
                .emptyList()));
    }
}
