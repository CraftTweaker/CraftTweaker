package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.entity.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl_native.entity.MCEntityType;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.tags.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@ZenRegister
@Document("vanilla/api/tags/TagManagerEntityType")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerEntityType")
public class TagManagerEntityType implements TagManager<MCEntityType> {
    
    public static final TagManagerEntityType INSTANCE = new TagManagerEntityType();
    
    private TagManagerEntityType() {
    }
    
    @Override
    public @Nonnull
    Class<MCEntityType> getElementClass() {
        return MCEntityType.class;
    }
    
    @Override
    public String getTagFolder() {
        return "entity_types";
    }
    
    @Override
    public List<MCTag<MCEntityType>> getAllTagsFor(MCEntityType element) {
        return getTagCollection().getOwningTags(element.getInternal())
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<MCEntityType> to, List<MCEntityType> toAdd) {
        final ITag<EntityType<?>> internal = getInternal(to);
        final List<EntityType<?>> entityTypes = CraftTweakerHelper.getEntityTypes(toAdd);
        if(internal == null) {
            final Tag<EntityType<?>> tagFromContents = Tag.getTagFromContents(Sets.newHashSet(entityTypes));
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, entityTypes, to));
        }
        
    }
    
    @Override
    public void removeElements(MCTag<MCEntityType> from, List<MCEntityType> toRemove) {
        final ITag<EntityType<?>> internal = getInternal(from);
        final List<EntityType<?>> entityTypes = CraftTweakerHelper.getEntityTypes(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, entityTypes, from));
    }
    
    @Override
    public List<MCEntityType> getElementsInTag(MCTag<MCEntityType> theTag) {
        final ITag<EntityType<?>> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements()
                .stream()
                .map(MCEntityType::new)
                .collect(Collectors.toList());
    }
    
    @Nullable
    @Override
    public ITag<EntityType<?>> getInternal(MCTag<MCEntityType> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<EntityType<?>> getTagCollection() {
        return TagCollectionManager.getManager().getEntityTypeTags();
    }
}
