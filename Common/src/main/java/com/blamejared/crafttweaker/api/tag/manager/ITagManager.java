package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.tag.ActionTagAdd;
import com.blamejared.crafttweaker.api.action.tag.ActionTagCreate;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagManager;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/tag/manager/ITagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.ITagManager")
public interface ITagManager<T> extends CommandStringDisplayable, Iterable<MCTag<T>> {

    @ZenCodeType.Method
    @ZenCodeType.Getter("tagFolder")
    default String tagFolder() {

        String tagDir = TagManager.getTagDir(resourceKey());

        // Really not ideal, but I don't see a better way, lets just hope that other mods don't be dumb and add their tags to other folders.
        if (tagDir.startsWith("tags/")) {
            tagDir = tagDir.substring("tags/".length());
        }
        return tagDir;
    }

    @ZenCodeType.Method
    default MCTag<T> tag(String id) {

        ResourceLocation rl = ResourceLocation.tryParse(id);
        Objects.requireNonNull(rl, "Invalid Resource Location passed! Given: '" + id + "'");
        return tag(rl);
    }

    @ZenCodeType.Method
    default MCTag<T> tag(ResourceLocation id) {

        return tagMap().getOrDefault(id, new MCTag<>(id, this));
    }


    @ZenCodeType.Method
    default void addElements(MCTag<T> to, T... values) {

        if (!exists(to)) {
            CraftTweakerAPI.apply(new ActionTagCreate<>(to, List.of(values)));
        } else {
            CraftTweakerAPI.apply(new ActionTagAdd<>(to, List.of(values)));
        }
        recalculate();
    }

    @ZenCodeType.Method
    default void removeElements(MCTag<T> from, T... values) {

        if (!exists(from)) {
            throw new IllegalArgumentException("Cannot remove elements from empty tag: " + from);
        }
        CraftTweakerAPI.apply(new ActionTagAdd<>(from, List.of(values)));
        recalculate();
    }

    @ZenCodeType.Method
    default List<T> elements(MCTag<T> of) {

        if (!exists(of)) {
            return List.of();
        }
        return getInternal(of).getValues().stream().map(Holder::value).collect(Collectors.toList());
    }

    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(String id) {

        ResourceLocation rl = ResourceLocation.tryParse(id);
        Objects.requireNonNull(rl, "Invalid Resource Location passed! Given: '" + id + "'");
        return exists(rl);
    }

    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(ResourceLocation id) {

        return tagMap().containsKey(id);
    }

    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(MCTag<T> tag) {

        return exists(tag.id());
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("tagMap")
    Map<ResourceLocation, MCTag<T>> tagMap();

    @ZenCodeType.Method
    @ZenCodeType.Getter("tags")
    default List<MCTag<T>> tags() {

        return new ArrayList<>(tagMap().values());
    }

    @ZenCodeType.Method
    default List<MCTag<T>> getTagsFor(T element) {

        return tags().stream().filter(tag -> tag.contains(element)).toList();
    }

    @Override
    default String getCommandString() {

        return "<tagmanager:" + tagFolder() + ">";
    }

    @NotNull
    @Override
    default Iterator<MCTag<T>> iterator() {

        return tagMap().values().iterator();
    }

    void addTag(ResourceLocation id, Tag<Holder<T>> tag);

    Map<ResourceLocation, Tag<Holder<T>>> internalTags();

    @Nullable
    Tag<Holder<T>> getInternal(MCTag<T> tag);

    void bind(TagManager.LoadResult<?> result);

    Class<T> elementClass();

    ResourceKey<? extends Registry<T>> resourceKey();

    void recalculate();

}
