package com.blamejared.crafttweaker.impl.command.type.script.example;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.UnknownServiceException;

public class ResourceManagerSourceFile implements SourceFile {
    
    private final ResourceLocation file;
    private final ResourceManager resourceManager;
    
    ResourceManagerSourceFile(ResourceLocation file, ResourceManager resourceManager) {
        
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
        
        final Resource resource = resourceManager.getResource(file);
        return new InputStreamReader(resource.getInputStream());
    }
    
    @Override
    public void update(String content) throws IOException {
        
        throw new UnknownServiceException("Cannot write to a ResourceManagerSourceFile");
    }
    
}