package com.blamejared.crafttweaker.impl.script.recipefs;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.HandleUtil;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.VarHandle;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RecipeFileSystemProviderInjector {
    
    private RecipeFileSystemProviderInjector() {}
    
    public static void inject(final Logger logger) {
        
        if(exists()) {
            return;
        }
        
        try {
            final VarHandle lock = HandleUtil.linkField(FileSystemProvider.class, HandleUtil.AccessType.STATIC, "lock", Object.class);
            final VarHandle installedProviders = HandleUtil.linkField(FileSystemProvider.class, HandleUtil.AccessType.STATIC, "installedProviders", List.class);
            
            while(true) {
                synchronized(lock.get()) {
                    final List<FileSystemProvider> providers = GenericUtil.uncheck((List<?>) installedProviders.getVolatile());
                    
                    final List<FileSystemProvider> newProviders = new ArrayList<>(providers);
                    newProviders.add(new RecipeFileSystemProvider());
                    
                    if(installedProviders.compareAndSet(providers, Collections.unmodifiableList(newProviders))) {
                        break;
                    }
                }
            }
            
            if(!exists()) {
                throw new RuntimeException("Unable to inject file system provider: discovered " + FileSystemProvider.installedProviders());
            } else {
                logger.info("Successfully injected RecipeFS file system");
            }
        } catch(final Throwable e) {
            throw new RuntimeException("An error occurred when attempting to inject RecipeFS file system provider", e);
        }
    }
    
    private static boolean exists() {
        
        return FileSystemProvider.installedProviders()
                .stream()
                .anyMatch(it -> RecipeFileSystemProvider.SCHEME.equals(it.getScheme()));
    }
    
}
