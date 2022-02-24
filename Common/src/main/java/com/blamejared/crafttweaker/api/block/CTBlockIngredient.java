package com.blamejared.crafttweaker.api.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.BlockIngredient")
@Document("vanilla/api/block/BlockIngredient")
public abstract class CTBlockIngredient implements CommandStringDisplayable {
    
    public abstract String getCommandString();
    
    public abstract <T> T mapTo(Function<Block, T> blockMapper,
                                Function<BlockState, T> blockStateMapper,
                                BiFunction<Tag<Block>, Integer, T> tagMapper,
                                Function<Stream<T>, T> compoundMapper);
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public CTBlockIngredient asCompound(CTBlockIngredient other) {
        
        List<CTBlockIngredient> ingredients = new ArrayList<>();
        if(other instanceof CTBlockIngredient.CompoundBlockIngredient) {
            ingredients.addAll(((CTBlockIngredient.CompoundBlockIngredient) other).elements);
        } else {
            ingredients.add(other);
        }
        
        if(this instanceof CTBlockIngredient.CompoundBlockIngredient) {
            ((CTBlockIngredient.CompoundBlockIngredient) this).elements.addAll(ingredients);
            return this;
        } else {
            ingredients.add(this);
        }
        
        return new CTBlockIngredient.CompoundBlockIngredient(ingredients);
    }
    
    
    public static final class BlockIngredient extends CTBlockIngredient {
        
        final Block block;
        
        public BlockIngredient(Block block) {
            
            this.block = block;
        }
        
        @Override
        public String getCommandString() {
            
            return ExpandBlock.getCommandString(block);
        }
        
        @Override
        public <T> T mapTo(Function<Block, T> blockMapper, Function<BlockState, T> blockStateMapper, BiFunction<Tag<Block>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return blockMapper.apply(block);
        }
        
    }
    
    public static final class BlockStateIngredient extends CTBlockIngredient {
        
        final BlockState blockState;
        
        public BlockStateIngredient(BlockState blockState) {
            
            this.blockState = blockState;
        }
        
        @Override
        public String getCommandString() {
            
            return ExpandBlockState.getCommandString(blockState);
        }
        
        @Override
        public <T> T mapTo(Function<Block, T> blockMapper, Function<BlockState, T> blockStateMapper, BiFunction<Tag<Block>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return blockStateMapper.apply(blockState);
        }
        
    }
    
    public final static class BlockTagWithAmountIngredient extends CTBlockIngredient {
        
        final Many<MCTag<Block>> tag;
        
        public BlockTagWithAmountIngredient(Many<MCTag<Block>> tag) {
            
            this.tag = tag;
        }
        
        @Override
        public String getCommandString() {
            
            return tag.getCommandString();
        }
        
        @Override
        public <T> T mapTo(Function<Block, T> blockMapper, Function<BlockState, T> blockStateMapper, BiFunction<Tag<Block>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            return tagMapper.apply(tag.getData().getInternal(), tag.getAmount());
        }
        
    }
    
    public final static class CompoundBlockIngredient extends CTBlockIngredient {
        
        final List<CTBlockIngredient> elements;
        
        public CompoundBlockIngredient(List<CTBlockIngredient> elements) {
            
            this.elements = elements;
        }
        
        @Override
        public String getCommandString() {
            
            return elements.stream().map(CTBlockIngredient::getCommandString).collect(Collectors.joining(" | "));
        }
        
        @Override
        public <T> T mapTo(Function<Block, T> blockMapper, Function<BlockState, T> blockStateMapper, BiFunction<Tag<Block>, Integer, T> tagMapper, Function<Stream<T>, T> compoundMapper) {
            
            Stream<T> stream = elements.stream()
                    .map(element -> element.mapTo(blockMapper, blockStateMapper, tagMapper, compoundMapper));
            return compoundMapper.apply(stream);
        }
        
    }
    
}
