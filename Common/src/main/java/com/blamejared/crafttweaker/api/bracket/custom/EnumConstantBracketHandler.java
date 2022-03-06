package com.blamejared.crafttweaker.api.bracket.custom;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.natives.resource.ExpandResourceLocation;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionCast;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;
import org.openzen.zenscript.parser.expression.ParsedExpressionVariable;
import org.openzen.zenscript.parser.expression.ParsedNewExpression;
import org.openzen.zenscript.parser.type.IParsedType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.EnumConstantBracketHandler")
public class EnumConstantBracketHandler implements BracketExpressionParser {
    
    public EnumConstantBracketHandler() {
    
    }
    
    public static <T extends Enum<T>> Class<T> getOrDefault(final ResourceLocation location) {
        
        return lookup(location);
    }
    
    @ZenCodeType.Method
    public static <T extends Enum<T>> Enum<T> getEnum(ResourceLocation type, String value) {
        
        return CraftTweakerAPI.getRegistry().getEnumBracketValue(null, type, value); // TODO("Loader")
    }
    
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        
        final String contents = ParseUtil.readContent(tokens);
        String typeStr = contents.substring(0, contents.lastIndexOf(":"));
        String name = contents.substring(typeStr.length() + 1);
        ResourceLocation type = ResourceLocation.tryParse(typeStr);
        
        if(type == null) {
            throw new ParseException(position, "Invalid ResourceLocation, expected: <constant:modid:location>");
        }
        
        return CraftTweakerAPI.getRegistry().getEnumBracketFor(CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader(), type)
                .map(it -> this.getCall(contents, it, type, name, position))
                .orElseThrow(() -> new ParseException(position, String.format("Unknown enum type <constant:%s:%s>", type, name)));
    }
    
    private <T extends Enum<T>> ParsedExpression getCall(String location, Class<T> bracketEnum, ResourceLocation type, String value, CodePosition position) {
        
        final ParsedExpressionVariable crafttweaker = new ParsedExpressionVariable(position, "crafttweaker", null);
        final ParsedExpressionMember api = new ParsedExpressionMember(position, crafttweaker, "api", Collections.emptyList());
        final ParsedExpressionMember bracket = new ParsedExpressionMember(position, api, "bracket", Collections.emptyList());
        final ParsedExpressionMember enumConstantBracketHandler = new ParsedExpressionMember(position, bracket, "EnumConstantBracketHandler", Collections.emptyList());
        final ParsedExpressionMember getRecipeManager = new ParsedExpressionMember(position, enumConstantBracketHandler, "getEnum", Collections.emptyList());
        
        final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
        final String nameContent = CraftTweakerAPI.getRegistry()
                .getZenClassRegistry()
                .getNameFor(loader, bracketEnum)
                .orElseThrow(NullPointerException::new);
        final IParsedType parsedType = ParseUtil.readParsedType(nameContent, position);
        final ParsedNewExpression rlExpression = createResourceLocationArgument(position, type);
        
        final ParsedCallArguments arguments = new ParsedCallArguments(Collections.singletonList(parsedType), List.of(rlExpression, new ParsedExpressionString(position, value, false)));
        final ParsedExpressionCall parsedExpressionCall = new ParsedExpressionCall(position, getRecipeManager, arguments);
        return new ParsedExpressionCast(position, parsedExpressionCall, parsedType, false);
    }
    
    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> Class<T> lookup(final ResourceLocation location) {
        
        return (Class<T>) CraftTweakerAPI.getRegistry()
                .getEnumBracketFor(null, location) // TODO("Loader")
                .orElseThrow(() -> new IllegalArgumentException("No such enum " + location));
    }
    
    private ParsedNewExpression createResourceLocationArgument(CodePosition position, ResourceLocation location) {
        
        final List<ParsedExpression> arguments = new ArrayList<>(2);
        arguments.add(new ParsedExpressionString(position, location.getNamespace(), false));
        arguments.add(new ParsedExpressionString(position, location.getPath(), false));
        final ParsedCallArguments newCallArguments = new ParsedCallArguments(null, arguments);
        return new ParsedNewExpression(position, ParseUtil.readParsedType(ExpandResourceLocation.ZC_CLASS_NAME, position), newCallArguments);
    }
    
    public static Supplier<Stream<String>> getDumperData() {
        
        return () -> Services.REGISTRY.recipeTypes()
                .keyStream()
                .filter(rl -> !rl.toString().equals("crafttweaker:scripts"))
                .map(rl -> String.format(Locale.ENGLISH, "<recipetype:%s>", rl));
    }
}
