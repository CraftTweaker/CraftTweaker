package com.blamejared.crafttweaker_annotation_processors.processors.document.yaml;

public abstract class YAMLObject {
    
    protected final String name;
    
    public YAMLObject(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract String getOutput(int subLevel);
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof YAMLObject))
            return false;
        
        YAMLObject that = (YAMLObject) o;
        
        return getName().equals(that.getName());
    }
    
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
