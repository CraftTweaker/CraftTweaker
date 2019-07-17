package com.blamejared.crafttweaker.api.nbt;

import com.blamejared.crafttweaker.impl.nbt.ByteArrayData;
import com.blamejared.crafttweaker.impl.nbt.ByteData;
import com.blamejared.crafttweaker.impl.nbt.DoubleData;
import com.blamejared.crafttweaker.impl.nbt.FloatData;
import com.blamejared.crafttweaker.impl.nbt.IntArrayData;
import com.blamejared.crafttweaker.impl.nbt.IntData;
import com.blamejared.crafttweaker.impl.nbt.ListData;
import com.blamejared.crafttweaker.impl.nbt.LongArrayData;
import com.blamejared.crafttweaker.impl.nbt.LongData;
import com.blamejared.crafttweaker.impl.nbt.MapData;
import com.blamejared.crafttweaker.impl.nbt.ShortData;
import com.blamejared.crafttweaker.impl.nbt.StringData;
import net.minecraft.nbt.ByteArrayNBT;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.LongArrayNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.nbt.ShortNBT;
import net.minecraft.nbt.StringNBT;

public class NBTConverter {
    
    public static IData convert(INBT nbt) {
        if(nbt == null) {
            return null;
        }
        switch(nbt.getId()) {
            case 1:
                return new ByteData((ByteNBT) nbt);
            case 2:
                return new ShortData((ShortNBT) nbt);
            case 3:
                return new IntData((IntNBT) nbt);
            case 4:
                return new LongData((LongNBT) nbt);
            case 5:
                return new FloatData((FloatNBT) nbt);
            case 6:
                return new DoubleData((DoubleNBT) nbt);
            case 7:
                return new ByteArrayData((ByteArrayNBT) nbt);
            case 8:
                return new StringData((StringNBT) nbt);
            case 9:
                return new ListData((ListNBT) nbt);
            case 10:
                return new MapData((CompoundNBT) nbt);
            case 11:
                return new IntArrayData((IntArrayNBT) nbt);
            case 12:
                return new LongArrayData((LongArrayNBT) nbt);
            default:
                return null;
        }
    }
    
}
