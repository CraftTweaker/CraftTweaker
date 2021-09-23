package com.blamejared.crafttweaker.impl.commands.script_examples;

import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import org.openzen.zencode.shared.SourceFile;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @deprecated Unneeded as examples will be discovered from the scripts folder in a datapack automatically now.
 */
@Deprecated
public class ExampleCollectionEvent extends Event {
    
    private final SortedSet<SourceFile> sourceFiles = new TreeSet<>(Comparator.comparing(SourceFile::getFilename));
    private final IResourceManager resourceManager;
    
    public ExampleCollectionEvent(IResourceManager resourceManager) {
        
        this.resourceManager = resourceManager;
    }
    
    public Collection<SourceFile> getSourceFiles() {
        
        return Collections.unmodifiableSet(sourceFiles);
    }
    
    public void add(SourceFile sourceFile) {
        
        sourceFiles.add(sourceFile);
    }
    
    /**
     * Adds a file to be written to when using {@code /ct examples}.
     * Will check at {@code <namespace>:scripts/<path>}
     */
    public void addResource(ResourceLocation location) {
        
        final ClassPathSourceFile sourceFile = ClassPathSourceFile.fromLocation(location, resourceManager);
        if(sourceFile != null) {
            add(sourceFile);
        }
    }
    
}
