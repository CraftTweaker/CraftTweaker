package com.blamejared.crafttweaker.impl.brackets.tags;

import com.blamejared.crafttweaker.impl.brackets.util.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.util.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.lexer.*;
import org.openzen.zenscript.parser.*;
import org.openzen.zenscript.parser.expression.*;

import java.util.*;

public class TagBracketHandler implements BracketExpressionParser {
    
    private final TagManagerBracketHandler tagManagerBracketHandler;
    
    public TagBracketHandler(TagManagerBracketHandler tagManagerBracketHandler) {
        this.tagManagerBracketHandler = tagManagerBracketHandler;
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        if(tokens.optional(ZSTokenType.T_GREATER) != null) {
            throw new IllegalArgumentException("Invalid Bracket handler, expected tagFolder here");
        }
        final String tagFolder = tokens.next().getContent();
        tagManagerBracketHandler.confirmTagFolderExists(tagFolder);
        
        tokens.required(ZSTokenType.T_COLON, "Expected ':', followed by Tag Name");
        
        final String tagName = ParseUtil.readContent(tokens);
        final ResourceLocation resourceLocation = ResourceLocation.tryCreate(tagName);
        if(resourceLocation == null) {
            throw new IllegalArgumentException("Invalid Tag Name '" + tagName + "', must be a valid resource location");
        }
        
        return createCall(position, tagFolder, resourceLocation);
        
    }
    
    private ParsedExpression createCall(CodePosition position, String tagFolder, ResourceLocation location) {
        final ParsedExpression tagManager = tagManagerBracketHandler.getParsedExpression(position, tagFolder);
        final ParsedExpressionMember getTag = new ParsedExpressionMember(position, tagManager, "getTag", null);
        final ParsedNewExpression newExpression = createMCResourceLocationArgument(position, location);
    
        final ParsedCallArguments arguments = new ParsedCallArguments(null, Collections.singletonList(newExpression));
        return new ParsedExpressionCall(position, getTag, arguments);
    }
    
    private ParsedNewExpression createMCResourceLocationArgument(CodePosition position, ResourceLocation location) {
        final List<ParsedExpression> arguments = new ArrayList<>(2);
        arguments.add(new ParsedExpressionString(position, location.getNamespace(), false));
        arguments.add(new ParsedExpressionString(position, location.getPath(), false));
        final ParsedCallArguments newCallArguments = new ParsedCallArguments(null, arguments);
        return new ParsedNewExpression(position, ParseUtil.readParsedType(MCResourceLocation.ZC_CLASS_NAME, position), newCallArguments);
    }
}
