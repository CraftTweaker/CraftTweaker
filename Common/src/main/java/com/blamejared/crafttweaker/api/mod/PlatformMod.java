package com.blamejared.crafttweaker.api.mod;

import java.nio.file.Path;

public interface PlatformMod {
    Mod mod();
    Path modFile();
    Path modRoot();
    
    default String id() {
        return this.mod().id();
    }
    
    default String name() {
        return this.mod().displayName();
    }
    
    default String version() {
        return this.mod().version();
    }
    
    default Path file(final String path) {
        return this.modRoot().resolve(path);
    }
}
