package com.blamejared.crafttweaker.impl.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.entity.EntityClassification;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.MCEntityClassification")
@Document("vanilla/api/entities/MCEntityClassification")
@ZenWrapper(wrappedClass = "net.minecraft.entity.EntityClassification", displayStringFormat = "%s.getCommandString()")
public class MCEntityClassification implements CommandStringDisplayable {
    
    private final EntityClassification internal;
    
    public MCEntityClassification(EntityClassification internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Getter("name")
    public String getName() {
        return internal.getName();
    }
    
    @ZenCodeType.Getter("maxNumberOfEntity")
    public int getMaxNumberOfCreature() {
        return internal.getMaxNumberOfCreature();
    }
    
    @ZenCodeType.Getter("isPeaceful")
    public boolean isPeacefulCreature() {
        return internal.getPeacefulCreature();
    }
    
    @ZenCodeType.Getter("isAnimal")
    public boolean isAnimal() {
        return internal.getAnimal();
    }


    
    public EntityClassification getInternal() {
        return internal;
    }

    @Override
    public String getCommandString() {
        return "<entityclassification:" + internal.getName() + ">";
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        MCEntityClassification that = (MCEntityClassification) o;
    
        return internal == that.internal;
    }
    
    @Override
    public int hashCode() {
        return internal.hashCode();
    }
}
