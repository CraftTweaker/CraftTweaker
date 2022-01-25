package com.blamejared.crafttweaker.api.event.type;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IBepRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ICustomBepRegistrationEvent;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptLoadingOptionsView;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zenscript.parser.BracketExpressionParser;

/**
 * Allows users to register custom implementations of {@link BracketExpressionParser}.
 *
 * <p>This event is invoked every time a new script run is started, even for syntax checks.</p>
 */
public final class CTCustomBepRegistrationEvent extends Event implements ICustomBepRegistrationEvent {
    
    private final IScriptLoadingOptionsView view;
    private final IBepRegistrationHandler registrationHandler;
    
    public CTCustomBepRegistrationEvent(final IScriptLoadingOptionsView view, final IBepRegistrationHandler registrationHandler) {
        
        this.view = view;
        this.registrationHandler = registrationHandler;
    }
    
    @Override
    public IScriptLoadingOptionsView currentOptions() {
        
        return this.view;
    }
    
    /**
     * Registers a new BEP.
     *
     * <p>Will not check whether a BEP with the same name already exists. In that case this parser will override the
     * present parser.</p>
     */
    @Override
    public void registerBep(final String name, final BracketExpressionParser parser) {
        
        this.registrationHandler.registerBracketHandler(name, parser);
    }
    
}
