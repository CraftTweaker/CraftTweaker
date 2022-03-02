package com.blamejared.crafttweaker.impl.script.scriptrun.natives;

import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.type.TypeID;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Hack class that tries to re-evaluate the expression {@code @Optional("expression")} if it could not be evaluated the first time.
 *
 * Attempts to handle the case when the Optional value relies on Expansions.
 */
final class CtJavaNativeHeaderConverter extends JavaNativeHeaderConverter {
    
    private final List<DefaultedLazyValue> lazyValues;
    
    CtJavaNativeHeaderConverter(
            final JavaNativeTypeConverter typeConverter,
            final JavaNativePackageInfo packageInfo,
            final JavaNativeTypeConversionContext typeConversionContext
    ) {
        
        super(typeConverter, packageInfo, typeConversionContext);
        this.lazyValues = new ArrayList<>();
    }
    
    
    @Override
    public Expression getDefaultValue(final Parameter parameter, final TypeID type, final FunctionParameter functionParameter) {
        
        if(parameter.isAnnotationPresent(ZenCodeType.Optional.class)) {
            
            this.lazyValues.add(new DefaultedLazyValue(parameter, type, functionParameter));
            return null;
        }
        
        return super.getDefaultValue(parameter, type, functionParameter);
    }
    
    void reinitializeAllLazyValues() {
        
        this.lazyValues.forEach(this::reinitializeLazyValue);
        this.lazyValues.clear();
    }
    
    private void reinitializeLazyValue(final DefaultedLazyValue lazyDefaultValueValue) {
        
        final Parameter parameter = lazyDefaultValueValue.parameter();
        final TypeID typeID = lazyDefaultValueValue.typeId();
        final FunctionParameter functionParameter = lazyDefaultValueValue.functionParameter();
        
        functionParameter.defaultValue = super.getDefaultValue(parameter, typeID, functionParameter);
    }
    
}
