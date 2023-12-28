package com.blamejared.crafttweaker.api.tag;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class MutableLoadResult<T> {
    
    private TagManager.LoadResult<T> result;
    
    public MutableLoadResult() {
    
    }
    
    public MutableLoadResult(TagManager.LoadResult<T> result) {
        
        this.result = result;
    }
    
    /**
     * Adds a tag to the {@link net.minecraft.tags.TagManager.LoadResult}
     *
     * @param id  The id of the tag to add.
     * @param tag The tag to add.
     */
    public void addTag(ResourceLocation id, Collection<Holder<T>> tag) {
        
        this.tagMap().put(id, tag);
    }
    
    /**
     * Gets the key of the {@link net.minecraft.tags.TagManager.LoadResult}
     *
     * @return The {@link ResourceKey} of the load result.
     */
    public ResourceKey<? extends Registry<T>> key() {
        
        Objects.requireNonNull(result, "Cannot get key before the result has been bound!");
        return result.key();
    }
    
    /**
     * Gets the tags of the {@link net.minecraft.tags.TagManager.LoadResult}
     *
     * @return The tags of the load result.
     */
    public Map<ResourceLocation, Collection<Holder<T>>> tagMap() {
        
        Objects.requireNonNull(result, "Cannot get tagMap before the result has been bound!");
        return result.tags();
    }
    
    /**
     * Gets the internal result.
     *
     * @return The internal result.
     */
    public TagManager.LoadResult<T> result() {
        
        Objects.requireNonNull(result, "Cannot get result before it has been bound!");
        return result;
    }
    
    /**
     * Binds the given {@link net.minecraft.tags.TagManager.LoadResult} to this object.
     *
     * <p>This will also make the elements of all tags in this result mutable.</p>
     *
     * @param result The {@link net.minecraft.tags.TagManager.LoadResult} to bind.
     */
    public void bind(TagManager.LoadResult<T> result) {
        
        if(this.result != null) {
            throw new IllegalStateException("Unable to bind a MutableLoadResult twice!");
        }
        this.result = result;
        this.tagMap().replaceAll((key, value) -> new ArrayList<>(value));
    }
    
}
