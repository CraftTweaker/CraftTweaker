package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import org.openzen.zencode.java.module.JavaNativeModule;

record DecoratedJavaNativeModule(JavaNativeModule module) {
    
    @Override
    public String toString() {
        
        return this.module().getModule().name;
    }
    
}
