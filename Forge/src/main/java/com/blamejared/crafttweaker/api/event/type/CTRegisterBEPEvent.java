package com.blamejared.crafttweaker.api.event.type;

import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Set;

/**
 * Allows users to register custom implementations of {@link BracketExpressionParser}.
 * This event is invoked every time a new script run is started, even for syntax checks.
 * <p>
 * This event is invoked very early in the load cycle, so {@link #getKnownBEPNames()} won't contain BEPs added by the {@link com.blamejared.crafttweaker.api.annotation.BracketResolver} annotation.
 *
 * @deprecated Use {@link CTRegisterCustomBepEvent}
 */
@Deprecated(forRemoval = true)
public class CTRegisterBEPEvent extends Event {
    
    private final IgnorePrefixCasingBracketParser bep;
    
    public CTRegisterBEPEvent(IgnorePrefixCasingBracketParser bep) {
        
        this.bep = bep;
    }
    
    /**
     * Gets all currently registered BEPs.
     */
    public Set<String> getKnownBEPNames() {
        
        return bep.getSubParsersName();
    }
    
    /**
     * Registers a new BEP.
     * Will not check whether a BEP with the same name already exists.
     * In that case this parser will override the present parser.
     */
    public void registerBEP(String name, BracketExpressionParser parser) {
        
        bep.register(name, parser);
    }
    
}
