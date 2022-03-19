package com.blamejared.crafttweaker.api.tag.manager.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.MutableLoadResult;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagManager;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tag/manager/type/KnownTagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.type.KnownTagManager")
public class KnownTagManager<T> implements ITagManager<T> {
    
    private final ResourceKey<? extends Registry<T>> resourceKey;
    private final Class<T> elementClass;
    private final MutableLoadResult<T> backingResult;
    private Map<ResourceLocation, MCTag<T>> tagCache;
    
    public KnownTagManager(ResourceKey<? extends Registry<T>> resourceKey, Class<T> elementClass) {
        
        this.resourceKey = resourceKey;
        this.elementClass = elementClass;
        this.backingResult = new MutableLoadResult<>();
        this.tagCache = new HashMap<>();
    }
    
    @Override
    public Class<T> elementClass() {
        
        return elementClass;
    }
    
    @Override
    public ResourceKey<? extends Registry<T>> resourceKey() {
        
        return resourceKey;
    }
    
    @Override
    public void recalculate() {
        
        this.tagCache = backingResult.tagMap()
                .keySet()
                .stream()
                .map(id -> Pair.of(id,new MCTag<>(id, this)))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }
    
    @Override
    public String tagFolder() {
        
        String tagDir = TagManager.getTagDir(resourceKey);
        
        // Really not ideal, but I don't see a better way, lets just hope that other mods don't be dumb and add their tags to other folders.
        if(tagDir.startsWith("tags/")) {
            tagDir = tagDir.substring("tags/".length());
        }
        return tagDir;
    }
    
    @Override
    public Map<ResourceLocation, MCTag<T>> tagMap() {
        
        if(this.tagCache.isEmpty()) {
            this.recalculate();
        }
        return tagCache;
    }
    
    @Override
    public Map<ResourceLocation, Tag<Holder<T>>> internalTags() {
        
        return Collections.unmodifiableMap(backingResult.tagMap());
    }
    
    @Override
    public Tag<Holder<T>> getInternal(MCTag<T> tag) {
        
        return backingResult.tagMap().get(tag.id());
    }
    
    @Override
    public void bind(TagManager.LoadResult<?> result) {
        
        this.backingResult.bind((TagManager.LoadResult<T>) result);
    }
    
    @Override
    public void addTag(ResourceLocation id, Tag<Holder<T>> tag) {
    
        AccessTag accessTag = (AccessTag) tag;
        accessTag.crafttweaker$setElements(new ArrayList<T>(accessTag.crafttweaker$getElements()));
        this.backingResult.addTag(id, tag);
        recalculate();
    }
    
}
