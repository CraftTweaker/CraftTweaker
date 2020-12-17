package com.blamejared.crafttweaker_annotation_processors.processors.document.yaml;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class YAMLTypeAdapter extends TypeAdapter<YAMLFolder> {
    
    @Override
    public void write(JsonWriter out, YAMLFolder value) throws IOException {
        out.beginObject();
        for(YAMLObject yamlObject : value.getFiles()) {
            
            if(yamlObject instanceof YAMLFile) {
                YAMLFile file = (YAMLFile) yamlObject;
                out.name(file.getName()).value(file.getPath() + ".md");
            } else if(yamlObject instanceof YAMLFolder) {
                YAMLFolder folder = (YAMLFolder) yamlObject;
                out.name(folder.getName());
                write(out, folder);
            }
        }
        out.endObject();
    }
    
    @Override
    public YAMLFolder read(JsonReader in) {
        // We don't really need to read here...
        return null;
    }
}
