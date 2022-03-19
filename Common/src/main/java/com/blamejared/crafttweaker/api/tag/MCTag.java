package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;


@ZenRegister
@Document("vanilla/api/tag/MCTag")
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
@SuppressWarnings("ClassCanBeRecord")
public class MCTag<T> implements CommandStringDisplayable, Iterable<T>, Comparable<MCTag<T>> {
    
    @Nonnull
    private final ResourceLocation id;
    @Nonnull
    private final ITagManager<T> manager;
    
    public MCTag(@Nonnull ResourceLocation id, @Nonnull ITagManager<T> manager) {
        
        this.id = id;
        this.manager = manager;
    }
    
    @SafeVarargs
    @ZenCodeType.Method
    public final void add(T... elements) {
        
        manager().addElements(this, elements);
    }
    
    @SafeVarargs
    @ZenCodeType.Method
    public final void remove(T... elements) {
        
        manager().removeElements(this, elements);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("exists")
    public boolean exists() {
        
        return manager().exists(this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("elements")
    public List<T> elements() {
        
        return manager().elements(this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    public boolean contains(T element) {
        
        return elements().contains(element);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public ResourceLocation id() {
        
        return id;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("manager")
    public ITagManager<T> manager() {
        
        return manager;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(MCTag<T> other) {
        
        return id.equals(other.id) && manager.equals(other.manager);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public Many<MCTag<T>> withAmount(int amount) {
        
        return new Many<>(this, amount, ts -> ts.getCommandString() + " * " + amount);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public Many<MCTag<T>> asTagWithAmount() {
        
        return withAmount(1);
    }
    
    @Override
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        
        return getCommandString();
    }
    
    @Override
    public String getCommandString() {
        
        return "<tag:" + manager().tagFolder() + ":" + id() + ">";
    }
    
    public Tag<Holder<T>> getInternal() {
        
        return manager.getInternal(this);
    }
    
    public TagKey<T> getTagKey() {
        
        return TagKey.create(manager().resourceKey(), this.id());
    }
    
    @Nonnull
    @Override
    public Iterator<T> iterator() {
        
        return elements().iterator();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        MCTag<?> mcTag = (MCTag<?>) o;
        
        if(!id.equals(mcTag.id)) {
            return false;
        }
        return manager.equals(mcTag.manager);
    }
    
    @Override
    public int hashCode() {
        
        int result = id.hashCode();
        result = 31 * result + manager.hashCode();
        return result;
    }
    
    
    @Override
    public int compareTo(@Nonnull MCTag<T> o) {
        
        return this.id().compareTo(o.id());
    }
    
}
