package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
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
    
    public TagManagerWrapper(Class<T> elementClass, ResourceLocation tagTypeName, MethodHandle unwrapperHandle, MethodHandle wrapperHandle) {
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
    
    private ITagCollection<?> getTagCollection() {
        final Map<ResourceLocation, ITagCollection<?>> customTagTypes = ForgeTagHandler.getCustomTagTypes();
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
    public boolean exists(MCResourceLocation location) {
        return getTagCollection().get(location.getInternal()) != null;
    }
    
    @Override
    public List<MCTag<T>> getAllTags() {
        return getTagCollection().getIDTagMap()
                .keySet()
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
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
    public void addElements(MCTag<T> to, List<T> toAdd) {
        final ITag<?> internal = getInternal(to);
        if(internal == null) {
            //TODO: Create tag
            return;
        }
        
        CraftTweakerAPI.apply(new ActionTagAdd(internal, convertEntries(toAdd), to.getIdInternal()));
    }
    
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void removeElements(MCTag<T> from, List<T> toRemove) {
        final ITag<?> internal = getInternal(from);
        if(internal == null) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        
        CraftTweakerAPI.apply(new ActionTagRemove(internal, convertEntries(toRemove), from.getIdInternal()));
    }
    
    @SuppressWarnings({"rawtypes"})
    private ForgeRegistryEntry[] convertEntries(List<T> list) {
        return list.stream().map(t -> {
            try {
                return (ForgeRegistryEntry) unwrapperHandle.invoke(t);
            } catch(Throwable throwable) {
                CraftTweakerAPI.logThrowing("Could not convert element for synthetic TagManager: ", throwable);
                return null;
            }
        }).filter(Objects::nonNull).toArray(ForgeRegistryEntry[]::new);
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
    
    @Nullable
    @Override
    public ITag<?> getInternal(MCTag<T> theTag) {
        return getTagCollection().get(theTag.getIdInternal());
    }
}
