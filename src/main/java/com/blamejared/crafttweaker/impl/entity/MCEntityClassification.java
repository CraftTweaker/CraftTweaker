package com.blamejared.crafttweaker.impl.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.entity.EntityClassification;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.MCEntityClassification")
@Document("vanilla/entities/MCEntityClassification")
@ZenWrapper(wrappedClass = "net.minecraft.entity.EntityClassification", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.getCommandString()")
public class MCEntityClassification implements CommandStringDisplayable {
    
    private EntityClassification internal;
    
    public MCEntityClassification(EntityClassification internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Getter("name")
    public String getName() {
        return internal.func_220363_a();
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
        return "<entityclassification:" + internal.func_220363_a() + ">";
    }
}
