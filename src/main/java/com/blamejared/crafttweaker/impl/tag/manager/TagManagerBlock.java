package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.tags.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@ZenRegister
@Document("vanilla/api/tags/TagManagerBlock")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerBlock")
public class TagManagerBlock implements TagManager<MCBlock> {
    
    public static final TagManagerBlock INSTANCE = new TagManagerBlock();
    
    private TagManagerBlock() {
    }
    
    @Override
    public @Nonnull
    Class<MCBlock> getElementClass() {
        return MCBlock.class;
    }
    
    @Override
    public boolean exists(MCResourceLocation location) {
        final ITagCollection<Item> itemTags = TagCollectionManager.getManager().getItemTags();
        return itemTags.get(location.getInternal()) != null;
    }
    
    @Override
    public List<MCTag<MCBlock>> getAllTags() {
        final ITagCollection<Item> itemTags = TagCollectionManager.getManager().getItemTags();
        return itemTags.getIDTagMap()
                .keySet()
                .stream()
                .map(itemITag -> new MCTag<>(itemITag, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getTagFolder() {
        return "block";
    }
    
    @Override
    public void addElements(MCTag<MCBlock> to, List<MCBlock> toAdd) {
        final ITag<Block> internal = getInternal(to);
        if(internal == null) {
            //TODO: Add tag
            return;
        }
        
        final Block[] itemsFromDefinitions = CraftTweakerHelper.getBlocks(toAdd.toArray(new MCBlock[0]));
        CraftTweakerAPI.apply(new ActionTagAdd<>(internal, itemsFromDefinitions, to.getIdInternal()));
    }
    
    @Override
    public void removeElements(MCTag<MCBlock> from, List<MCBlock> toRemove) {
        final ITag<Block> internal = getInternal(from);
        final Block[] itemsFromDefinitions = CraftTweakerHelper.getBlocks(toRemove.toArray(new MCBlock[0]));
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, itemsFromDefinitions, from.getIdInternal()));
    }
    
    @Override
    public List<MCBlock> getElementsInTag(MCTag<MCBlock> theTag) {
        final ITag<Block> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements().stream().map(MCBlock::new).collect(Collectors.toList());
    }
    
    @Override
    public ITag<Block> getInternal(MCTag<MCBlock> theTag) {
        final ITagCollection<Block> blockTags = TagCollectionManager.getManager().getBlockTags();
        return blockTags.get(theTag.getIdInternal());
    }
}
