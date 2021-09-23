package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import net.minecraft.fluid.Fluid;
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
@Document("vanilla/api/tags/TagManagerFluid")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerFluid")
public class TagManagerFluid implements TagManager<Fluid> {
    
    public static final TagManagerFluid INSTANCE = new TagManagerFluid();
    
    private TagManagerFluid() {
    
    }
    
    @Nonnull
    @Override
    public Class<Fluid> getElementClass() {
        
        return Fluid.class;
    }
    
    @Override
    public String getTagFolder() {
        
        return "fluids";
    }
    
    @Override
    public List<MCTag<Fluid>> getAllTagsFor(Fluid element) {
        
        return getTagCollection().getOwningTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<Fluid> to, List<Fluid> toAdd) {
        
        final ITag<Fluid> internal = getInternal(to);
        if(internal == null) {
            final Tag<Fluid> tagFromContents = new Tag<>(Sets.newHashSet(toAdd), Fluid.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<Fluid> from, List<Fluid> toRemove) {
        
        final ITag<Fluid> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<Fluid> getElementsInTag(MCTag<Fluid> theTag) {
        
        final ITag<Fluid> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements();
    }
    
    @Nullable
    @Override
    public ITag<Fluid> getInternal(MCTag<Fluid> theTag) {
        
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<Fluid> getTagCollection() {
        
        return TagCollectionManager.getManager().getFluidTags();
    }
    
}
