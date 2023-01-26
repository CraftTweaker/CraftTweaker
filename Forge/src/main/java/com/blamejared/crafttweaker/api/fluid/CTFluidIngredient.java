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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
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
    
    public static final Supplier<CTFluidIngredient> EMPTY = () -> new FluidStackIngredient(IFluidStack.empty());
    
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
    
    protected abstract void dissolve();
    
    public abstract List<IFluidStack> getMatchingStacks();
    
    public boolean contains(CTFluidIngredient other) {
        
        return other.getMatchingStacks().stream().allMatch(iFluidStack -> matches(iFluidStack.getInternal()));
    }
    
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
        
        List<IFluidStack> fluidStacks;
        
        public FluidStackIngredient(IFluidStack fluidStack) {
            
            this.fluidStack = fluidStack;
        }
        
        @Override
        public boolean matches(Fluid fluid) {
            
            return this.fluidStack.getInternal().getFluid() == fluid;
        }
        
        @Override
        public boolean matches(FluidStack fluidStack) {
            
            FluidStack internal = this.fluidStack.getInternal();
            return internal.isFluidEqual(fluidStack) && internal.getAmount() <= fluidStack.getAmount();
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag) {
            
            return this.fluidStack.getInternal().getFluid().is(fluidTag);
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag, int amount) {
            
            return this.matches(fluidTag) && this.fluidStack.getAmount() <= amount;
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
        protected void dissolve() {
            
            if(fluidStacks == null) {
                this.fluidStacks = List.of(fluidStack);
            }
        }
        
        @Override
        public List<IFluidStack> getMatchingStacks() {
            
            dissolve();
            return this.fluidStacks;
        }
        
    }
    
    public final static class FluidTagWithAmountIngredient extends CTFluidIngredient {
        
        final Many<KnownTag<Fluid>> tag;
        
        List<IFluidStack> matchingStacks;
        
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
            
            return this.tag.getData().contains(fluidStack.getFluid()) && this.tag.getAmount() <= fluidStack.getAmount();
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag) {
            
            return this.tag.getData().getTagKey().equals(fluidTag);
        }
        
        @Override
        public boolean matches(TagKey<Fluid> fluidTag, int amount) {
            
            return matches(fluidTag) && this.tag.getAmount() <= amount;
        }
        
        protected void dissolve() {
            
            if(matchingStacks == null) {
                matchingStacks = tag.getData()
                        .elements()
                        .stream()
                        .map(fluid -> new FluidStack(fluid, tag.getAmount()))
                        .map(IFluidStack::of)
                        .toList();
            }
        }
        
        @Override
        public List<IFluidStack> getMatchingStacks() {
            
            dissolve();
            return matchingStacks;
        }
        
    }
    
    public final static class CompoundFluidIngredient extends CTFluidIngredient {
        
        final List<CTFluidIngredient> elements;
        List<IFluidStack> matchingStacks;
        
        final List<CTFluidIngredient> elementsView;
        
        public CompoundFluidIngredient(List<CTFluidIngredient> elements) {
            
            this.elements = elements;
            this.elementsView = Collections.unmodifiableList(elements);
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
        
        @Override
        protected void dissolve() {
            
            if(matchingStacks == null) {
                matchingStacks = elements.stream()
                        .map(CTFluidIngredient::getMatchingStacks)
                        .flatMap(Collection::stream)
                        .toList();
            }
        }
        
        @Override
        public List<IFluidStack> getMatchingStacks() {
            
            dissolve();
            return matchingStacks;
        }
        
        public List<CTFluidIngredient> getElements() {
            
            return elementsView;
        }
        
    }
    
}