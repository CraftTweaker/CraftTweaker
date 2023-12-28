package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TypedExpansion;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeExpansionConverter;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativeMemberConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zencode.shared.logging.IZSLogger;

import java.util.Map;
import java.util.stream.Collectors;

final class CtJavaNativeExpansionConverter extends JavaNativeExpansionConverter {
    
    private final Map<Class<?>, String> expansionTargets;
    
    CtJavaNativeExpansionConverter(
            final JavaNativeTypeConverter typeConverter,
            final IZSLogger logger,
            final JavaNativePackageInfo packageInfo,
            final JavaNativeMemberConverter memberConverter,
            final JavaNativeTypeConversionContext typeConversionContext,
            final JavaNativeHeaderConverter headerConverter,
            final IScriptRunInfo info,
            final IZenClassRegistry registry
    ) {
        
        super(typeConverter, logger, packageInfo, memberConverter, typeConversionContext, headerConverter);
        this.expansionTargets = buildExpansionTargetsFrom(info, registry);
    }
    
    private static Map<Class<?>, String> buildExpansionTargetsFrom(final IScriptRunInfo info, final IZenClassRegistry registry) {
        
        // TODO("Move away from IClassData")
        return registry.getClassData(info.loader())
                .expansions()
                .entries()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
    
    @Override
    protected String getExpandedName(final Class<?> cls) {
        
        // TODO("Might be interesting to move away from annotations completely in here")
        if(cls.isAnnotationPresent(NativeTypeRegistration.class) || cls.isAnnotationPresent(TypedExpansion.class)) {
            
            final String name = this.expansionTargets.get(cls);
            return name == null ? super.getExpandedName(cls) : name;
        }
        return super.getExpandedName(cls);
    }
    
    @Override
    protected boolean doesClassNotHaveAnnotation(final Class<?> cls) {
        
        return !cls.isAnnotationPresent(NativeTypeRegistration.class)
                && !cls.isAnnotationPresent(TypedExpansion.class)
                && super.doesClassNotHaveAnnotation(cls);
    }
    
}
