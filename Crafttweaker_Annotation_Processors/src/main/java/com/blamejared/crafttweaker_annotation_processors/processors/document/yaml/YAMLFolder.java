package com.blamejared.crafttweaker_annotation_processors.processors.document.yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YAMLFolder extends YAMLObject {
    
    private final List<YAMLObject> files;
    
    public YAMLFolder(String name) {
        super(name);
        this.files = new ArrayList<>();
    }
    
    @Override
    public String getOutput(int subLevel) {
        //TODO add layer stuff to get spacing right
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < subLevel; i++) {
            sb.append("  ");
        }
        sb.append("  ").append(name).append(":\n");
        for(YAMLObject file : getFiles()) {
            sb.append("  ").append(file.getOutput(subLevel + 1));
        }
        return sb.toString();
    }
    
    public YAMLFolder getOrCreate(String name) {
        // Having this be a full stream one line method was giving me errors and wasn't working as intended
        for(YAMLFolder object : files.stream()
                .filter(yamlObject -> yamlObject instanceof YAMLFolder)
                .map(yamlObject -> (YAMLFolder) yamlObject)
                .collect(Collectors.toSet())) {
            if(object.getName().equals(name)) {
                return object;
            }
        }
        return new YAMLFolder(name);
    }
    
    public boolean contains(String name) {
        
        for(YAMLFolder object : files.stream()
                .filter(yamlObject -> yamlObject instanceof YAMLFolder)
                .map(yamlObject -> (YAMLFolder) yamlObject)
                .collect(Collectors.toSet())) {
            if(object.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public void addFile(YAMLObject file) {
        if(contains(file.getName())) {
            return;
        }
        this.files.add(file);
    }
    
    public List<YAMLObject> getFiles() {
        return files;
    }
    
    @Override
    public String toString() {
        return String.format("YAMLFolder{name='%s', files=%s}", name, files);
    }
    
    
}
