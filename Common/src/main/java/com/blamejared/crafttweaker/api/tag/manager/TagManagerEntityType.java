package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.ActionTagAdd;
import com.blamejared.crafttweaker.api.action.tag.ActionTagCreate;
import com.blamejared.crafttweaker.api.action.tag.ActionTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistry;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessSetTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tag/TagManagerEntityType")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerEntityType")
public class TagManagerEntityType implements ITagManager<EntityType> {
    
    public static final TagManagerEntityType INSTANCE = new TagManagerEntityType();
    
    private TagManagerEntityType() {
    
    }
    
    @Override
    public Class<EntityType> getElementClass() {
        
        return EntityType.class;
    }
    
    @Override
    public String getTagFolder() {
        
        return "entity_types";
    }
    
    @Override
    public List<MCTag<EntityType>> getAllTagsFor(EntityType element) {
        
        return getTagCollection().getMatchingTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<EntityType> to, List<EntityType> toAdd) {
        
        final Tag<EntityType> internal = getInternal(to);
        if(internal == null) {
            final Tag<EntityType> tagFromContents = AccessSetTag.init(Sets.newHashSet(toAdd), EntityType.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
        
    }
    
    @Override
    public void removeElements(MCTag<EntityType> from, List<EntityType> toRemove) {
        
        final Tag<EntityType> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<EntityType> getElementsInTag(MCTag<EntityType> theTag) {
        
        final Tag<EntityType> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getValues();
    }
    
    @Nullable
    @Override
    public Tag<EntityType> getInternal(MCTag<EntityType> theTag) {
        
        return getTagCollection().getTag(theTag.id());
    }
    
    @Override
    public TagCollection<EntityType> getTagCollection() {
        
        // Not idea, but it works?
        return (TagCollection) CrTTagRegistry.INSTANCE.getCurrentTagContainer().get().getOrEmpty(Registry.ENTITY_TYPE_REGISTRY);
    }
    
}
