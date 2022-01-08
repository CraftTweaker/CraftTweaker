package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.ActionTagAdd;
import com.blamejared.crafttweaker.api.action.tag.ActionTagCreate;
import com.blamejared.crafttweaker.api.action.tag.ActionTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessSetTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.SetTag;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tag/TagManagerItem")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerItem")
public class TagManagerItem implements ITagManager<Item> {
    
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
        
        return getTagCollection().getMatchingTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<Item> to, List<Item> toAdd) {
        
        final Tag<Item> internal = getInternal(to);
        if(internal == null) {
            final SetTag<Item> tagFromContents = AccessSetTag.init(Sets.newHashSet(toAdd), Item.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<Item> from, List<Item> toRemove) {
        
        final Tag<Item> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<Item> getElementsInTag(MCTag<Item> theTag) {
        
        final Tag<Item> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getValues();
    }
    
    @Override
    public Tag<Item> getInternal(MCTag<Item> theTag) {
        
        return getTagCollection().getTag(theTag.id());
    }
    
    @Override
    public TagCollection<Item> getTagCollection() {
        
        return SerializationTags.getInstance().getOrEmpty(Registry.ITEM_REGISTRY);
    }
    
}
