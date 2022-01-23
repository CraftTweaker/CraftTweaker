package com.blamejared.crafttweaker.api.event.type;

import net.minecraftforge.eventbus.api.Event;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.function.BiConsumer;

/**
 * Allows users to register custom implementations of {@link BracketExpressionParser}.
 *
 * <p>This event is invoked every time a new script run is started, even for syntax checks.</p>
 */
public final class CTRegisterCustomBepEvent extends Event {
    
    private final String currentLoader;
    private final BiConsumer<String, BracketExpressionParser> registerFunction;
    
    public CTRegisterCustomBepEvent(final String currentLoader, final BiConsumer<String, BracketExpressionParser> registerFunction) {
        
        this.currentLoader = currentLoader;
        this.registerFunction = registerFunction;
    }
    
    /**
     * Gets the loader for which BEPs are currently being registered for.
     */
    public String getCurrentLoader() {
        
        return this.currentLoader;
    }
    
    /**
     * Registers a new BEP.
     *
     * <p>Will not check whether a BEP with the same name already exists. In that case this parser will override the
     * present parser.</p>
     */
    public void registerBep(final String name, final BracketExpressionParser parser) {
        
        this.registerFunction.accept(name, parser);
    }
    
    /**
     * Registers a new BEP if the current loader matches the request.
     *
     * <p>Will not check whether a BEP with the same name already exists. In that case this parser will override the
     * present parser.</p>
     */
    public void registerBepIfLoader(final String loader, final String name, final BracketExpressionParser parser) {
        
        if(this.getCurrentLoader().equals(loader)) {
            this.registerBep(name, parser);
        }
    }
    
}
