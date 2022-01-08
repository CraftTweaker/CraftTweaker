package com.blamejared.crafttweaker.gametest.test.api.util;

import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.TestModifier;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithFieldAndConstructor;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithOnlyConstructor;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithOnlyField;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithoutFieldOrConstructor;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings("ConstantConditions")
@CraftTweakerGameTestHolder
public class InstantiationUtilTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatPublicStaticFieldIsUsedIfPresent(GameTestHelper helper) {
        
        final ClassWithOnlyField instance = InstantiationUtil.getOrCreateInstance(ClassWithOnlyField.class);
        assertThat(instance)
                .isNotNull();
        assertThat(instance.getClass())
                .isEqualTo(ClassWithOnlyField.class);
        assertWithMessage("getOrCreateInstance must return field content")
                .that(instance)
                .isEqualTo(ClassWithOnlyField.INSTANCE);
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatPublicConstructorIsUsedIfPresent(GameTestHelper helper) {
        
        final ClassWithOnlyConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithOnlyConstructor.class);
        assertThat(instance)
                .isNotNull();
        assertThat(instance.getClass())
                .isEqualTo(ClassWithOnlyConstructor.class);
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatPublicStaticFieldIsPreferredOverConstructor(GameTestHelper helper) {
        
        final ClassWithFieldAndConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithFieldAndConstructor.class);
        assertThat(instance)
                .isNotNull();
        assertThat(instance.getClass())
                .isEqualTo(ClassWithFieldAndConstructor.class);
        assertWithMessage("getOrCreateInstance must return field content")
                .that(instance)
                .isEqualTo(ClassWithFieldAndConstructor.INSTANCE);
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatMethodReturnsNullWhenNeitherFieldNorConstructorArePresent(GameTestHelper helper) {
        
        final ClassWithoutFieldOrConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithoutFieldOrConstructor.class);
        assertThat(instance)
                .isNull();
    }
    
}