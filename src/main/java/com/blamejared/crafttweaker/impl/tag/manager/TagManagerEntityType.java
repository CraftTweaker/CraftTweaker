package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
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
public class TagManagerEntityType implements TagManager<EntityType<?>> {
    
    public static final TagManagerEntityType INSTANCE = new TagManagerEntityType();
    
    private TagManagerEntityType() {
    }
    
    @Override
    @Nonnull
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Class<EntityType<?>> getElementClass() {
        return (Class) EntityType.class;
    }
    
    @Override
    public String getTagFolder() {
        return "entity_types";
    }
    
    @Override
    public List<MCTag<EntityType<?>>> getAllTagsFor(EntityType<?> element) {
        return getTagCollection().getOwningTags(element)
                .stream()
                .map(location -> new MCTag<>(location, this))
                .collect(Collectors.toList());
    }
    
    @Override
    public void addElements(MCTag<EntityType<?>> to, List<EntityType<?>> toAdd) {
        final ITag<EntityType<?>> internal = getInternal(to);
        if(internal == null) {
            final Tag<EntityType<?>> tagFromContents = Tag.getTagFromContents(Sets.newHashSet(toAdd));
            CraftTweakerAPI.apply(new ActionTagCreate<>(getTagCollection(), tagFromContents, to));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(internal, toAdd, to));
        }
        
    }
    
    @Override
    public void removeElements(MCTag<EntityType<?>> from, List<EntityType<?>> toRemove) {
        final ITag<EntityType<?>> internal = getInternal(from);
        CraftTweakerAPI.apply(new ActionTagRemove<>(internal, toRemove, from));
    }
    
    @Override
    public List<EntityType<?>> getElementsInTag(MCTag<EntityType<?>> theTag) {
        final ITag<EntityType<?>> internal = getInternal(theTag);
        if(internal == null) {
            return Collections.emptyList();
        }
        
        return internal.getAllElements();
    }
    
    @Nullable
    @Override
    public ITag<EntityType<?>> getInternal(MCTag<EntityType<?>> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    @Override
    public ITagCollection<EntityType<?>> getTagCollection() {
        return TagCollectionManager.getManager().getEntityTypeTags();
    }
}
