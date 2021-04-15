package com.blamejared.crafttweaker.impl.commands.script_examples;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.UnknownServiceException;

import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.shared.SourceFile;

public class ResourceManagerSourceFile implements SourceFile {
    
    private final ResourceLocation file;
    private final IResourceManager resourceManager;
    
    ResourceManagerSourceFile(ResourceLocation file, IResourceManager resourceManager) {
        this.file = file;
        this.resourceManager = resourceManager;
    }
    
    @Override
    public String getFilename() {
        //Skip the scripts part of the name
        return file.getPath().substring(8);
    }
    
    @Override
    public Reader open() throws IOException {
        final IResource resource = resourceManager.getResource(file);
        return new InputStreamReader(resource.getInputStream());
    }
    
    @Override
    public void update(String content) throws IOException {
        throw new UnknownServiceException("Cannot write to a ResourceManagerSourceFile");
    }
}