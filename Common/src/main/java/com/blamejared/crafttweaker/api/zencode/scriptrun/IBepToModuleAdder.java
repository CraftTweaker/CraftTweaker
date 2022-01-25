package com.blamejared.crafttweaker.api.zencode.scriptrun;

import org.openzen.zencode.java.module.JavaNativeModule;

@FunctionalInterface
public interface IBepToModuleAdder {
    
    void addBepToModule(final JavaNativeModule module);
    
}
