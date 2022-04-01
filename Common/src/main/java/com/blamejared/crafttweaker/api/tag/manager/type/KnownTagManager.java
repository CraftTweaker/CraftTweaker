package com.blamejared.crafttweaker.api.tag.manager.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagAdd;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagCreate;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MutableLoadResult;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tag/manager/type/KnownTagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.type.KnownTagManager")
public class KnownTagManager<T> implements ITagManager<KnownTag<T>> {
    
    private final ResourceKey<? extends Registry<T>> resourceKey;
    private final Class<T> elementClass;
    private final MutableLoadResult<T> backingResult;
    private Map<ResourceLocation, KnownTag<T>> tagCache;
    
    public KnownTagManager(ResourceKey<? extends Registry<T>> resourceKey, Class<T> elementClass) {
        
        this.resourceKey = resourceKey;
        this.elementClass = elementClass;
        this.backingResult = new MutableLoadResult<>();
        this.tagCache = new HashMap<>();
    }
    
    public Optional<Class<?>> elementClass() {
        
        return Optional.of(elementClass);
    }
    
    @Override
    public ResourceKey<? extends Registry<T>> resourceKey() {
        
        return resourceKey;
    }
    
    @SafeVarargs
    @ZenCodeType.Method
    public final void addElements(KnownTag<T> to, T... values) {
        
        if(!exists(to)) {
            CraftTweakerAPI.apply(new ActionKnownTagCreate<>(to));
        }
        CraftTweakerAPI.apply(new ActionKnownTagAdd<>(to, List.of(values)));
        recalculate();
    }
    
    @ZenCodeType.Method
    public List<T> elements(KnownTag<T> of) {
        
        if(!exists(of)) {
            return List.of();
        }
        return getInternal(of).getValues().stream().map(Holder::value).collect(Collectors.toList());
    }
    
    @SafeVarargs
    @ZenCodeType.Method
    public final void removeElements(KnownTag<T> from, T... values) {
        
        if(!exists(from)) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        CraftTweakerAPI.apply(new ActionKnownTagRemove<>(from, List.of(values)));
        recalculate();
    }
    
    @ZenCodeType.Method
    public KnownTag<T> tag(String id) {
        
        return tag(new ResourceLocation(id));
    }
    
    @ZenCodeType.Method
    public KnownTag<T> tag(ResourceLocation id) {
        
        return tagMap().getOrDefault(id, new KnownTag<>(id, this));
    }
    
    @Override
    public KnownTag<T> tag(TagKey<?> key) {
        
        return tag(key.location());
    }
    
    @Override
    public void recalculate() {
        
        this.tagCache = backingResult.tagMap()
                .keySet()
                .stream()
                .map(id -> Pair.of(id, new KnownTag<>(id, this)))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagMap")
    public Map<ResourceLocation, KnownTag<T>> tagMap() {
        
        if(this.tagCache.isEmpty()) {
            this.recalculate();
        }
        return tagCache;
    }
    
    public Map<ResourceLocation, Tag<Holder<?>>> internalTags() {
        
        return GenericUtil.uncheck(Collections.unmodifiableMap(backingResult.tagMap()));
    }
    
    @Nullable
    public Tag<Holder<T>> getInternal(KnownTag<T> tag) {
        
        return backingResult.tagMap().get(tag.id());
    }
    
    @Override
    public List<ResourceLocation> tagKeys() {
        
        return new ArrayList<>(tagMap().keySet());
    }
    
    @Override
    public <U> void addTag(ResourceLocation id, Tag<Holder<U>> tag) {
        
        AccessTag accessTag = (AccessTag) tag;
        accessTag.crafttweaker$setElements(new ArrayList<>(accessTag.crafttweaker$getElements()));
        this.backingResult.addTag(id, GenericUtil.uncheck(tag));
        recalculate();
    }
    
    @Override
    public void bind(TagManager.LoadResult<?> result) {
        
        this.backingResult.bind((TagManager.LoadResult<T>) result);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tags")
    public List<KnownTag<T>> tags() {
        
        return new ArrayList<>(tagMap().values());
    }
    
    @Override
    public List<KnownTag<T>> getTagsFor(ResourceLocation element) {
        
        return tags().stream().filter(tag -> tag.contains(element)).toList();
    }
    
    @ZenCodeType.Method
    public List<KnownTag<T>> getTagsFor(T element) {
        
        return tags().stream().filter(tag -> tag.contains(element)).toList();
    }
    
}
