package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.tags.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@ZenRegister
@Document("vanilla/api/tags/TagManagerItem")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerItem")
public class TagManagerItem implements TagManager<MCItemDefinition> {
    
    public static final TagManagerItem INSTANCE = new TagManagerItem();
    
    private TagManagerItem() {
    }
    
    @Override
    public @Nonnull
    Class<MCItemDefinition> getElementClass() {
        return MCItemDefinition.class;
    }
    
    @Override
    public String getTagFolder() {
        return "items";
    }
    
    @Override
    public List<MCTag<MCItemDefinition>> getAllTagsFor(MCItemDefinition element) {
        return getTagCollection().getOwningTags(element.getInternal())
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<MCItemDefinition> to, List<MCItemDefinition> toAdd) {
        final ITag<Item> internal = getInternal(to);
        final List<Item> items = CraftTweakerHelper.getItemsFromDefinitions(toAdd);
        if(internal == null) {
            final Tag<Item> tagFromContents = Tag.getTagFromContents(Sets.newHashSet(items));
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, items, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<MCItemDefinition> from, List<MCItemDefinition> toRemove) {
        final ITag<Item> internal = getInternal(from);
        final List<Item> itemsFromDefinitions = CraftTweakerHelper.getItemsFromDefinitions(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, itemsFromDefinitions, from));
    }
    
    @Override
    public List<MCItemDefinition> getElementsInTag(MCTag<MCItemDefinition> theTag) {
        final ITag<Item> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements()
                .stream()
                .map(MCItemDefinition::new)
                .collect(Collectors.toList());
    }
    
    @Override
    public ITag<Item> getInternal(MCTag<MCItemDefinition> theTag) {
        return getTagCollection().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<Item> getTagCollection() {
        return TagCollectionManager.getManager().getItemTags();
    }
}
