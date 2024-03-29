package com.blamejared.crafttweaker.api.bracket.custom;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class EnumConstantBracketHandler implements BracketExpressionParser {
    
    public EnumConstantBracketHandler() {
    
    }
    
    @Override
    public ParsedExpression parse(CodePosition position, ZSTokenParser tokens) throws ParseException {
        
        final String contents = ParseUtil.readBracketContent(position, tokens);
        String typeStr = contents.substring(0, contents.lastIndexOf(":"));
        String name = contents.substring(typeStr.length() + 1);
        ResourceLocation type = ResourceLocation.tryParse(typeStr);
        
        if(type == null) {
            throw new ParseException(position, "Invalid ResourceLocation, expected: <constant:modid:location>");
        }
        
        final AtomicReference<ParseException> exception = new AtomicReference<>(null);
        ParsedExpression parsedExpression = CraftTweakerAPI.getRegistry()
                .getEnumBracketFor(CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader(), type)
                .map(it -> {
                    try {
                        return this.getCall(contents, it, type, name, position);
                    } catch(ParseException e) {
                        if(!exception.compareAndSet(null, e)) {
                            exception.get().addSuppressed(e);
                        }
                        return null;
                    }
                })
                .orElseThrow(() -> new ParseException(position, String.format("Unknown enum type <constant:%s:%s>", type, name)));
        
        if(exception.get() != null) {
            throw exception.get();
        }
        return parsedExpression;
    }
    
    private <T extends Enum<T>> ParsedExpression getCall(String location, Class<T> bracketEnum, ResourceLocation type, String value, CodePosition position) throws ParseException {
        
        final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
        String name = CraftTweakerAPI.getRegistry()
                .getZenClassRegistry()
                .getNameFor(loader, bracketEnum)
                .orElseThrow(() -> new ParseException(position, "No class found for bracket enum: " + bracketEnum));
        
        final ParsedExpression enumClass = ParseUtil.staticMemberExpression(position, name);
        return new ParsedExpressionMember(position, enumClass, value.toUpperCase(Locale.ENGLISH), List.of());
    }
    
    public static Supplier<Stream<String>> getDumperData() {
        
        return () -> CraftTweakerAPI.getRegistry()
                .getAllLoaders()
                .stream()
                .map(CraftTweakerAPI.getRegistry()::getAllEnumStringsForEnumBracket)
                .flatMap(Collection::stream)
                .distinct();
    }
    
}
