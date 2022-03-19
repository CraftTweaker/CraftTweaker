package com.blamejared.crafttweaker.api.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * EntityIngredient that facilitates accepting either a single, or multiple {@link EntityType}s, {@link MCTag<EntityType>}s
 * or {@link Many<MCTag<EntityType>>}s.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.EntityIngredient")
@Document("vanilla/api/entity/EntityIngredient")
public abstract class CTEntityIngredient implements CommandStringDisplayable {
    
    CTEntityIngredient() {}
    
    public abstract String getCommandString();
    
    public abstract <T> T mapTo(Function<EntityType<?>, T> typeMapper,
                                BiFunction<TagKey<EntityType<?>>, Integer, T> tagMapper,
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
        
        final EntityType<?> entityType;
        
        public EntityTypeIngredient(EntityType<?> entityType) {
            
            this.entityType = entityType;
        }
        
        @Override
        public String getCommandString() {
            
            return ExpandEntityType.getCommandString(entityType);
        }
        
        @Override
        public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<TagKey<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return typeMapper.apply(entityType);
        }
        
    }
    
    public final static class EntityTagWithAmountIngredient extends CTEntityIngredient {
        
        final Many<MCTag<EntityType<?>>> tag;
        
        public EntityTagWithAmountIngredient(Many<MCTag<EntityType<?>>> tag) {
            
            this.tag = tag;
        }
        
        @Override
        public String getCommandString() {
            
            return tag.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<TagKey<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return tagMapper.apply(tag.getData().getTagKey(), tag.getAmount());
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
        public <T> T mapTo(Function<EntityType<?>, T> typeMapper, BiFunction<TagKey<EntityType<?>>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            Stream<T> stream = elements.stream()
                    .map(element -> element.mapTo(typeMapper, tagMapper, compoundMapper));
            return compoundMapper.apply(stream);
        }
        
    }
    
    
}