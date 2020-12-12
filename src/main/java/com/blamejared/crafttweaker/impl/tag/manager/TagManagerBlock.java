package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl_native.blocks.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.tags.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@ZenRegister
@Document("vanilla/api/tags/TagManagerBlock")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerBlock")
public class TagManagerBlock implements TagManager<ExpandBlock> {
    
    public static final TagManagerBlock INSTANCE = new TagManagerBlock();
    
    private TagManagerBlock() {
    }
    
    @Override
    public @Nonnull
    Class<ExpandBlock> getElementClass() {
        return ExpandBlock.class;
    }
    
    @Override
    public String getTagFolder() {
        return "blocks";
    }
    
    @Override
    public List<MCTag<ExpandBlock>> getAllTagsFor(ExpandBlock element) {
        return getTagCollection().getOwningTags(element.getInternal())
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<ExpandBlock> to, List<ExpandBlock> toAdd) {
        final ITag<Block> internal = getInternal(to);
        final List<Block> blocks = CraftTweakerHelper.getBlocks(toAdd);
        
        if(internal == null) {
            final Tag<Block> tag = Tag.getTagFromContents(Sets.newHashSet(blocks));
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tag, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, blocks, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<ExpandBlock> from, List<ExpandBlock> toRemove) {
        final ITag<Block> internal = getInternal(from);
        final List<Block> blocks = CraftTweakerHelper.getBlocks(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, blocks, from));
    }
    
    @Override
    public List<ExpandBlock> getElementsInTag(MCTag<ExpandBlock> theTag) {
        final ITag<Block> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements().stream().map(ExpandBlock::new).collect(Collectors.toList());
    }
    
    @Nullable
    @Override
    public ITag<Block> getInternal(MCTag<ExpandBlock> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<Block> getTagCollection() {
        return TagCollectionManager.getManager().getBlockTags();
    }
}
