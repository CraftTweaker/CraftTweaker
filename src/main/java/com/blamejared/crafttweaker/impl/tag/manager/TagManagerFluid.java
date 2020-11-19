package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.fluid.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.google.common.collect.*;
import net.minecraft.fluid.*;
import net.minecraft.tags.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@ZenRegister
@Document("vanilla/api/tags/TagManagerFluid")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerFluid")
public class TagManagerFluid implements TagManager<MCFluid> {
    
    public static final TagManagerFluid INSTANCE = new TagManagerFluid();
    
    private TagManagerFluid() {
    }
    
    @Nonnull
    @Override
    public Class<MCFluid> getElementClass() {
        return MCFluid.class;
    }
    
    @Override
    public String getTagFolder() {
        return "fluids";
    }
    
    @Override
    public List<MCTag<MCFluid>> getAllTagsFor(MCFluid element) {
        return getTagCollection().getOwningTags(element.getInternal())
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<MCFluid> to, List<MCFluid> toAdd) {
        final ITag<Fluid> internal = getInternal(to);
        final List<Fluid> itemsFromDefinitions = CraftTweakerHelper.getFluids(toAdd);
        if(internal == null) {
            final Tag<Fluid> tagFromContents = Tag.getTagFromContents(Sets.newHashSet(itemsFromDefinitions));
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, itemsFromDefinitions, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<MCFluid> from, List<MCFluid> toRemove) {
        final ITag<Fluid> internal = getInternal(from);
        final List<Fluid> fluids = CraftTweakerHelper.getFluids(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, fluids, from));
    }
    
    @Override
    public List<MCFluid> getElementsInTag(MCTag<MCFluid> theTag) {
        final ITag<Fluid> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements().stream().map(MCFluid::new).collect(Collectors.toList());
    }
    
    @Nullable
    @Override
    public ITag<Fluid> getInternal(MCTag<MCFluid> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<Fluid> getTagCollection() {
        return TagCollectionManager.getManager().getFluidTags();
    }
}
