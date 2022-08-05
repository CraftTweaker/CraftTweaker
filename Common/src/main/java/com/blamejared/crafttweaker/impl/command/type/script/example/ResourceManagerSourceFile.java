package com.blamejared.crafttweaker.impl.command.type.script.example;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.UnknownServiceException;
import java.util.Map;

public class ResourceManagerSourceFile implements SourceFile {
    
    private final ResourceLocation file;
    private final Resource resource;
    
    ResourceManagerSourceFile(ResourceLocation file, Resource resource) {
        
        this.file = file;
        this.resource = resource;
    }
    ResourceManagerSourceFile(Map.Entry<ResourceLocation, Resource> entry){
        this(entry.getKey(), entry.getValue());
    }
    
    @Override
    public String getFilename() {
        //Skip the scripts part of the name
        return file.getPath().substring(8);
    }
    
    @Override
    public Reader open() throws IOException {
        
        return resource.openAsReader();
    }
    
    @Override
    public void update(String content) throws IOException {
        
        throw new UnknownServiceException("Cannot write to a ResourceManagerSourceFile");
    }
    
}