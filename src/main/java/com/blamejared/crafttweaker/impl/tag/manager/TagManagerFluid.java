package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.fluid.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
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
    public boolean exists(MCResourceLocation location) {
        final ITagCollection<Fluid> itemTags = TagCollectionManager.getManager().getFluidTags();
        return itemTags.get(location.getInternal()) != null;
    }
    
    @Override
    public List<MCTag<MCFluid>> getAllTags() {
        final ITagCollection<Fluid> itemTags = TagCollectionManager.getManager().getFluidTags();
        return itemTags.getIDTagMap()
                .keySet()
                .stream()
                .map(itemITag -> new MCTag<>(itemITag, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getTagFolder() {
        return "fluid";
    }
    
    @Override
    public void addElements(MCTag<MCFluid> to, List<MCFluid> toAdd) {
        final ITag<Fluid> internal = getInternal(to);
        if(internal == null) {
            //TODO: Add tag
            return;
        }
        
        final Fluid[] itemsFromDefinitions = CraftTweakerHelper.getFluids(toAdd.toArray(new MCFluid[0]));
        CraftTweakerAPI.apply(new ActionTagAdd<>(internal, itemsFromDefinitions, to.getIdInternal()));
    }
    
    @Override
    public void removeElements(MCTag<MCFluid> from, List<MCFluid> toRemove) {
        final ITag<Fluid> internal = getInternal(from);
        final Fluid[] itemsFromDefinitions = CraftTweakerHelper.getFluids(toRemove.toArray(new MCFluid[0]));
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, itemsFromDefinitions, from.getIdInternal()));
    }
    
    @Override
    public List<MCFluid> getElementsInTag(MCTag<MCFluid> theTag) {
        final ITag<Fluid> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements().stream().map(MCFluid::new).collect(Collectors.toList());
    }
    
    @Override
    public ITag<Fluid> getInternal(MCTag<MCFluid> theTag) {
        return TagCollectionManager.getManager().getFluidTags().get(theTag.getIdInternal());
    }
}
