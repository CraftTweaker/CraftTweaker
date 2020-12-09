package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import net.minecraft.item.ItemStack;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;
import org.openzen.zenscript.codemodel.HighLevelDefinition;

public class CrTJavaNativeClassConverter extends JavaNativeClassConverter {
    
    public CrTJavaNativeClassConverter(JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext, JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter) {
        super(typeConverter, memberConverter, packageInfo, typeConversionContext, headerConverter);
    }
    
    @Override
    public HighLevelDefinition convertClass(Class<?> cls) {
        return super.convertClass(cls);
    }
    
    @Override
    public String getNameForScripts(Class<?> cls) {
        if(cls == ItemStack.class) {
            return "crafttweaker.ItemStack";
        }
        
        return super.getNameForScripts(cls);
    }
    
    @Override
    public boolean shouldLoadClass(Class<?> cls) {
        return super.shouldLoadClass(cls);
    }
}
