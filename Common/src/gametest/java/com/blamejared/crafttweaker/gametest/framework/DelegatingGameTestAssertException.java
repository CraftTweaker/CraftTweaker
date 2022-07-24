package com.blamejared.crafttweaker.gametest.framework;

import net.minecraft.gametest.framework.GameTestAssertException;

public class DelegatingGameTestAssertException extends GameTestAssertException {
    
    public DelegatingGameTestAssertException(String exceptionMessage, Throwable cause) {
        
        super(exceptionMessage);
        this.initCause(cause);
    }
    
}
