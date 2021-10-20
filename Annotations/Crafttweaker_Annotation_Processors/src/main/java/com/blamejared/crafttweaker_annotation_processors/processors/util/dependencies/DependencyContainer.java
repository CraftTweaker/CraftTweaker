package com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies;

public interface DependencyContainer {
    
    <Type> Type getInstanceOfClass(Class<Type> cls);
    
    <Type> void addInstance(Type instance);
    
    <Type, Instance extends Type> void addInstanceAs(Instance instance, Class<Type> as);
    
}
