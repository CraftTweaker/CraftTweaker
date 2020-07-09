package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ActionTagCreate<T extends ForgeRegistryEntry> extends ActionTag<T> {
    
    private final TagCollection<T> collection;
    
    private final String type;
    
    public ActionTagCreate(TagCollection<T> collection, String type, ITag<T> tag, ResourceLocation id) {
        super(tag, id);
        this.collection = collection;
        this.type = type;
    }
    
    @Override
    public void apply() {
        if(collection.tagMap instanceof ImmutableMap)
            collection.tagMap = HashBiMap.create(collection.tagMap);
        collection.tagMap.put(getId(), tag);
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(collection.get(getId()) != null) {
            logger.error(type + " Tag: " + getId() + " already exists!");
            return false;
        }
        return true;
    }
    
    @Override
    public String describe() {
        return "Registering new " + type + " tag with name " + new MCTag(getId());
    }
}
