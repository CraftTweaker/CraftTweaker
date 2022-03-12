package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.ActionTagAdd;
import com.blamejared.crafttweaker.api.action.tag.ActionTagCreate;
import com.blamejared.crafttweaker.api.action.tag.ActionTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessSetTag;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SetTag;
import net.minecraft.tags.StaticTagHelper;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerWrapper")
@ParametersAreNonnullByDefault
public class TagManagerWrapper<T> implements ITagManager<T> {
    
    @Nonnull
    private final Class<T> elementClass;
    @Nonnull
    private final ResourceLocation tagTypeName;
    
    @Nonnull
    private final String tagFolder;
    
    public TagManagerWrapper(Class<T> elementClass, ResourceLocation tagTypeName, String tagFolder) {
        
        this.elementClass = elementClass;
        this.tagTypeName = tagTypeName;
        this.tagFolder = tagFolder;
    }
    
    @Nonnull
    @Override
    public Class<T> getElementClass() {
        
        return elementClass;
    }
    
    public TagCollection<T> getTagCollection() {
        
        final Map<ResourceLocation, TagCollection<?>> customTagTypes = Services.PLATFORM.getCustomTags();
        if(customTagTypes.containsKey(tagTypeName)) {
            return (TagCollection<T>) customTagTypes.get(tagTypeName);
        }
        Optional<StaticTagHelper<?>> found = Services.PLATFORM.getStaticTagHelpers()
                .stream()
                .filter(staticTagHelper -> staticTagHelper.getKey().location().equals(tagTypeName))
                .findFirst();
        
        return found.map(staticTagHelper -> {
            CraftTweakerAPI.LOGGER.debug("Falling back to StaticTags for collection '" + tagTypeName + '\'');
            return (TagCollection<T>) staticTagHelper.getAllTags();
        }).orElseThrow(() -> new IllegalArgumentException("Could not find TagCollection for '" + tagTypeName + '\''));
        
    }
    
    @Nonnull
    @Override
    public String getTagFolder() {
        
        return tagFolder;
    }
    
    @Override
    @SuppressWarnings({"rawtypes"})
    public List<MCTag<T>> getAllTagsFor(T element) {
        
        final TagCollection tagCollection = getTagCollection();
        
        final Collection<ResourceLocation> owningTags = tagCollection.getMatchingTags(element);
        return owningTags.stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    @SuppressWarnings({"rawtypes"})
    public void addElements(MCTag<T> to, List<T> toAdd) {
        
        final Tag<?> internal = getInternal(to);
        if(internal == null) {
            final SetTag<?> tagFromContents = AccessSetTag.crafttweaker$init(Sets.newHashSet(toAdd), getElementClass());
            CraftTweakerAPI.apply(new ActionTagCreate(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd(internal, toAdd, to));
        }
    }
    
    @Override
    @SuppressWarnings({"rawtypes"})
    public void removeElements(MCTag<T> from, List<T> toRemove) {
        
        final Tag<?> internal = getInternal(from);
        if(internal == null) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        
        CraftTweakerAPI.apply(new ActionTagRemove(internal, toRemove, from));
    }
    
    @Override
    public List<T> getElementsInTag(MCTag<T> theTag) {
        
        final Tag<?> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return (List<T>) internal.getValues();
        
        
    }
    
}
