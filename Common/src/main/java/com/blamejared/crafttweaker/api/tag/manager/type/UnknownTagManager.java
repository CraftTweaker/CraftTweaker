package com.blamejared.crafttweaker.api.tag.manager.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagAdd;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagClear;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagCreate;
import com.blamejared.crafttweaker.api.action.tag.unknown.ActionUnknownTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MutableLoadResult;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@Document("vanilla/api/tag/manager/type/UnknownTagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.type.UnknownTagManager")
public class UnknownTagManager implements ITagManager<UnknownTag> {
    
    private final ResourceKey<? extends Registry<?>> resourceKey;
    private final MutableLoadResult<?> backingResult;
    private Map<ResourceLocation, UnknownTag> tagCache;
    
    public UnknownTagManager(ResourceKey<? extends Registry<?>> resourceKey) {
        
        this.resourceKey = resourceKey;
        this.backingResult = new MutableLoadResult<>();
        this.tagCache = new HashMap<>();
    }
    
    @Override
    public ResourceKey<? extends Registry<?>> resourceKey() {
        
        return resourceKey;
    }
    
    @ZenCodeType.Method
    public final void addId(UnknownTag to, ResourceLocation... values) {
        
        if(!exists(to)) {
            CraftTweakerAPI.apply(new ActionUnknownTagCreate(to));
        }
        CraftTweakerAPI.apply(new ActionUnknownTagAdd(to, List.of(values)));
        recalculate();
    }
    
    @ZenCodeType.Method
    public final void removeId(UnknownTag from, ResourceLocation... values) {
        
        if(!exists(from)) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        CraftTweakerAPI.apply(new ActionUnknownTagRemove(from, List.of(values)));
        recalculate();
    }
    
    @Override
    public void clear(UnknownTag from) {
        
        if(!exists(from)) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        CraftTweakerAPI.apply(new ActionUnknownTagClear(from));
        recalculate();
    }
    
    @ZenCodeType.Method
    public UnknownTag tag(String id) {
        
        return tag(new ResourceLocation(id));
    }
    
    @ZenCodeType.Method
    public UnknownTag tag(ResourceLocation id) {
        
        return tagMap().getOrDefault(id, new UnknownTag(id, this));
    }
    
    @Override
    public void recalculate() {
        
        this.tagCache = backingResult.tagMap()
                .keySet()
                .stream()
                .map(id -> Pair.of(id, new UnknownTag(id, this)))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagMap")
    public Map<ResourceLocation, UnknownTag> tagMap() {
        
        if(this.tagCache.isEmpty()) {
            this.recalculate();
        }
        return tagCache;
    }
    
    public Map<ResourceLocation, Tag<Holder<?>>> internalTags() {
        
        return GenericUtil.uncheck(Collections.unmodifiableMap(backingResult.tagMap()));
    }
    
    @Nullable
    @Override
    public Tag<Holder<?>> getInternalRaw(UnknownTag tag) {
        
        return GenericUtil.uncheck(backingResult.tagMap().get(tag.id()));
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
        
        this.backingResult.bind(GenericUtil.uncheck(result));
    }
    
}
