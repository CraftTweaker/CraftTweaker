package com.blamejared.crafttweaker.api.bracket.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
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
import org.openzen.zenscript.parser.expression.ParsedExpressionVariable;
import org.openzen.zenscript.parser.type.IParsedType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TagManagerBracketHandler implements BracketExpressionParser {
    
    public TagManagerBracketHandler() {
        
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        
        if(tokens.optional(ZSTokenType.T_GREATER) != null) {
            throw new ParseException(position, "Invalid Bracket handler, expected tagFolder here");
        }
        IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
        final String tagFolder = ParseUtil.readContent(position, tokens);
        confirmTagFolderExists(tagFolder, position);
        
        
        return createTagManagerCall(position, tagFolder, loader);
        
    }
    
    static ParsedExpression createTagManagerCall(CodePosition position, String tagFolder, IScriptLoader loader) throws ParseException {
        
        if(CraftTweakerTagRegistry.INSTANCE.isCustomManager(ResourceLocation.tryParse(tagFolder))) {
            return createCustomTagManagerCall(position, tagFolder, loader);
        }
        
        return createKnownTagManagerCall(position, tagFolder, loader);
    }
    
    @Nonnull
    static ParsedExpression createCustomTagManagerCall(CodePosition position, String tagFolder, IScriptLoader loader) throws ParseException {
        
        final ParsedExpressionVariable tags = new ParsedExpressionVariable(position, CraftTweakerTagRegistry.GLOBAL_NAME, null);
        final ParsedExpressionMember member = new ParsedExpressionMember(position, tags, "tagManager", null);
        ITagManager<?> manager = CraftTweakerTagRegistry.INSTANCE.tagManagerFromFolder(ResourceLocation.tryParse(tagFolder))
                .orElseThrow(() -> new ParseException(position, "Could not find tag manager with folder '" + tagFolder + "'. Make sure it exists!"));
        
        String managerZCName = CraftTweakerAPI.getRegistry()
                .getZenClassRegistry()
                .getNameFor(loader, manager.getClass())
                .orElseThrow(() -> new ParseException(position, "Unable to make tag for unknown class!"));
        
        String elementName = CraftTweakerAPI.getRegistry()
                .getZenClassRegistry()
                .getNameFor(loader, manager.elementClass())
                .orElseThrow(() -> new ParseException(position, "Unable to make tag for unknown class!"));
        
        final List<IParsedType> typeArguments = Collections.singletonList(ParseUtil.readParsedType(managerZCName, position));
        final ParsedCallArguments arguments = new ParsedCallArguments(typeArguments, List.of(ParseUtil.createResourceLocationArgument(position, manager.resourceKey()
                .location())));
        
        return new ParsedExpressionCall(position, member, arguments);
    }
    
    @Nonnull
    static ParsedExpression createKnownTagManagerCall(CodePosition position, String tagFolder, IScriptLoader loader) throws ParseException {
        
        final ParsedExpressionVariable tags = new ParsedExpressionVariable(position, CraftTweakerTagRegistry.GLOBAL_NAME, null);
        final ParsedExpressionMember member = new ParsedExpressionMember(position, tags, "tagManager", null);
        ITagManager<?> manager = CraftTweakerTagRegistry.INSTANCE.tagManagerFromFolder(ResourceLocation.tryParse(tagFolder))
                .orElseThrow(() -> new ParseException(position, "Could not find tag manager with folder '" + tagFolder + "'. Make sure it exists!"));
        
        String managerZCName = CraftTweakerAPI.getRegistry()
                .getZenClassRegistry()
                .getNameFor(loader, manager.getClass())
                .orElseThrow(() -> new ParseException(position, "Unable to make tag for unknown class!"));
        
        String elementName = CraftTweakerAPI.getRegistry()
                .getZenClassRegistry()
                .getNameFor(loader, manager.elementClass())
                .orElseThrow(() -> new ParseException(position, "Unable to make tag for unknown class!"));
        
        final List<IParsedType> typeArguments = Collections.singletonList(ParseUtil.readParsedType(managerZCName + "<" + elementName + ">", position));
        final ParsedCallArguments arguments = new ParsedCallArguments(typeArguments, List.of(ParseUtil.createResourceLocationArgument(position, manager.resourceKey()
                .location())));
        
        return new ParsedExpressionCall(position, member, arguments);
    }
    
    static void confirmTagFolderExists(String tagFolder, CodePosition position) throws ParseException {
        
        ResourceLocation location = ResourceLocation.tryParse(tagFolder);
        if(location == null) {
            throw new ParseException(position, "Invalid ResourceLocation '" + tagFolder + "'");
        }
        
        if(CraftTweakerTagRegistry.INSTANCE.tagManagerFromFolder(location).isEmpty()) {
            throw new ParseException(position, "Could not find tag manager with folder '" + tagFolder + "'. Make sure it exists!");
        }
    }
    
    
    public static Supplier<Stream<String>> getDumperData() {
        
        return () -> CraftTweakerTagRegistry.INSTANCE.managers()
                .stream()
                .map(ITagManager::getCommandString)
                .distinct();
    }
    
}
