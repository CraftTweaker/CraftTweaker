package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.google.common.collect.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraftforge.registries.*;

import javax.annotation.*;
import java.lang.invoke.*;
import java.util.*;
import java.util.stream.*;

@ParametersAreNonnullByDefault
public class TagManagerWrapper<T extends CommandStringDisplayable> implements TagManager<T> {
    
    @Nonnull
    private final Class<T> elementClass;
    @Nonnull
    private final ResourceLocation tagTypeName;
    @Nonnull
    private final MethodHandle unwrapperHandle;
    @Nonnull
    private final MethodHandle wrapperHandle;
    
    public TagManagerWrapper(Class<T> elementClass, ResourceLocation tagTypeName, MethodHandle wrapperHandle, MethodHandle unwrapperHandle) {
        this.elementClass = elementClass;
        this.tagTypeName = tagTypeName;
        this.unwrapperHandle = unwrapperHandle;
        this.wrapperHandle = wrapperHandle;
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
        final ForgeRegistryEntry itemIn = convertEntry(element);
        if(itemIn == null) {
            return Collections.emptyList();
        }
        final Collection<ResourceLocation> owningTags = tagCollection.getOwningTags(itemIn);
        return owningTags.stream()
                .map(location -> new MCTag<T>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addElements(MCTag<T> to, List<T> toAdd) {
        final ITag<?> internal = getInternal(to);
        final List<ForgeRegistryEntry> entries = convertEntries(toAdd);
        if(internal == null) {
            final Tag<?> tagFromContents = Tag.getTagFromContents(Sets.newHashSet(entries));
            CraftTweakerAPI.apply(new ActionTagCreate(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd(internal, entries, to));
        }
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void removeElements(MCTag<T> from, List<T> toRemove) {
        final ITag<?> internal = getInternal(from);
        if(internal == null) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        
        CraftTweakerAPI.apply(new ActionTagRemove(internal, convertEntries(toRemove), from));
    }
    
    @SuppressWarnings({"rawtypes"})
    private List<ForgeRegistryEntry> convertEntries(List<T> list) {
        return list.stream()
                .map(this::convertEntry)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    @SuppressWarnings("rawtypes")
    private ForgeRegistryEntry convertEntry(T entry) {
        try {
            return (ForgeRegistryEntry) unwrapperHandle.invoke(entry);
        } catch(Throwable throwable) {
            CraftTweakerAPI.logThrowing("Could not convert element for synthetic TagManager: ", throwable);
            return null;
        }
    }
    
    @Override
    public List<T> getElementsInTag(MCTag<T> theTag) {
        final ITag<?> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements().stream().map(element -> {
            try {
                //noinspection unchecked
                return (T) wrapperHandle.invoke(element);
            } catch(Throwable throwable) {
                CraftTweakerAPI.logThrowing("Could not convert element for synthetic TagManager: ", throwable);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        
        
    }
}
