package com.blamejared.crafttweaker.api.fluid;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;
import net.minecraftforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.fluid.FluidIngredient")
@Document("vanilla/api/fluid/FluidIngredient")
public abstract class CrTFluidIngredient implements CommandStringDisplayable {
    
    CrTFluidIngredient() {} //package private if impls in same package, private if in same class
    
    public abstract String getCommandString();
    
    public abstract <T> T mapTo(
            Function<FluidStack, T> fluidMapper,
            BiFunction<ITag<Fluid>, Integer, T> tagMapper,
            Function<List<? extends T>, T> compoundMapper
    );
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public CrTFluidIngredient asCompound(CrTFluidIngredient other) {
        // TODO if other is a compound ingredient, maybe expand it's inner ingredients?
        return new CompoundFluidIngredient(new CrTFluidIngredient[] {this, other});
    }
    
    public static final class FluidStackIngredient extends CrTFluidIngredient {
        
        private final IFluidStack fluidStack;
        
        public FluidStackIngredient(IFluidStack fluidStack) {
            
            this.fluidStack = fluidStack;
        }
        
        @Override
        public String getCommandString() {
            
            return fluidStack.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<FluidStack, T> fluidMapper, BiFunction<ITag<Fluid>, Integer, T> tagMapper, Function<List<? extends T>, T> compoundMapper) {
            
            return fluidMapper.apply(fluidStack.getImmutableInternal());
        }
        
    }
    
    public final static class TagWithAmountFluidIngredient extends CrTFluidIngredient {
        
        private final MCTagWithAmount<Fluid> tag;
        
        public TagWithAmountFluidIngredient(MCTagWithAmount<Fluid> tag) {
            
            this.tag = tag;
        }
        
        @Override
        public String getCommandString() {
            
            return tag.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<FluidStack, T> fluidMapper, BiFunction<ITag<Fluid>, Integer, T> tagMapper, Function<List<? extends T>, T> compoundMapper) {
            
            return (T) tagMapper.apply(tag.getTag().getInternalRaw(), tag.getAmount());
        }
        
    }
    
    public final static class CompoundFluidIngredient extends CrTFluidIngredient {
        
        private final CrTFluidIngredient[] elements;
        
        public CompoundFluidIngredient(CrTFluidIngredient[] elements) {
            
            this.elements = elements;
        }
        
        @Override
        public String getCommandString() {
            
            return Arrays.stream(elements).map(CrTFluidIngredient::getCommandString).collect(Collectors.joining(" | "));
        }
        
        @Override
        public <T> T mapTo(Function<FluidStack, T> fluidMapper, BiFunction<ITag<Fluid>, Integer, T> tagMapper, Function<List<? extends T>, T> compoundMapper) {
            
            List<T> list = Arrays.stream(elements)
                    .map(element -> element.mapTo(fluidMapper, tagMapper, compoundMapper))
                    .collect(Collectors.toList());
            return compoundMapper.apply(list);
        }
        
    }
    
    
}