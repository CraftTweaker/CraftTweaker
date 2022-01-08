package com.blamejared.crafttweaker.api.data.base.converter.tag;

import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.base.IData;
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

/**
 * Vistor to convert from Vanilla's Tag to CraftTweaker's Data system.
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
    public void visitString(StringTag tag) {
        
        setValue(new StringData(tag));
    }
    
    @Override
    public void visitByte(ByteTag tag) {
        
        setValue(new ByteData(tag));
    }
    
    @Override
    public void visitShort(ShortTag tag) {
        
        setValue(new ShortData(tag));
    }
    
    @Override
    public void visitInt(IntTag tag) {
        
        setValue(new IntData(tag));
    }
    
    @Override
    public void visitLong(LongTag tag) {
        
        setValue(new LongData(tag));
    }
    
    @Override
    public void visitFloat(FloatTag tag) {
        
        setValue(new FloatData(tag));
    }
    
    @Override
    public void visitDouble(DoubleTag tag) {
        
        setValue(new DoubleData(tag));
    }
    
    @Override
    public void visitByteArray(ByteArrayTag tag) {
        
        setValue(new ByteArrayData(tag));
    }
    
    @Override
    public void visitIntArray(IntArrayTag tag) {
        
        setValue(new IntArrayData(tag));
    }
    
    @Override
    public void visitLongArray(LongArrayTag tag) {
        
        setValue(new LongArrayData(tag));
    }
    
    @Override
    public void visitList(ListTag tag) {
        
        setValue(new ListData(tag));
    }
    
    @Override
    public void visitCompound(CompoundTag tag) {
        
        setValue(new MapData(tag));
    }
    
    @Override
    public void visitEnd(EndTag tag) {
        
        setValue(null);
    }
    
}
