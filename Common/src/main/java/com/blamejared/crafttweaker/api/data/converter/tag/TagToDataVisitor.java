package com.blamejared.crafttweaker.api.data.converter.tag;

import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * Visitor to convert from Vanilla's Tag to CraftTweaker's Data system.
 *
 * You shouldn't be calling this class directly, you should be using {@link TagToDataConverter} as that takes into account a null Tag.
 */
public class TagToDataVisitor implements TagVisitor {
    
    private IData value;
    
    protected TagToDataVisitor() {
    
    }
    
    public IData visit(Tag tag) {
        
        tag.accept(this);
        return value;
    }
    
    private void setValue(IData data) {
        
        this.value = data;
    }
    
    public IData getValue() {
        
        return value;
    }
    
    @Override
    public void visitString(@NotNull StringTag tag) {
        
        setValue(new StringData(tag));
    }
    
    @Override
    public void visitByte(@NotNull ByteTag tag) {
        
        setValue(new ByteData(tag));
    }
    
    @Override
    public void visitShort(@NotNull ShortTag tag) {
        
        setValue(new ShortData(tag));
    }
    
    @Override
    public void visitInt(@NotNull IntTag tag) {
        
        setValue(new IntData(tag));
    }
    
    @Override
    public void visitLong(@NotNull LongTag tag) {
        
        setValue(new LongData(tag));
    }
    
    @Override
    public void visitFloat(@NotNull FloatTag tag) {
        
        setValue(new FloatData(tag));
    }
    
    @Override
    public void visitDouble(@NotNull DoubleTag tag) {
        
        setValue(new DoubleData(tag));
    }
    
    @Override
    public void visitByteArray(@NotNull ByteArrayTag tag) {
        
        setValue(new ByteArrayData(tag));
    }
    
    @Override
    public void visitIntArray(@NotNull IntArrayTag tag) {
        
        setValue(new IntArrayData(tag));
    }
    
    @Override
    public void visitLongArray(@NotNull LongArrayTag tag) {
        
        setValue(new LongArrayData(tag));
    }
    
    @Override
    public void visitList(@NotNull ListTag tag) {
        
        setValue(new ListData(tag));
    }
    
    @Override
    public void visitCompound(@NotNull CompoundTag tag) {
        
        setValue(new MapData(tag));
    }
    
    @Override
    public void visitEnd(@NotNull EndTag tag) {
        
        setValue(null);
    }
    
}
