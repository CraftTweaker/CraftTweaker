package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import org.openzen.zencode.java.ZenCodeGlobals;

import java.lang.reflect.InvocationTargetException;

@ZenRegister
public class CraftTweakerGlobals {
    
    @ZenCodeGlobals.Global
    public static void println(String msg) {
        CraftTweakerAPI.logger.info(msg);
    }
    
    @ZenCodeGlobals.Global
    public static void print(String msg) {
        println(msg);
    }

    @ZenCodeGlobals.Global
    public static boolean isNull(Object obj) {
        return isNull(obj, false);
    }

    @ZenCodeGlobals.Global
    public static boolean nonNull(Object obj) {
        return nonNull(obj, false);
    }

    @ZenCodeGlobals.Global
    public static boolean isNull(Object obj, boolean checkInternal) {
        if (!checkInternal) return obj == null;
        if (obj == null) {
            return true;
        } else {
            Class<?> clazz = obj.getClass();
            if (clazz.isAnnotationPresent(ZenWrapper.class)) {
                String methodName = clazz.getAnnotation(ZenWrapper.class).conversionMethodFormat();
                methodName = methodName.substring(3, methodName.length() - 2);
                try {
                    return clazz.getMethod(methodName).invoke(obj) == null;
                } catch (NoSuchMethodException | IllegalAccessException e) {
                    CraftTweakerAPI.logError("%s doesn't have a proper conversion method!", obj.getClass().getCanonicalName());
                    e.printStackTrace();
                    return false;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @ZenCodeGlobals.Global
    public static boolean nonNull(Object obj, boolean checkInternal) {
        return !isNull(obj, checkInternal);
    }
}