package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
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
    public @Nonnull Class<MCItemDefinition> getElementClass() {
        return MCItemDefinition.class;
    }
    
    @Override
    public boolean exists(MCResourceLocation location) {
        final ITagCollection<Item> itemTags = TagCollectionManager.getManager().getItemTags();
        return itemTags.get(location.getInternal()) != null;
    }
    
    @Override
    public List<MCTag<MCItemDefinition>> getAllTags() {
        final ITagCollection<Item> itemTags = TagCollectionManager.getManager().getItemTags();
        return itemTags.getIDTagMap()
                .keySet()
                .stream()
                .map(itemITag -> new MCTag<>(itemITag, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getTagFolder() {
        return "item";
    }
    
    @Override
    public void addElements(MCTag<MCItemDefinition> to, List<MCItemDefinition> toAdd) {
        final ITag<Item> internal = getInternal(to);
        if(internal == null) {
            //TODO: Add tag
            return;
        }
        
        final Item[] itemsFromDefinitions = CraftTweakerHelper.getItemsFromDefinitions(toAdd);
        CraftTweakerAPI.apply(new ActionTagAdd<>(internal, itemsFromDefinitions, to.getIdInternal()));
    }
    
    @Override
    public void removeElements(MCTag<MCItemDefinition> from, List<MCItemDefinition> toRemove) {
        final ITag<Item> internal = getInternal(from);
        final Item[] itemsFromDefinitions = CraftTweakerHelper.getItemsFromDefinitions(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, itemsFromDefinitions, from.getIdInternal()));
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
        final ITagCollection<Item> itemTags = TagCollectionManager.getManager().getItemTags();
        return itemTags.get(theTag.getIdInternal());
    }
}
