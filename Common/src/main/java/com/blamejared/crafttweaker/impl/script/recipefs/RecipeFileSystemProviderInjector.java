package com.blamejared.crafttweaker.impl.script.recipefs;

import com.dwarveddonuts.neverwinter.fs.FileSystemInjector;

public final class RecipeFileSystemProviderInjector {
    
    private RecipeFileSystemProviderInjector() {}
    
    public static void inject() {
        
        FileSystemInjector.injectFileSystem(new RecipeFileSystemProvider());
    }
    
}
