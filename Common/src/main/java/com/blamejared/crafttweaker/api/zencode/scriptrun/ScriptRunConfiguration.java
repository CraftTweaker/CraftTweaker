package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import net.minecraft.resources.ResourceLocation;

public record ScriptRunConfiguration(
        IScriptLoader loader,
        IScriptLoadSource loadSource,
        RunKind runKind
) {
    
    public enum RunKind {
        SYNTAX_CHECK,
        FORMAT,
        EXECUTE
    }
    
    public ScriptRunConfiguration(final String loader, final ResourceLocation loadSource, final RunKind runKind) {
        
        this(IScriptLoader.find(loader), IScriptLoadSource.find(loadSource), runKind);
    }
    
    public ScriptRunConfiguration(final String loader, final String loadSource, final RunKind runKind) {
        
        this(loader, new ResourceLocation(loadSource), runKind);
    }
    
}
