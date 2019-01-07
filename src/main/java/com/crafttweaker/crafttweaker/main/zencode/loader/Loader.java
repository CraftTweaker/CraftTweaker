package com.crafttweaker.crafttweaker.main.zencode.loader;

import java.lang.reflect.*;

public class Loader extends ClassLoader {
    
    private static final Method findLoadedClassMethod;
    static {
        Method method;
        try {
            method = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            method.setAccessible(true);
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
            method = null;
        }
        
        findLoadedClassMethod = method;
    }
    
    public Loader(ClassLoader classLoader) {
        super(classLoader);
    }
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(findLoadedClassMethod != null) {
            try {
                Class<?> clazz = (Class<?>) findLoadedClassMethod.invoke(this.getParent(), name);
                if(clazz != null)
                    return clazz;
            } catch(IllegalAccessException | InvocationTargetException ignored) {
            }
        }
        return getParent().loadClass(name);
    }
    
}
