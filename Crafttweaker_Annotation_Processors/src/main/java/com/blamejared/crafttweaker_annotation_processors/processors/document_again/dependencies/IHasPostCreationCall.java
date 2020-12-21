package com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies;

/**
 * Causes the {@link DependencyContainer} to call this method after the constructor has been invoked
 * Used for breaking recursive dependencies
 */
public interface IHasPostCreationCall {
    
    /**
     * The method called after the constructor of the type was invoked.
     * At this time the instance will already be in the DI registry, so anything created by this can request an instance of this type.
     */
    void afterCreation();
}
