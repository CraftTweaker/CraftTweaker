package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.ActionTagAdd;
import com.blamejared.crafttweaker.api.action.tag.ActionTagCreate;
import com.blamejared.crafttweaker.api.action.tag.ActionTagRemove;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistry;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessSetTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.tags.SetTag;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tag/TagManagerBlock")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerBlock")
public class TagManagerBlock implements ITagManager<Block> {
    
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
        
        return getTagCollection().getMatchingTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<Block> to, List<Block> toAdd) {
        
        final Tag<Block> internal = getInternal(to);
        
        if(internal == null) {
            
            final SetTag<Block> tag = AccessSetTag.init(Sets.newHashSet(toAdd), Block.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tag, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<Block> from, List<Block> toRemove) {
        
        final Tag<Block> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<Block> getElementsInTag(MCTag<Block> theTag) {
        
        final Tag<Block> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getValues();
    }
    
    @Nullable
    @Override
    public Tag<Block> getInternal(MCTag<Block> theTag) {
        
        return getTagCollection().getTag(theTag.id());
    }
    
    @Override
    public TagCollection<Block> getTagCollection() {
    
        return CrTTagRegistry.INSTANCE.getCurrentTagContainer().get().getOrEmpty(Registry.BLOCK_REGISTRY);
    }
    
}
