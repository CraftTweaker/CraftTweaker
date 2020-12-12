package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.*;
import org.openzen.zenscript.codemodel.HighLevelDefinition;

class CrTJavaNativeConverter extends JavaNativeConverter {
    
    public CrTJavaNativeConverter(JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter, JavaNativeClassConverter classConverter, JavaNativeGlobalConverter globalConverter, JavaNativeExpansionConverter expansionConverter, JavaNativeTypeConversionContext typeConversionContext) {
        super(typeConverter, headerConverter, memberConverter, classConverter, globalConverter, expansionConverter, typeConversionContext);
    }
    
    @Override
    public HighLevelDefinition addClass(Class<?> cls) {
        if(cls.isAnnotationPresent(NativeExpansion.class)) {
            return expansionConverter.convertExpansion(cls);
        }
        
        return super.addClass(cls);
    }
}
