package com.blamejared.crafttweaker.impl.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.entity.CTEntityIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO breaking - move this to a native
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.MCEntityType")
@Document("vanilla/api/entities/MCEntityType")
@ZenWrapper(wrappedClass = "net.minecraft.entity.EntityType", displayStringFormat = "%s.getCommandString()")
public class MCEntityType implements CommandStringDisplayable {
    
    private final EntityType<?> internal;
    
    public MCEntityType(@Nonnull EntityType<?> internal) {
        
        this.internal = Objects.requireNonNull(internal);
    }
    
    
    /**
     * Gets the registry name of this EntityType
     *
     * @return The registry name of this EntityType as a ResourceLocation
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public ResourceLocation getRegistryName() {
        
        return getInternal().getRegistryName();
    }
    
    /**
     * Creates a new entity in the world.
     *
     * @param world World for the entity to be created in
     *
     * @return The newly created Entity
     */
    @ZenCodeType.Method
    public Entity create(World world) {
        
        return getInternal().create(world);
    }
    
    @ZenCodeType.Getter("classification")
    public EntityClassification getClassification() {
        
        return getInternal().getClassification();
    }
    
    @ZenCodeType.Getter("serializable")
    public boolean isSerializable() {
        
        return getInternal().isSerializable();
    }
    
    @ZenCodeType.Getter("summonable")
    public boolean isSummonable() {
        
        return getInternal().isSummonable();
    }
    
    @ZenCodeType.Getter("immuneToFire")
    public boolean isImmuneToFire() {
        
        return getInternal().isImmuneToFire();
    }
    
    @ZenCodeType.Getter("translationKey")
    public String getTranslationKey() {
        
        return getInternal().getTranslationKey();
    }
    
    @ZenCodeType.Getter("name")
    public String getName() {
        
        return getInternal().getName().getString();
    }
    
    @ZenCodeType.Getter("lootTable")
    public String getLootTable() {
        
        return getInternal().getLootTable().toString();
    }
    
    @ZenCodeType.Getter("width")
    public float getWidth() {
        
        return getInternal().getWidth();
    }
    
    @ZenCodeType.Getter("height")
    public float getHeight() {
        
        return getInternal().getHeight();
    }
    
    @ZenCodeType.Getter("commandString")
    @Override
    public String getCommandString() {
        
        return "<entityType:" + getInternal().getRegistryName() + ">";
    }
    
    public EntityType<?> getInternal() {
        
        return internal;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        MCEntityType that = (MCEntityType) o;
        
        return getInternal().equals(that.getInternal());
    }
    
    @Override
    public int hashCode() {
        
        return getInternal().hashCode();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public CTEntityIngredient asEntityIngredient() {
        
        return new CTEntityIngredient.EntityTypeIngredient(this);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public CTEntityIngredient asList(CTEntityIngredient other) {
        
        List<CTEntityIngredient> elements = new ArrayList<>();
        elements.add(asEntityIngredient());
        elements.add(other);
        return new CTEntityIngredient.CompoundEntityIngredient(elements);
    }
    
}
