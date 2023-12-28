package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

final class BracketParserRegistrationHandler implements IBracketParserRegistrationHandler {
    
    record BracketData(String loader, String parserName, BracketExpressionParser parser, DumperData parserDumper) {}
    
    record EnumData(String loader, ResourceLocation id, Class<? extends Enum<?>> enumClass) {}
    
    private final List<BracketData> bracketRequests;
    private final List<EnumData> enumRequests;
    
    private BracketParserRegistrationHandler() {
        
        this.bracketRequests = new ArrayList<>();
        this.enumRequests = new ArrayList<>();
    }
    
    static BracketParserRegistrationHandler of(final Consumer<IBracketParserRegistrationHandler> consumer) {
        
        final BracketParserRegistrationHandler parser = new BracketParserRegistrationHandler();
        consumer.accept(parser);
        return parser;
    }
    
    @Override
    public void registerParserFor(final String loader, final String parserName, final BracketExpressionParser parser, final DumperData parserDumper) {
        
        this.bracketRequests.add(new BracketData(loader, parserName, parser, parserDumper));
    }
    
    @Override
    public void registerParserFor(final String loader, final String parserName, final Method parser, final Method validator, final DumperData dumper) {
        
        this.registerParserFor(
                loader,
                parserName,
                new ValidatedEscapableBracketParser(parserName, parser, this.lookup(validator)),
                dumper
        );
    }
    
    @Override
    public <T extends Enum<T>> void registerEnumForBracket(final String loader, final ResourceLocation id, final Class<T> enumClass) {
        
        this.enumRequests.add(new EnumData(loader, id, enumClass));
    }
    
    List<BracketData> bracketRequests() {
        
        return Collections.unmodifiableList(this.bracketRequests);
    }
    
    List<EnumData> enumRequests() {
        
        return Collections.unmodifiableList(this.enumRequests);
    }
    
    private MethodHandle lookup(final Method method) {
        
        if(method == null) {
            return null;
        }
        
        try {
            return MethodHandles.publicLookup().unreflect(method);
        } catch(final IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid validator specified", e);
        }
    }
    
}
