package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
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
 * FluidIngredient that facilitates accepting either a single, or multiple {@link IFluidStack}s, {@link KnownTag <Fluid>}s
 * or {@link Many<KnownTag<Fluid>>}s.
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
    
    @ZenCodeType.Method
    public abstract boolean matches(Fluid fluid);
    
    public abstract boolean matches(FluidStack fluidStack);
    
    public abstract boolean matches(TagKey<Fluid> fluidTag);
    
    public abstract boolean matches(TagKey<Fluid> fluidTag, int amount);
    
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
        public boolean matches(FluidStack fluidStack) {
            
            return this.fluidStack.getInternal().containsFluid(fluidStack);
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag) {
            
            return this.fluidStack.getInternal().getFluid().is(fluidTag);
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag, int amount) {
            
            return amount == 1 && this.matches(fluidTag);
        }
        
        @Override
        public String getCommandString() {
            
            return fluidStack.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<FluidStack, T> fluidMapper, BiFunction<TagKey<Fluid>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return fluidMapper.apply(fluidStack.getImmutableInternal());
        }
    
        @Override
        public boolean matches(Fluid fluid) {
        
            return this.fluidStack.getInternal().getFluid() == fluid;
        }
    
    }
    
    public final static class FluidTagWithAmountIngredient extends CTFluidIngredient {
        
        final Many<KnownTag<Fluid>> tag;
        
        public FluidTagWithAmountIngredient(Many<KnownTag<Fluid>> tag) {
            
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
    
        @Override
        public boolean matches(Fluid fluid) {
        
            return this.tag.getData().contains(fluid);
        }
    
        @Override
        public boolean matches(FluidStack fluidStack) {
            
            return this.tag.getAmount() == 1 && this.tag.getData().contains(fluidStack.getFluid());
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag) {
            
            return this.tag.getData().getTagKey().equals(fluidTag);
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag, int amount) {
            
            return this.tag.getAmount() == amount && matches(fluidTag);
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
    
        @Override
        public boolean matches(Fluid fluid) {
        
            return this.elements.stream().anyMatch(ingr -> ingr.matches(fluid));
        }
    
        @Override
        public boolean matches(FluidStack fluidStack) {
            
            return this.elements.stream().anyMatch(ingr -> ingr.matches(fluidStack));
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag) {
            
            return this.elements.stream().anyMatch(ingr -> ingr.matches(fluidTag));
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag, int amount) {
            
            return this.elements.stream().anyMatch(ingr -> ingr.matches(fluidTag, amount));
        }
        
    }
    
    
}