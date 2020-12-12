package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.entity.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl_native.entity.ExpandEntityType;
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
public class TagManagerEntityType implements TagManager<ExpandEntityType> {
    
    public static final TagManagerEntityType INSTANCE = new TagManagerEntityType();
    
    private TagManagerEntityType() {
    }
    
    @Override
    public @Nonnull
    Class<ExpandEntityType> getElementClass() {
        return ExpandEntityType.class;
    }
    
    @Override
    public String getTagFolder() {
        return "entity_types";
    }
    
    @Override
    public List<MCTag<ExpandEntityType>> getAllTagsFor(ExpandEntityType element) {
        return getTagCollection().getOwningTags(element.getInternal())
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<ExpandEntityType> to, List<ExpandEntityType> toAdd) {
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
    public void removeElements(MCTag<ExpandEntityType> from, List<ExpandEntityType> toRemove) {
        final ITag<EntityType<?>> internal = getInternal(from);
        final List<EntityType<?>> entityTypes = CraftTweakerHelper.getEntityTypes(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, entityTypes, from));
    }
    
    @Override
    public List<ExpandEntityType> getElementsInTag(MCTag<ExpandEntityType> theTag) {
        final ITag<EntityType<?>> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements()
                .stream()
                .map(ExpandEntityType::new)
                .collect(Collectors.toList());
    }
    
    @Nullable
    @Override
    public ITag<EntityType<?>> getInternal(MCTag<ExpandEntityType> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<EntityType<?>> getTagCollection() {
        return TagCollectionManager.getManager().getEntityTypeTags();
    }
}
