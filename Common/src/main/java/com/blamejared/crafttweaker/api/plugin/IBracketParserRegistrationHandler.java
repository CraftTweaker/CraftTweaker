package com.blamejared.crafttweaker.api.plugin;

import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface IBracketParserRegistrationHandler {
    
    @FunctionalInterface
    interface Creator {
        
        BracketExpressionParser createParser(final ScriptingEngine engine, final JavaNativeModule module);
        
    }
    
    record DumperData(String subCommandName, String outputFileName, Supplier<Stream<String>> data) {
        
        public DumperData(final String subCommandName, final Supplier<Stream<String>> data) {
            
            this(subCommandName, null, data);
        }
        
        public DumperData(final Supplier<Stream<String>> data) {
            
            this(null, data);
        }
        
    }
    
    void registerParserFor(final String loader, final String parserName, final Creator parserCreator, final DumperData parserDumper);
    
    default void registerParserFor(final String loader, final String parserName, final Creator parserCreator) {
        
        this.registerParserFor(loader, parserName, parserCreator, null);
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
