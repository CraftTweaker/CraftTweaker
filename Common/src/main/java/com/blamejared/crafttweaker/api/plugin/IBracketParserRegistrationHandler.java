package com.blamejared.crafttweaker.api.plugin;

import org.openzen.zenscript.parser.BracketExpressionParser;

public interface IBracketParserRegistrationHandler {
    
    void registerParserFor(final String loader, final String parserName, final BracketExpressionParser parser);
    
}
