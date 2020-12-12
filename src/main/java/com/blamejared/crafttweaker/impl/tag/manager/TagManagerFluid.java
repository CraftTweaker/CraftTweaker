package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.actions.tags.*;
import com.blamejared.crafttweaker.impl.helper.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl_native.fluid.ExpandFluid;
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
public class TagManagerFluid implements TagManager<ExpandFluid> {
    
    public static final TagManagerFluid INSTANCE = new TagManagerFluid();
    
    private TagManagerFluid() {
    }
    
    @Nonnull
    @Override
    public Class<ExpandFluid> getElementClass() {
        return ExpandFluid.class;
    }
    
    @Override
    public String getTagFolder() {
        return "fluids";
    }
    
    @Override
    public List<MCTag<ExpandFluid>> getAllTagsFor(ExpandFluid element) {
        return getTagCollection().getOwningTags(element.getInternal())
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<ExpandFluid> to, List<ExpandFluid> toAdd) {
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
    public void removeElements(MCTag<ExpandFluid> from, List<ExpandFluid> toRemove) {
        final ITag<Fluid> internal = getInternal(from);
        final List<Fluid> fluids = CraftTweakerHelper.getFluids(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, fluids, from));
    }
    
    @Override
    public List<ExpandFluid> getElementsInTag(MCTag<ExpandFluid> theTag) {
        final ITag<Fluid> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements().stream().map(ExpandFluid::new).collect(Collectors.toList());
    }
    
    @Nullable
    @Override
    public ITag<Fluid> getInternal(MCTag<ExpandFluid> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<Fluid> getTagCollection() {
        return TagCollectionManager.getManager().getFluidTags();
    }
}
