package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;

import java.util.HashMap;

public class ActionTagCreate<T> extends ActionTag<T> {
    
    private final TagCollection<T> collection;
    
    private final String type;
    
    public ActionTagCreate(TagCollection<T> collection, String type, Tag<T> tag) {
        super(tag);
        this.collection = collection;
        this.type = type;
    }
    
    @Override
    public void apply() {
        if(ItemTags.getCollection().tagMap instanceof ImmutableMap)
            ItemTags.getCollection().tagMap = new HashMap<>(ItemTags.getCollection().tagMap);
        collection.tagMap.put(tag.getId(), tag);
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(collection.get(tag.getId()) != null) {
            logger.error(type + " Tag: " + tag.getId() + " already exists!");
            return false;
        }
        return true;
    }
    
    @Override
    public String describe() {
        return "Registering new " + type + " tag with name " + new MCTag(tag.getId());
    }
}
