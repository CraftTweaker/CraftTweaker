package com.blamejared.crafttweaker.api.plugin;

import net.minecraft.resources.ResourceLocation;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface IBracketParserRegistrationHandler {
    
    record DumperData(String subCommandName, String outputFileName, Supplier<Stream<String>> data) {
        
        public DumperData(final String subCommandName, final Supplier<Stream<String>> data) {
            
            this(subCommandName, subCommandName, data);
        }
        
    }
    
    void registerParserFor(final String loader, final String parserName, final BracketExpressionParser parser, final DumperData parserDumper);
    
    default void registerParserFor(final String loader, final String parserName, final BracketExpressionParser parser) {
        
        this.registerParserFor(loader, parserName, parser, null);
    }
    
    void registerParserFor(final String loader, final String parserName, final Method parser, final Method validator, final DumperData dumper);
    
    default void registerParserFor(final String loader, final String parserName, final Method parser, final DumperData dumper) {
        
        this.registerParserFor(loader, parserName, parser, null, dumper);
    }
    
    default void registerParserFor(final String loader, final String parserName, final Method parser, final Method validator) {
        
        this.registerParserFor(loader, parserName, parser, validator, null);
    }
    
    default void registerParserFor(final String loader, final String parserName, final Method parser) {
        
        this.registerParserFor(loader, parserName, parser, (Method) null);
    }
    
    <T extends Enum<T>> void registerEnumForBracket(final String loader, final ResourceLocation id, final Class<T> enumClass);
    
}
