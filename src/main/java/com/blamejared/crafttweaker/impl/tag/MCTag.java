package com.blamejared.crafttweaker.impl.tag;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.tag.manager.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import java.util.*;

/**
 * A reference to a Tag object.
 * Note that this tag may not exist in the game already, such as when you create new tags.
 * See the {@link MCTag#exists()} Method on whether or not this tag already exists.
 * <p>
 * A tag will be created as soon as you add
 *
 * @param <T> The elements within this tag.
 */
@ZenRegister
@Document("vanilla/api/tags/MCTag")
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
public final class MCTag<T extends CommandStringDisplayable> implements CommandStringDisplayable {
    
    private final ResourceLocation id;
    private final TagManager<T> manager;
    
    public MCTag(ResourceLocation id, TagManager<T> manager) {
        this.id = id;
        this.manager = manager;
    }
    
    @ZenCodeType.Method
    public void add(List<T> items) {
        manager.addElements(this, items);
    }
    
    @ZenCodeType.Method
    public void remove(List<T> items) {
        manager.removeElements(this, items);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("exists")
    public boolean exists() {
        return manager.exists(id.toString());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("elements")
    public List<T> getElements() {
        return manager.getElementsInTag(this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("first")
    public T getFirst() {
        final List<T> elements = getElements();
        if(elements.isEmpty()) {
            throw new NoSuchElementException("Cannot get first element of empty or nonexistent tag!");
        }
        return elements.get(0);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("manager")
    public TagManager<T> getManager() {
        return manager;
    }
    
    @Override
    public String getCommandString() {
        return "<tag:" + manager.getTagFolder() + ":" + id + ">";
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public MCResourceLocation getId() {
        return new MCResourceLocation(id);
    }
    
    public ResourceLocation getIdInternal() {
        return id;
    }
    
    /**
     * Use the manager directly if possible, as then you can work typed.
     */
    public ITag<?> getInternal() {
        return manager.getInternal(this);
    }
    
    /**
     * Only used to make it easier if for some reason you cannot work typed, to not always have to cast to Raw.
     */
    @SuppressWarnings({"rawtypes", "unused"})
    public final ITag getInternalRaw() {
        return getInternal();
    }
    
    @Override
    public String toString() {
        return getCommandString();
    }
}
