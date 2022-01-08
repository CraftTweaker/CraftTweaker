package com.blamejared.crafttweaker.gametest.test.api.data;

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
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.TestModifier;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;

@SuppressWarnings("ConstantConditions")
@CraftTweakerGameTestHolder
public class TagToDataConverterTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nullReturnsNull(GameTestHelper helper) {
        //Arrange - none
        //Act
        final IData convert = TagToDataConverter.convert(null);
        
        //Assert
        assertThat(convert).isNull();
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void byteNBTReturnsByteData(GameTestHelper helper) {
        //Arrange
        final byte value = 13;
        final ByteTag byteNBT = ByteTag.valueOf(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(byteNBT);
        
        //Assert
        assertThat(convert).isEqualTo(new ByteData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void shortNBTReturnsShortData(GameTestHelper helper) {
        //Arrange
        final short value = 15;
        final ShortTag shortNbt = ShortTag.valueOf(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(shortNbt);
        
        //Assert
        assertThat(convert).isEqualTo(new ShortData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void intNBTReturnsIntData(GameTestHelper helper) {
        //Arrange
        final int value = 15;
        final IntTag intNbt = IntTag.valueOf(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(intNbt);
        
        //Assert
        assertThat(convert).isEqualTo(new IntData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void longNBTReturnsLongData(GameTestHelper helper) {
        //Arrange
        final long value = 15;
        final LongTag longNbt = LongTag.valueOf(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(longNbt);
        
        //Assert
        assertThat(convert).isEqualTo(new LongData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void floatNBTReturnsFloatData(GameTestHelper helper) {
        //Arrange
        final float value = 15;
        final FloatTag floatNbt = FloatTag.valueOf(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(floatNbt);
        
        //Assert
        assertThat(convert).isEqualTo(new FloatData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void doubleNBTReturnsDoubleData(GameTestHelper helper) {
        //Arrange
        final double value = 15;
        final DoubleTag doubleNbt = DoubleTag.valueOf(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(doubleNbt);
        
        //Assert
        assertThat(convert).isEqualTo(new DoubleData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void stringNBTReturnsStringData(GameTestHelper helper) {
        //Arrange
        final String value = "Hello World";
        final StringTag stringNBT = StringTag.valueOf(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(stringNBT);
        
        //Assert
        assertThat(convert).isEqualTo(new StringData(value));
    }
    
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void byteArrayNBTReturnsByteArrayData(GameTestHelper helper) {
        //Arrange
        final byte[] value = new byte[] {1, 2, 3};
        final ByteArrayTag byteArrayNBT = new ByteArrayTag(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(byteArrayNBT);
        
        //Assert
        assertThat(convert).isEqualTo(new ByteArrayData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void intArrayNBTReturnsIntArrayData(GameTestHelper helper) {
        //Arrange
        final int[] value = new int[] {1, 2, 3};
        final IntArrayTag intArrayNBT = new IntArrayTag(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(intArrayNBT);
        
        //Assert
        assertThat(convert).isEqualTo(new IntArrayData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void longArrayNBTReturnsLongArrayData(GameTestHelper helper) {
        //Arrange
        final long[] value = new long[] {1, 2, 3};
        final LongArrayTag longArrayNBT = new LongArrayTag(value);
        
        //Act
        final IData convert = TagToDataConverter.convert(longArrayNBT);
        
        //Assert
        assertThat(convert).isEqualTo(new LongArrayData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void listNBTReturnsListData(GameTestHelper helper) {
        //Arrange
        final ListTag listNBT = new ListTag();
        listNBT.add(IntTag.valueOf(1));
        listNBT.add(IntTag.valueOf(2));
        listNBT.add(IntTag.valueOf(3));
        
        //Act
        final IData convert = TagToDataConverter.convert(listNBT);
        
        //Assert
        assertThat(convert).isEqualTo(new ListData(listNBT));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void compoundNBTReturnsMapData(GameTestHelper helper) {
        //Arrange
        final CompoundTag compoundNBT = new CompoundTag();
        compoundNBT.putInt("Hello", 0);
        compoundNBT.putInt("World", 1);
        
        //Act
        final IData convert = TagToDataConverter.convert(compoundNBT);
        
        assertThat(convert).isEqualTo(new MapData(compoundNBT));
    }
    
}