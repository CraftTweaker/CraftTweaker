package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.mixin.common.access.tag.AccessTag;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MutableLoadResult<T> {
    
    private TagManager.LoadResult<T> result;
    
    public MutableLoadResult() {
    
    }
    
    public MutableLoadResult(TagManager.LoadResult<T> result) {
        
        this.result = result;
    }
    
    public void addTag(ResourceLocation id, Tag<Holder<T>> tag) {
        
        this.tagMap().put(id, tag);
    }
    
    public ResourceKey<? extends Registry<T>> key() {
        
        Objects.requireNonNull(result, "Cannot get key before the result has been bound!");
        return result.key();
    }
    
    public Map<ResourceLocation, Tag<Holder<T>>> tagMap() {
        
        Objects.requireNonNull(result, "Cannot get tagMap before the result has been bound!");
        return result.tags();
    }
    
    public TagManager.LoadResult<T> result() {
        
        Objects.requireNonNull(result, "Cannot get result before it has been bound!");
        return result;
    }
    
    public void bind(TagManager.LoadResult<T> result) {
        
        this.result = result;
        for(Tag<Holder<T>> tag : this.tagMap().values()) {
            AccessTag accessTag = (AccessTag) tag;
            accessTag.crafttweaker$setElements(new ArrayList<T>(accessTag.crafttweaker$getElements()));
        }
        
    }
    
}
