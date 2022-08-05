package com.blamejared.crafttweaker.api.tag.manager.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagAdd;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagClear;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagCreate;
import com.blamejared.crafttweaker.api.action.tag.known.ActionKnownTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MutableLoadResult;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagManager;
import net.minecraft.world.item.Items;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    
    @Override
    public void addId(KnownTag<T> to, ResourceLocation... values) {
        
        if(!exists(to)) {
            CraftTweakerAPI.apply(new ActionKnownTagCreate<>(to));
        }
        List<T> actualValues = Arrays.stream(values)
                .map(resourceLocation -> Services.REGISTRY.makeHolder(resourceKey(), resourceLocation))
                .map(Holder::value)
                .map(o -> (T) o)
                .toList();
        CraftTweakerAPI.apply(new ActionKnownTagAdd<>(to, actualValues));
        recalculate();
    }
    
    @ZenCodeType.Method
    public final void removeId(KnownTag<T> from, ResourceLocation... values) {
        
        if(!exists(from)) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        List<T> actualValues = Arrays.stream(values)
                .map(resourceLocation -> Services.REGISTRY.makeHolder(resourceKey(), resourceLocation))
                .map(Holder::value)
                .map(o -> (T) o)
                .toList();
        
        CraftTweakerAPI.apply(new ActionKnownTagRemove<>(from, actualValues));
        recalculate();
    }
    
    @Override
    public void clear(KnownTag<T> from) {
        
        if(!exists(from)) {
            throw new IllegalArgumentException("Cannot clear elements of an empty tag: " + from);
        }
        
        CraftTweakerAPI.apply(new ActionKnownTagClear<>(from));
        recalculate();
    }
    
    /**
     * Gets the elements of the given tag.
     *
     * @param of The tag to get the elements of.
     *
     * @return The list of elements in the tag.
     *
     * @docParam of <tag:items:minecraft:dirt>
     */
    @ZenCodeType.Method
    public List<T> elements(KnownTag<T> of) {
        
        if(!exists(of)) {
            return List.of();
        }
        return getInternal(of).stream().map(Holder::value).collect(Collectors.toList());
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
    
    public Map<ResourceLocation, Collection<Holder<?>>> internalTags() {
        
        return GenericUtil.uncheck(Collections.unmodifiableMap(backingResult.tagMap()));
    }
    
    @Nullable
    public Collection<Holder<T>> getInternal(KnownTag<T> tag) {
    
        Collection<Holder<T>> holderTag = backingResult.tagMap().get(tag.id());
        return backingResult.tagMap().get(tag.id());
    }
    
    @Nullable
    @Override
    public Collection<Holder<?>> getInternalRaw(KnownTag<T> tag) {
        
        return GenericUtil.uncheck(getInternal(tag));
    }
    
    @Override
    public List<ResourceLocation> tagKeys() {
        
        return new ArrayList<>(tagMap().keySet());
    }
    
    @Override
    public <U> void addTag(ResourceLocation id, Collection<Holder<U>> tag) {
    
        //TODO 1.19 confirm, this used to make the tag contents mutable, but this should only ever be a list we control.
        this.backingResult.addTag(id, GenericUtil.uncheck(tag));
        recalculate();
    }
    
    @Override
    public void bind(TagManager.LoadResult<?> result) {
        
        this.backingResult.bind((TagManager.LoadResult<T>) result);
    }
    
    @ZenCodeType.Method
    public List<KnownTag<T>> getTagsFor(T element) {
        
        return tags().stream().filter(tag -> tag.contains(element)).toList();
    }
    
}
