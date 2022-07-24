package com.blamejared.crafttweaker.gametest.framework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptTestHolder {
    
    String loader();
    
}
