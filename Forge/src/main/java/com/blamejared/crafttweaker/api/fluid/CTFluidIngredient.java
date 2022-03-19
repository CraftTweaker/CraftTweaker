package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FluidIngredient that facilitates accepting either a single, or multiple {@link IFluidStack}s, {@link MCTag<Fluid>}s
 * or {@link Many<MCTag><Fluid>}s.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.FluidIngredient")
@Document("forge/api/fluid/FluidIngredient")
public abstract class CTFluidIngredient implements CommandStringDisplayable {
    
    CTFluidIngredient() {}
    
    public abstract String getCommandString();
    
    public abstract <T> T mapTo(Function<FluidStack, T> fluidMapper,
                                BiFunction<TagKey<Fluid>, Integer, T> tagMapper,
                                Function<Stream<T>, T> compoundMapper);
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public CTFluidIngredient asCompound(CTFluidIngredient other) {
        
        List<CTFluidIngredient> ingredients = new ArrayList<>();
        if(other instanceof CompoundFluidIngredient) {
            ingredients.addAll(((CompoundFluidIngredient) other).elements);
        } else {
            ingredients.add(other);
        }
        
        if(this instanceof CompoundFluidIngredient) {
            ((CompoundFluidIngredient) this).elements.addAll(ingredients);
            return this;
        } else {
            ingredients.add(this);
        }
        
        return new CompoundFluidIngredient(ingredients);
    }
    
    public static final class FluidStackIngredient extends CTFluidIngredient {
        
        final IFluidStack fluidStack;
        
        public FluidStackIngredient(IFluidStack fluidStack) {
            
            this.fluidStack = fluidStack;
        }
        
        @Override
        public String getCommandString() {
            
            return fluidStack.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<FluidStack, T> fluidMapper, BiFunction<TagKey<Fluid>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return fluidMapper.apply(fluidStack.getImmutableInternal());
        }
        
    }
    
    public final static class FluidTagWithAmountIngredient extends CTFluidIngredient {
        
        final Many<MCTag<Fluid>> tag;
        
        public FluidTagWithAmountIngredient(Many<MCTag<Fluid>> tag) {
            
            this.tag = tag;
        }
        
        @Override
        public String getCommandString() {
            
            return tag.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<FluidStack, T> fluidMapper, BiFunction<TagKey<Fluid>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return tagMapper.apply(tag.getData().getTagKey(), tag.getAmount());
        }
        
    }
    
    public final static class CompoundFluidIngredient extends CTFluidIngredient {
        
        final List<CTFluidIngredient> elements;
        
        public CompoundFluidIngredient(List<CTFluidIngredient> elements) {
            
            this.elements = elements;
        }
        
        @Override
        public String getCommandString() {
            
            return elements.stream().map(CTFluidIngredient::getCommandString).collect(Collectors.joining(" | "));
        }
        
        @Override
        public <T> T mapTo(Function<FluidStack, T> fluidMapper, BiFunction<TagKey<Fluid>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            Stream<T> stream = elements.stream()
                    .map(element -> element.mapTo(fluidMapper, tagMapper, compoundMapper));
            return compoundMapper.apply(stream);
        }
        
    }
    
    
}