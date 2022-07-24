package com.blamejared.crafttweaker.gametest.framework;

import com.blamejared.crafttweaker.gametest.framework.annotation.TestModifier;

import java.lang.reflect.Method;

public class Modifier {
    
    private boolean implicitSuccession;
    
    private Modifier() {
        
        this.implicitSuccession = false;
    }
    
    public static Modifier empty() {
        
        return new Modifier();
    }
    
    public static Modifier from(Method method) {
        
        Modifier mod = empty();
        if(!method.isAnnotationPresent(TestModifier.class)) {
            return mod;
        }
        TestModifier modifier = method.getAnnotation(TestModifier.class);
        mod.setImplicitSuccession(modifier.implicitSuccession());
        return mod;
    }
    
    
    public static Modifier from(TestModifier modifier) {
        
        Modifier mod = empty();
        mod.setImplicitSuccession(modifier.implicitSuccession());
        return mod;
    }
    
    public Modifier merge(Modifier modifier) {
        
        Modifier mod = empty();
        mod.setImplicitSuccession(this.isImplicitSuccession() & modifier.isImplicitSuccession());
        return mod;
    }
    
    public Modifier merge(TestModifier modifier) {
        
        return merge(from(modifier));
    }
    
    public boolean isImplicitSuccession() {
        
        return implicitSuccession;
    }
    
    public void setImplicitSuccession(boolean implicitSuccession) {
        
        this.implicitSuccession = implicitSuccession;
    }
    
}
