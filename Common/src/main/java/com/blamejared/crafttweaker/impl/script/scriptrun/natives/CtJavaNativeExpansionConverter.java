package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zencode.shared.logging.IZSLogger;

final class CtJavaNativeExpansionConverter extends JavaNativeExpansionConverter {
    
    CtJavaNativeExpansionConverter(
            final JavaNativeTypeConverter typeConverter,
            final IZSLogger logger,
            final JavaNativePackageInfo packageInfo,
            final JavaNativeMemberConverter memberConverter,
            final JavaNativeTypeConversionContext typeConversionContext,
            final JavaNativeHeaderConverter headerConverter
    ) {
        
        super(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
    }
    
    @Override
    protected String getExpandedName(final Class<?> cls) {
        
        if(cls.isAnnotationPresent(NativeTypeRegistration.class)) {
            final NativeTypeRegistration annotation = cls.getAnnotation(NativeTypeRegistration.class);
            return annotation.zenCodeName();
        }
        return super.getExpandedName(cls);
    }
    
    @Override
    protected boolean doesClassNotHaveAnnotation(final Class<?> cls) {
        
        return !cls.isAnnotationPresent(NativeTypeRegistration.class) && super.doesClassNotHaveAnnotation(cls);
    }
    
}
