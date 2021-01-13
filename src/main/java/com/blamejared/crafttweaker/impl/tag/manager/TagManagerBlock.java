package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollectionManager;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tags/TagManagerBlock")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerBlock")
public class TagManagerBlock implements TagManager<Block> {
    
    public static final TagManagerBlock INSTANCE = new TagManagerBlock();
    
    private TagManagerBlock() {
    }
    
    @Override
    public @Nonnull
    Class<Block> getElementClass() {
        return Block.class;
    }
    
    @Override
    public String getTagFolder() {
        return "blocks";
    }
    
    @Override
    public List<MCTag<Block>> getAllTagsFor(Block element) {
        return getTagCollection().getOwningTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<Block> to, List<Block> toAdd) {
        final ITag<Block> internal = getInternal(to);
        
        if(internal == null) {
            final Tag<Block> tag = new Tag<>(Sets.newHashSet(toAdd), Block.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tag, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<Block> from, List<Block> toRemove) {
        final ITag<Block> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<Block> getElementsInTag(MCTag<Block> theTag) {
        final ITag<Block> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements();
    }
    
    @Nullable
    @Override
    public ITag<Block> getInternal(MCTag<Block> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<Block> getTagCollection() {
        return TagCollectionManager.getManager().getBlockTags();
    }
}
