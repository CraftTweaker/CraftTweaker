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
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tag/TagManagerFluid")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerFluid")
public class TagManagerFluid implements ITagManager<Fluid> {
    
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
        
        return getTagCollection().getMatchingTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<Fluid> to, List<Fluid> toAdd) {
        
        final Tag<Fluid> internal = getInternal(to);
        if(internal == null) {
            final SetTag<Fluid> tagFromContents = AccessSetTag.init(Sets.newHashSet(toAdd), Fluid.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
    }
    
    @Override
    public void removeElements(MCTag<Fluid> from, List<Fluid> toRemove) {
        
        final Tag<Fluid> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<Fluid> getElementsInTag(MCTag<Fluid> theTag) {
        
        final Tag<Fluid> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getValues();
    }
    
    @Nullable
    @Override
    public Tag<Fluid> getInternal(MCTag<Fluid> theTag) {
        
        return getTagCollection().getTag(theTag.id());
    }
    
    @Override
    public TagCollection<Fluid> getTagCollection() {
        
        return CrTTagRegistry.INSTANCE.getCurrentTagContainer().get().getOrEmpty(Registry.FLUID_REGISTRY);
    }
    
}
