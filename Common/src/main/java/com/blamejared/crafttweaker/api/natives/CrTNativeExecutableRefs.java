package com.blamejared.crafttweaker.api.natives;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Groups multiple {@link CrTNativeExecutableRef} objects together.
 * All signatures in here are of the same Method/Constructor name but represent different overloads
 */
class CrTNativeExecutableRefs {
    
    private final List<CrTNativeExecutableRef> signatures = new ArrayList<>();
    
    CrTNativeExecutableRefs() {}
    
    Optional<CrTNativeExecutableRef> getForSignature(Class<?>[] parameters) {
        
        return signatures.stream()
                .filter(executable -> executable.matchesParameters(parameters))
                .findAny();
    }
    
    CrTNativeExecutableRef createForSignature(Class<?>[] classes) {
        
        final Optional<CrTNativeExecutableRef> forSignature = getForSignature(classes);
        if(forSignature.isPresent()) {
            return forSignature.get();
        }
        
        final CrTNativeExecutableRef executableRef = new CrTNativeExecutableRef(classes);
        signatures.add(executableRef);
        return executableRef;
    }
    
}
