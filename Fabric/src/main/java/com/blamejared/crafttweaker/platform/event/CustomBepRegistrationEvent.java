package com.blamejared.crafttweaker.platform.event;

import com.blamejared.crafttweaker.api.zencode.scriptrun.IBepRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ICustomBepRegistrationEvent;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptLoadingOptionsView;
import org.openzen.zenscript.parser.BracketExpressionParser;

@SuppressWarnings("ClassCanBeRecord")
final class CustomBepRegistrationEvent implements ICustomBepRegistrationEvent {
    
    private final IScriptLoadingOptionsView view;
    private final IBepRegistrationHandler handler;
    
    CustomBepRegistrationEvent(final IScriptLoadingOptionsView view, final IBepRegistrationHandler handler) {
        
        this.view = view;
        this.handler = handler;
    }
    
    @Override
    public IScriptLoadingOptionsView currentOptions() {
        
        return this.view;
    }
    
    @Override
    public void registerBep(final String name, final BracketExpressionParser parser) {
        
        this.handler.registerBracketHandler(name, parser);
    }
    
}
