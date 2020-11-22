package com.blamejared.crafttweaker.api.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InstantiationUtilTest {
    
    @Test
    public void testThatPublicStaticFieldIsUsedIfPresent() {
        final ClassWithOnlyField instance = InstantiationUtil.getOrCreateInstance(ClassWithOnlyField.class);
        assertNotNull(instance);
        assertEquals(ClassWithOnlyField.class, instance.getClass());
        assertEquals(ClassWithOnlyField.instance, instance, "getOrCreateInstance must return field content");
    }
    
    @Test
    public void testThatPublicConstructorIsUsedIfPresent() {
        final ClassWithOnlyConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithOnlyConstructor.class);
        assertNotNull(instance);
        assertEquals(ClassWithOnlyConstructor.class, instance.getClass());
    }
    
    @Test
    public void testThatPublicStaticFieldIsPreferredOverConstructor() {
        final ClassWithFieldAndConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithFieldAndConstructor.class);
        assertNotNull(instance);
        assertEquals(ClassWithFieldAndConstructor.class, instance.getClass());
        assertEquals(ClassWithFieldAndConstructor.instance, instance, "getOrCreateInstance must return field content");
    }
    
    @Test
    public void testThatMethodReturnsNullWhenNeitherFieldNorConstructorArePresent() {
        final ClassWithoutFieldOrConstructor instance = InstantiationUtil.getOrCreateInstance(ClassWithoutFieldOrConstructor.class);
        assertNull(instance);
    }
    
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final class ClassWithOnlyField {
        
        public static final ClassWithOnlyField instance = new ClassWithOnlyField();
    }
    
    public static final class ClassWithOnlyConstructor {
        
        public ClassWithOnlyConstructor() {
        }
    }
    
    public static final class ClassWithFieldAndConstructor {
        
        public static final ClassWithFieldAndConstructor instance = new ClassWithFieldAndConstructor();
        
        public ClassWithFieldAndConstructor() {
        }
    }
    
    public static final class ClassWithoutFieldOrConstructor {
        
        private ClassWithoutFieldOrConstructor() {
        }
    }
}