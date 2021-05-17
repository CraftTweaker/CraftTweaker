package com.blamejared.crafttweaker.api.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * EntityIngredient that facilitates accepting either a single, or multiple {@link net.minecraft.entity.EntityType}s, {@link com.blamejared.crafttweaker.impl.tag.MCTag<net.minecraft.entity.EntityType>}s
 * or {@link MCTagWithAmount<net.minecraft.entity.EntityType>}s.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.EntityIngredient")
@Document("vanilla/api/entity/EntityIngredient")
public abstract class CTEntityIngredient implements CommandStringDisplayable {
    
    CTEntityIngredient() {}
    
    public abstract String getCommandString();
    
    public abstract <T> T mapTo(Function<EntityType<?>, T> typeMapper,
                                BiFunction<ITag<EntityType<?>>, Integer, T> tagMapper,
                                Function<Stream<T>, T> compoundMapper);
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public CTEntityIngredient asCompound(CTEntityIngredient other) {
        
        List<CTEntityIngredient> ingredients = new ArrayList<>();
        if(other instanceof CompoundEntityIngredient) {
            ingredients.addAll(((CompoundEntityIngredient) other).elements);
        } else {
            ingredients.add(other);
        }
        
        if(this instanceof CompoundEntityIngredient) {
            ((CompoundEntityIngredient) this).elements.addAll(ingredients);
            return this;
        } else {
            ingredients.add(this);
        }
        
        return new CompoundEntityIngredient(ingredients);
    }
    
    public static final class EntityTypeIngredient extends CTEntityIngredient {
        
        final MCEntityType entityType;
        
        public EntityTypeIngredient(MCEntityType entityType) {
            
            this.entityType = entityType;
        }
        
        @Override
        public String getCommandString() {
            
            return entityType.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<ITag<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return typeMapper.apply(entityType.getInternal());
        }
        
    }
    
    public final static class EntityTagWithAmountIngredient extends CTEntityIngredient {
        
        final MCTagWithAmount<MCEntityType> tag;
        
        public EntityTagWithAmountIngredient(MCTagWithAmount<MCEntityType> tag) {
            
            this.tag = tag;
        }
        
        @Override
        public String getCommandString() {
            
            return tag.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<ITag<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return (T) tagMapper.apply(tag.getTag().getInternalRaw(), tag.getAmount());
        }
        
    }
    
    public final static class CompoundEntityIngredient extends CTEntityIngredient {
        
        final List<CTEntityIngredient> elements;
        
        public CompoundEntityIngredient(List<CTEntityIngredient> elements) {
            
            this.elements = elements;
        }
        
        @Override
        public String getCommandString() {
            
            return elements.stream().map(CTEntityIngredient::getCommandString).collect(Collectors.joining(" | "));
        }
        
        @Override
        public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<ITag<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            Stream<T> stream = elements.stream()
                    .map(element -> element.mapTo(typeMapper, tagMapper, compoundMapper));
            return compoundMapper.apply(stream);
        }
        
    }
    
    
}