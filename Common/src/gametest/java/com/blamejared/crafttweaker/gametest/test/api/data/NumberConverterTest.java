package com.blamejared.crafttweaker.gametest.test.api.data;

import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.converter.NumberConverter;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.TestModifier;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

import java.math.BigDecimal;

@CraftTweakerGameTestHolder
public class NumberConverterTest implements CraftTweakerGameTest {
    
    @SuppressWarnings("ConstantConditions")
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void nullReturnsNull(GameTestHelper helper) {
        //Arrange - none
        //Act
        final INumberData convert = NumberConverter.convertNumber(null);
        
        //Assert
        assertThat(convert).isNull();
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void integerReturnsIntegerData(GameTestHelper helper) {
        //Arrange
        final int value = 13;
        
        //Act
        final INumberData convert = NumberConverter.convertNumber(value);
        
        //Assert
        assertThat(convert).isEqualTo(new IntData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void byteReturnsByteData(GameTestHelper helper) {
        //Arrange
        final byte value = 13;
        
        //Act
        final INumberData convert = NumberConverter.convertNumber(value);
        
        //Assert
        assertThat(convert).isEqualTo(new ByteData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void doubleReturnsDoubleData(GameTestHelper helper) {
        //Arrange
        final double value = 13;
        
        //Act
        final INumberData convert = NumberConverter.convertNumber(value);
        
        //Assert
        assertThat(convert).isEqualTo(new DoubleData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void floatReturnsFloatData(GameTestHelper helper) {
        //Arrange
        final float value = 13;
        
        //Act
        final INumberData convert = NumberConverter.convertNumber(value);
        
        //Assert
        assertThat(convert).isEqualTo(new FloatData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void longReturnsLongData(GameTestHelper helper) {
        //Arrange
        final long value = 13;
        
        //Act
        final INumberData convert = NumberConverter.convertNumber(value);
        
        //Assert
        assertThat(convert).isEqualTo(new LongData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void shortReturnsShortData(GameTestHelper helper) {
        //Arrange
        final short value = 13;
        
        //Act
        final INumberData convert = NumberConverter.convertNumber(value);
        
        //Assert
        assertThat(convert).isEqualTo(new ShortData(value));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void unknownNumberTypeReturnsDoubleData(GameTestHelper helper) {
        //Arrange
        final double doubleValue = 123;
        final BigDecimal bigDecimal = new BigDecimal(doubleValue);
        
        //Act
        final INumberData convert = NumberConverter.convertNumber(bigDecimal);
        
        //Assert
        
        assertThat(convert).isInstanceOf(DoubleData.class);
        DoubleData doubleData = (DoubleData) convert;
        assertThat(doubleData).isEqualTo(new DoubleData(doubleValue));
    }
    
}
