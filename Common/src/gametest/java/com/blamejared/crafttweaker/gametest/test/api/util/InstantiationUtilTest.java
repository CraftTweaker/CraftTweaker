package com.blamejared.crafttweaker.gametest.test.api.util;

import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.gametest.CraftTweakerGameTest;
import com.blamejared.crafttweaker.gametest.framework.annotation.CraftTweakerGameTestHolder;
import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithFieldAndConstructor;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithOnlyConstructor;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithOnlyField;
import com.blamejared.crafttweaker.gametest.test.stub.ClassWithoutFieldOrConstructor;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@SuppressWarnings("ConstantConditions")
@CraftTweakerGameTestHolder
public class InstantiationUtilTest implements CraftTweakerGameTest {
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatPublicStaticFieldIsUsedIfPresent(GameTestHelper helper) {
        
        final ClassWithOnlyField instance = InstantiationUtil.getOrCreateInstance(ClassWithOnlyField.class);
        assertThat(instance, is(notNullValue()));
        assertThat(instance.getClass(), is(ClassWithOnlyField.class));
        assertThat("getOrCreateInstance must return field content", instance, is(ClassWithOnlyField.INSTANCE));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatPublicConstructorIsUsedIfPresent(GameTestHelper helper) {
        
        final ClassWithOnlyConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithOnlyConstructor.class);
        assertThat(instance, is(notNullValue()));
        assertThat(instance.getClass(), is(ClassWithOnlyConstructor.class));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatPublicStaticFieldIsPreferredOverConstructor(GameTestHelper helper) {
        
        final ClassWithFieldAndConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithFieldAndConstructor.class);
        assertThat(instance, is(notNullValue()));
        assertThat(instance.getClass(), is(ClassWithFieldAndConstructor.class));
        assertThat("getOrCreateInstance must return field content", instance, is(ClassWithFieldAndConstructor.INSTANCE));
    }
    
    @GameTest(template = "crafttweaker:empty")
    @TestModifier(implicitSuccession = true)
    public void testThatMethodReturnsNullWhenNeitherFieldNorConstructorArePresent(GameTestHelper helper) {
        
        final ClassWithoutFieldOrConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithoutFieldOrConstructor.class);
        assertThat(instance, is(nullValue()));
    }
    
}