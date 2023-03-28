package com.blamejared.crafttweaker.api.bracket.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.lexer.ZSTokenType;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.expression.ParsedNewExpression;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TagBracketHandler implements BracketExpressionParser {
    
    public TagBracketHandler() {
        
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        
        if(tokens.optional(ZSTokenType.T_GREATER) != null) {
            throw new ParseException(position, "Invalid Bracket handler, expected tagFolder here");
        }
        final String tagFolder = ParseUtil.readContent(position, tokens, ZSTokenType.T_COLON);
        TagManagerBracketHandler.confirmTagFolderExists(tagFolder, position);
        
        IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
        
        final String tagName = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation resourceLocation = ResourceLocation.tryParse(tagName);
        if(resourceLocation == null) {
            throw new ParseException(position, "Invalid Tag Name '" + tagName + "', must be a valid resource location");
        }
        
        return createTagCall(position, tagFolder, resourceLocation, loader);
    }
    
    private ParsedExpression createTagCall(CodePosition position, String tagFolder, ResourceLocation location, IScriptLoader loader) throws ParseException {
        
        final ParsedExpression tagManager = TagManagerBracketHandler.createTagManagerCall(position, tagFolder, loader);
        final ParsedExpressionMember getTag = new ParsedExpressionMember(position, tagManager, "tag", null);
        final ParsedNewExpression newExpression = ParseUtil.createResourceLocationArgument(position, location);
        
        final ParsedCallArguments arguments = new ParsedCallArguments(null, Collections.singletonList(newExpression));
        return new ParsedExpressionCall(position, getTag, arguments);
    }
    
    public static Supplier<Stream<String>> getDumperData() {
        
        return () -> CraftTweakerTagRegistry.INSTANCE
                .managers()
                .stream()
                .flatMap(iTagManager -> iTagManager.tags().stream())
                .sorted(MCTag::compareTo)
                .map(MCTag::getCommandString)
                .distinct();
    }
    
}
