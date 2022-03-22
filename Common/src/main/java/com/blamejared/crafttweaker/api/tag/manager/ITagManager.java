package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagManager;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ZenRegister
@Document("vanilla/api/tag/manager/ITagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.ITagManager")
public interface ITagManager<T extends MCTag> extends CommandStringDisplayable, Iterable<T> {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagFolder")
    default String tagFolder() {
        
        String tagDir = TagManager.getTagDir(resourceKey());
        
        // Really not ideal, but I don't see a better way, lets just hope that other mods don't be dumb and add their tags to other folders.
        if(tagDir.startsWith("tags/")) {
            tagDir = tagDir.substring("tags/".length());
        }
        return tagDir;
    }
    
    @ZenCodeType.Method
    T tag(String id);
    
    @ZenCodeType.Method
    T tag(ResourceLocation id);
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagMap")
    Map<ResourceLocation, T> tagMap();
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(String id) {
        
        return exists(new ResourceLocation(id));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(ResourceLocation id) {
        
        return tagKeys().contains(id);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(T tag) {
        
        return exists(tag.id());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagKeys")
    List<ResourceLocation> tagKeys();
    
    @Override
    default String getCommandString() {
        
        return "<tagmanager:" + tagFolder() + ">";
    }
    
    <U> void addTag(ResourceLocation id, Tag<Holder<U>> tag);
    
    Map<ResourceLocation, Tag<Holder<?>>> internalTags();
    
    void bind(TagManager.LoadResult<?> result);
    
    default Optional<Class<?>> elementClass() {
        return Optional.empty();
    }
    
    List<?> elements(T of);
    
    ResourceKey<? extends Registry<?>> resourceKey();
    
    void recalculate();
    
    @Nonnull
    @Override
    default Iterator<T> iterator() {
        
        return tags().iterator();
    }
    
    List<T> tags();
    
    @ZenCodeType.Method
    List<T> getTagsFor(ResourceLocation element);
    
}
