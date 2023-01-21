package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeClassConverter;
import org.openzen.zencode.java.module.converters.JavaNativeConverter;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeGlobalConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;

final class CtJavaNativeConverter extends JavaNativeConverter {
    
    CtJavaNativeConverter(
            final JavaNativeTypeConverter typeConverter,
            final JavaNativeHeaderConverter headerConverter,
            final JavaNativeMemberConverter memberConverter,
            final JavaNativeClassConverter classConverter,
            final JavaNativeGlobalConverter globalConverter,
            final JavaNativeExpansionConverter expansionConverter,
            final JavaNativeTypeConversionContext typeConversionContext
    ) {
        
        super(typeConverter, headerConverter, memberConverter, classConverter, globalConverter, expansionConverter, typeConversionContext);
    }
    
    @Override
    public HighLevelDefinition addClass(final Class<?> cls) {
        
        try {
            if(cls.isAnnotationPresent(NativeTypeRegistration.class)) {
                return this.expansionConverter.convertExpansion(cls);
            }
            if(cls.isAnnotationPresent(TypedExpansion.class)) {
                return this.expansionConverter.convertExpansion(cls);
            }
            
            return super.addClass(cls);
        } catch(final Throwable e) {
            CommonLoggers.zenCode()
                    .error("Error while registering class: '{}', this is most likely a compatibility issue:", cls.getName(), e);
            return null;
        }
    }
    
}
