package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityType;
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
@Document("vanilla/api/tags/TagManagerEntityType")
@ZenCodeType.Name("crafttweaker.api.tag.TagManagerEntityType")
public class TagManagerEntityType implements TagManager<MCEntityType> {
    
    public static final TagManagerEntityType INSTANCE = new TagManagerEntityType();
    
    private TagManagerEntityType() {
    }
    
    @Override
    public @Nonnull
    Class<MCEntityType> getElementClass() {
        return MCEntityType.class;
    }
    
    @Override
    public String getTagFolder() {
        return "entity_types";
    }
    
    @Override
    public List<MCTag<MCEntityType>> getAllTagsFor(MCEntityType element) {
        return getTagCollection().getOwningTags(element.getInternal())
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<MCEntityType> to, List<MCEntityType> toAdd) {
        final ITag<EntityType<?>> internal = getInternal(to);
        final List<EntityType<?>> entityTypes = CraftTweakerHelper.getEntityTypes(toAdd);
        if(internal == null) {
            final Tag<EntityType<?>> tagFromContents = new Tag<>(Sets.newHashSet(entityTypes), EntityType.class);
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, entityTypes, to));
        }
        
    }
    
    @Override
    public void removeElements(MCTag<MCEntityType> from, List<MCEntityType> toRemove) {
        final ITag<EntityType<?>> internal = getInternal(from);
        final List<EntityType<?>> entityTypes = CraftTweakerHelper.getEntityTypes(toRemove);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, entityTypes, from));
    }
    
    @Override
    public List<MCEntityType> getElementsInTag(MCTag<MCEntityType> theTag) {
        final ITag<EntityType<?>> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements()
                .stream()
                .map(MCEntityType::new)
                .collect(Collectors.toList());
    }
    
    @Nullable
    @Override
    public ITag<EntityType<?>> getInternal(MCTag<MCEntityType> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<EntityType<?>> getTagCollection() {
        return TagCollectionManager.getManager().getEntityTypeTags();
    }
}
