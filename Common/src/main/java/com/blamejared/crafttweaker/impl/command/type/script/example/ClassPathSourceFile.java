package com.blamejared.crafttweaker.impl.command.type.script.example;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.openzen.zencode.shared.SourceFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.UnknownServiceException;

public class ClassPathSourceFile implements SourceFile {
    
    private final ResourceLocation location;
    private final ResourceManager resourceManager;
    
    private ClassPathSourceFile(ResourceLocation location, ResourceManager resourceManager) {
        
        this.location = location;
        this.resourceManager = resourceManager;
    }
    
    @Nullable
    public static ClassPathSourceFile fromLocation(ResourceLocation location, ResourceManager resourceManager) {
        
        if(!resourceManager.hasResource(addPrefixAndSuffix(location))) {
            CraftTweakerAPI.LOGGER.error("Unknown script name: " + location);
            return null;
        }
        
        return new ClassPathSourceFile(location, resourceManager);
    }
    
    private static ResourceLocation addPrefixAndSuffix(ResourceLocation location) {
        
        return new ResourceLocation(location.getNamespace(), "scripts/" + location.getPath() + ".zs");
    }
    
    
    @Override
    public String getFilename() {
        
        return location.getPath() + ".zs";
    }
    
    @Override
    public Reader open() throws IOException {
        
        final Resource resource = resourceManager.getResource(addPrefixAndSuffix(location));
        return new InputStreamReader(resource.getInputStream());
    }
    
    @Override
    public void update(String content) throws IOException {
        
        throw new UnknownServiceException("Cannot write to a ClassPathSourceFile");
    }
    
}
