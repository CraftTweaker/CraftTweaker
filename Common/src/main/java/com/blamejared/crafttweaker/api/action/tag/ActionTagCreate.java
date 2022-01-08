package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.HandleHelper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.Map;

public class ActionTagCreate<T> extends ActionTag<T> {
    
    private static final VarHandle ID_TAG_MAP = link();
    
    private final TagCollection<T> collection;
    
    public ActionTagCreate(TagCollection<T> collection, Tag<T> tag, MCTag<?> theTag) {
        
        super(tag, theTag);
        this.collection = collection;
    }
    
    private static VarHandle link() {
        
        try {
            final Class<?> type = Class.forName(TagCollection.class.getName() + "$1");
            
            return Arrays.stream(type.getDeclaredFields())
                    .filter(it -> BiMap.class.isAssignableFrom(it.getType()))
                    .findFirst()
                    .map(it -> HandleHelper.linkField(type, it.getName(), it.getType().descriptorString()))
                    .orElseThrow(NoSuchFieldException::new);
        } catch(final ReflectiveOperationException e) {
            throw new RuntimeException("Unable to identify field to link to", e);
        }
    }
    
    @Override
    public void apply() {
        
        getIdTagMap(collection).put(getId(), tag);
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(collection.getTag(getId()) != null) {
            logger.error(getType() + " Tag: " + mcTag + " already exists!");
            return false;
        }
        if(getIdTagMap(collection) instanceof ImmutableMap) {
            logger.error(getType() + " Tag Internal error: TagMap is " + collection.getAllTags()
                    .getClass()
                    .getCanonicalName());
            return false;
        }
        
        return true;
    }
    
    @Override
    public String describe() {
        
        return "Registering new " + getType() + " tag with name " + mcTag;
    }
    
    private Map<ResourceLocation, Tag<T>> getIdTagMap(final TagCollection<T> collection) {
        
        Map<ResourceLocation, Tag<T>> map = collection.getAllTags();
        if(map instanceof ImmutableBiMap<?, ?>) {
            final BiMap<ResourceLocation, Tag<T>> newMap = HashBiMap.create(map);
            map = newMap;
            ID_TAG_MAP.set(collection, newMap);
        }
        return map;
    }
    
}
