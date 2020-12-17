package com.blamejared.crafttweaker_annotation_processors.processors.document.yaml;

public class YAMLFile extends YAMLObject {
    
    private final String path;
    
    public YAMLFile(String name, String path) {
        super(name);
        this.path = path;
    }
    
    
    public String getPath() {
        return path;
    }
    
    
    @Override
    public String getOutput(int subLevel) {
        StringBuilder subBuilder = new StringBuilder();
        for(int i = 0; i < subLevel; i++) {
            subBuilder.append("  ");
        }
        return subBuilder.toString() + String.format("  %s: '%s.md'%n", name, path);
    }
    
    @Override
    public String toString() {
        return String.format("YAMLFile{name='%s', path='%s'}", name, path);
    }
}
