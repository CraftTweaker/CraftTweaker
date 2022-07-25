package com.blamejared.crafttweaker.api.tag.type;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.type.UnknownTagManager;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;

/**
 * An unknown tag is a tag whose element is not known or has not been registered as Taggable.
 *
 * @implNote Modders should use {@link MCTag} instead of this class for parameters as a previously unknown tag could become a known tag in the future..
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@Document("vanilla/api/tag/type/UnknownTag")
@ZenCodeType.Name("crafttweaker.api.tag.type.UnknownTag")
@SuppressWarnings("ClassCanBeRecord")
public class UnknownTag implements MCTag {
    
    @Nonnull
    private final ResourceLocation id;
    @Nonnull
    private final UnknownTagManager manager;
    
    public UnknownTag(@Nonnull ResourceLocation id, @Nonnull UnknownTagManager manager) {
        
        this.id = id;
        this.manager = manager;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public ResourceLocation id() {
        
        return id;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("manager")
    public UnknownTagManager manager() {
        
        return manager;
    }
    
    @Override
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        
        return getCommandString();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        UnknownTag mcTag = (UnknownTag) o;
        
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
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public Many<UnknownTag> withAmount(int amount) {
        
        return new Many<>(this, amount, ts -> ts.getCommandString() + " * " + amount);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public Many<UnknownTag> asTagWithAmount() {
        
        return withAmount(1);
    }
    
}
