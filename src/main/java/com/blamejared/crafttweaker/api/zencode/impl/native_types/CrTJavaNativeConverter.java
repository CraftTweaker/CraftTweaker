package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeClassConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverter;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeGlobalConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;

class CrTJavaNativeConverter extends JavaNativeConverter {
    
    public CrTJavaNativeConverter(JavaNativeTypeConverter typeConverter, JavaNativeHeaderConverter headerConverter, JavaNativeMemberConverter memberConverter, JavaNativeClassConverter classConverter, JavaNativeGlobalConverter globalConverter, JavaNativeExpansionConverter expansionConverter, JavaNativeTypeConversionContext typeConversionContext) {
        
        super(typeConverter, headerConverter, memberConverter, classConverter, globalConverter, expansionConverter, typeConversionContext);
    }
    
    @Override
    public HighLevelDefinition addClass(Class<?> cls) {
        
        try {
            if(cls.isAnnotationPresent(NativeTypeRegistration.class)) {
                return expansionConverter.convertExpansion(cls);
            }
            
            return super.addClass(cls);
        } catch(Throwable e) {
            CraftTweakerAPI.logThrowing("Error while registering class: '%s', this is most likely a compatibility issue:", e, cls.getName());
            return null;
        }
    }
    
}
