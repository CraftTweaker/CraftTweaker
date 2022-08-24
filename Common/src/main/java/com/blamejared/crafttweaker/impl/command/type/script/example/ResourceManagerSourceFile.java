package com.blamejared.crafttweaker.impl.command.type.script.example;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.Reader;
import java.net.UnknownServiceException;
import java.util.Map;

final class ResourceManagerSourceFile implements SourceFile {
    
    private static final int PREFIX_LENGTH = "scripts/".length();
    
    private final ResourceLocation file;
    private final Resource resource;
    
    ResourceManagerSourceFile(ResourceLocation file, Resource resource) {
        
        this.file = file;
        this.resource = resource;
    }
    
    ResourceManagerSourceFile(Map.Entry<ResourceLocation, Resource> entry) {
        
        this(entry.getKey(), entry.getValue());
    }
    
    @Override
    public String getFilename() {
        //Skip the scripts part of the name
        final String actualName = file.getPath().substring(PREFIX_LENGTH);
        
        if(actualName.startsWith(file.getNamespace())) {
            return actualName;
        }
        
        return file.getNamespace() + '/' + actualName;
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