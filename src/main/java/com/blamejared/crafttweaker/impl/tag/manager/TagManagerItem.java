package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollectionManager;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tags/TagManagerItem")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerItem")
public class TagManagerItem implements TagManager<Item> {
    
    public static final TagManagerItem INSTANCE = new TagManagerItem();
    
    private TagManagerItem() {
    }
    
    @Override
    public @Nonnull
    Class<Item> getElementClass() {
        return Item.class;
    }
    
    @Override
    public String getTagFolder() {
        return "items";
    }
    
    @Override
    public List<MCTag<Item>> getAllTagsFor(Item element) {
        return getTagCollection().getOwningTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<Item> to, List<Item> toAdd) {
        final ITag<Item> internal = getInternal(to);
        if(internal == null) {
            final Tag<Item> tagFromContents = new Tag<>(Sets.newHashSet(toAdd), Item.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<Item> from, List<Item> toRemove) {
        final ITag<Item> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<Item> getElementsInTag(MCTag<Item> theTag) {
        final ITag<Item> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements();
    }
    
    @Override
    public ITag<Item> getInternal(MCTag<Item> theTag) {
        return getTagCollection().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<Item> getTagCollection() {
        return TagCollectionManager.getManager().getItemTags();
    }
}
