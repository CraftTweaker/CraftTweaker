package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.impl.native_types.NativeTypeRegistry;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zencode.shared.logging.IZSLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class CrTJavaNativeExpansionConverter extends JavaNativeExpansionConverter {
    
    
    public CrTJavaNativeExpansionConverter(JavaNativeTypeConverter typeConverter, IZSLogger logger, JavaNativePackageInfo packageInfo, JavaNativeMemberConverter memberConverter, JavaNativeTypeConversionContext typeConversionContext, JavaNativeHeaderConverter headerConverter) {
        
        super(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
    }
    
    @Override
    protected <T extends Annotation> T getMethodAnnotation(Method method, Class<T> annotationClass) {
        
        return super.getMethodAnnotation(method, annotationClass);
    }
    
    @Override
    protected String getExpandedName(Class<?> cls) {
        
        if(cls.isAnnotationPresent(NativeTypeRegistration.class)) {
            final NativeTypeRegistration annotation = cls.getAnnotation(NativeTypeRegistration.class);
            return annotation.zenCodeName();
        }
        if (cls.isAnnotationPresent(TypedExpansion.class)) {
            final TypedExpansion annotation = cls.getAnnotation(TypedExpansion.class);
            final Class<?> expandedType = annotation.value();
            NativeTypeRegistry nativeTypeRegistry = CraftTweakerRegistry.getZenClassRegistry().getNativeTypeRegistry();
            if (nativeTypeRegistry.hasInfoFor(expandedType)) {
                return nativeTypeRegistry.getCrTNameFor(expandedType);
            } else if (expandedType.isAnnotationPresent(ZenCodeType.Name.class)) {
                final ZenCodeType.Name nameAnnotation = expandedType.getAnnotation(ZenCodeType.Name.class);
                return nameAnnotation.value();
            }
            final String expandedTypeClassName = expandedType.getCanonicalName();
            throw new IllegalArgumentException("Could not find expansion for type " + expandedTypeClassName);
        }
        return super.getExpandedName(cls);
    }
    
    @Override
    protected boolean doesClassNotHaveAnnotation(Class<?> cls) {
        
        return !cls.isAnnotationPresent(NativeTypeRegistration.class) && !cls.isAnnotationPresent(TypedExpansion.class) && super.doesClassNotHaveAnnotation(cls);
    }
    
}
