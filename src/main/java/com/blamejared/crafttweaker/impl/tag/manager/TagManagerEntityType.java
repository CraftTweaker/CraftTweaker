package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.entity.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
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
    public boolean exists(MCResourceLocation location) {
        final ITagCollection<Item> itemTags = TagCollectionManager.getManager().getItemTags();
        return itemTags.get(location.getInternal()) != null;
    }
    
    @Override
    public List<MCTag<MCEntityType>> getAllTags() {
        final ITagCollection<Item> itemTags = TagCollectionManager.getManager().getItemTags();
        return itemTags.getIDTagMap()
                .keySet()
                .stream()
                .map(itemITag -> new MCTag<>(itemITag, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getTagFolder() {
        return "entity_type";
    }
    
    @Override
    public void addElements(MCTag<MCEntityType> to, List<MCEntityType> toAdd) {
        final ITag<EntityType<?>> internal = getInternal(to);
        if(internal == null) {
            //TODO: Add tag
            return;
        }
        
        final EntityType<?>[] itemsFromDefinitions = CraftTweakerHelper.getEntityTypes(toAdd.toArray(new MCEntityType[0]));
        CraftTweakerAPI.apply(new ActionTagAdd<>(internal, itemsFromDefinitions, to.getIdInternal()));
    }
    
    @Override
    public void removeElements(MCTag<MCEntityType> from, List<MCEntityType> toRemove) {
        final ITag<EntityType<?>> internal = getInternal(from);
        final EntityType<?>[] itemsFromDefinitions = CraftTweakerHelper.getEntityTypes(toRemove.toArray(new MCEntityType[0]));
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, itemsFromDefinitions, from.getIdInternal()));
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
    
    @Override
    public ITag<EntityType<?>> getInternal(MCTag<MCEntityType> theTag) {
        final ITagCollection<EntityType<?>> blockTags = TagCollectionManager.getManager()
                .getEntityTypeTags();
        return blockTags.get(theTag.getIdInternal());
    }
}
