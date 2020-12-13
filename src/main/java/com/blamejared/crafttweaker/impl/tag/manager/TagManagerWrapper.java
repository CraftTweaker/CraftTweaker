package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.google.common.collect.Sets;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class TagManagerWrapper<T> implements TagManager<T> {
    
    @Nonnull
    private final Class<T> elementClass;
    @Nonnull
    private final ResourceLocation tagTypeName;
    
    public TagManagerWrapper(Class<T> elementClass, ResourceLocation tagTypeName) {
        this.elementClass = elementClass;
        this.tagTypeName = tagTypeName;
    }
    
    @Nonnull
    @Override
    public Class<T> getElementClass() {
        return elementClass;
    }
    
    public ITagCollection<?> getTagCollection() {
        
        final Map<ResourceLocation, ITagCollection<?>> customTagTypes = TagCollectionManager.getManager()
                .getCustomTagTypes();
        if(customTagTypes.containsKey(tagTypeName)) {
            return customTagTypes.get(tagTypeName);
        }
        
        if(TagRegistryManager.idToRegistryMap.containsKey(tagTypeName)) {
            CraftTweakerAPI.logDebug("Falling back to TagRegistryManager for collection '" + tagTypeName + '\'');
            return TagRegistryManager.idToRegistryMap.get(tagTypeName).getCollection();
        }
        
        throw new IllegalArgumentException("Could not find TagCollection for '" + tagTypeName + '\'');
    }
    
    @Override
    public String getTagFolder() {
        final Optional<String> o = Optional.ofNullable(RegistryManager.ACTIVE.getRegistry(tagTypeName))
                .map(ForgeRegistry::getTagFolder);
        if(o.isPresent()) {
            return o.get();
        } else if(tagTypeName.getNamespace().equals("minecraft")) {
            return tagTypeName.getPath();
        } else {
            //Fallback, just in case :thinking:
            return tagTypeName.toString();
        }
    }
    
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<MCTag<T>> getAllTagsFor(T element) {
        final ITagCollection tagCollection = getTagCollection();
        
        final Collection<ResourceLocation> owningTags = tagCollection.getOwningTags(element);
        return owningTags.stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addElements(MCTag<T> to, List<T> toAdd) {
        final ITag<?> internal = getInternal(to);
        if(internal == null) {
            final Tag<?> tagFromContents = Tag.getTagFromContents(Sets.newHashSet(toAdd));
            CraftTweakerAPI.apply(new ActionTagCreate(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd(internal, toAdd, to));
        }
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void removeElements(MCTag<T> from, List<T> toRemove) {
        final ITag<?> internal = getInternal(from);
        if(internal == null) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        
        CraftTweakerAPI.apply(new ActionTagRemove(internal, toRemove, from));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<T> getElementsInTag(MCTag<T> theTag) {
        final ITag<?> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return (List<T>) internal.getAllElements();
        
        
    }
}
