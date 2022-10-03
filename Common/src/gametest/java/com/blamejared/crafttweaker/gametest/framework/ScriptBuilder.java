package com.blamejared.crafttweaker.gametest.framework;



import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.google.common.annotations.VisibleForTesting;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@VisibleForTesting
public class ScriptBuilder {
    
    
    
    @VisibleForTesting
    public record Script(String name, String content) {}
    
    private List<Script> scripts;
    
    
    public ScriptBuilder() {
        
        this.scripts = new LinkedList<>();
    }
    
    public ScriptBuilder file(String path) {
    
        try {
            URL resource = this.getClass().getResource("/data/crafttweaker/gametest/scripts/" + path);
            String content = IOUtils.toString(resource.openStream(), StandardCharsets.UTF_8);
            scripts.add(new Script(path, content));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
    
    public ScriptBuilder literal(String name, String content) {
        
        scripts.add(new Script(name, content));
        return this;
    }
    
    public List<Script> build() {
        
        return Collections.unmodifiableList(this.scripts);
    }
    
}
