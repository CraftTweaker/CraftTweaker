package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.java.module.JavaNativeTypeConversionContext;
import org.openzen.zencode.java.module.converters.JavaNativeHeaderConverter;
import org.openzen.zencode.java.module.converters.JavaNativePackageInfo;
import org.openzen.zencode.java.module.converters.JavaNativeTypeConverter;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.expression.Expression;
import org.openzen.zenscript.codemodel.expression.InvalidAssignExpression;
import org.openzen.zenscript.codemodel.expression.InvalidExpression;
import org.openzen.zenscript.codemodel.expression.WrapOptionalExpression;
import org.openzen.zenscript.codemodel.type.TypeID;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Hack class that tries to re-evaluate the expression {@code @Optional("expression")} if it could not be evaluated the first time.
 *
 * Attempts to handle the case when the Optional value relies on Expansions.
 */
public class CrTJavaNativeHeaderConverter extends JavaNativeHeaderConverter {
    
    
    private static final List<LazyDefaultValueValue> lazyDefaultValueValueList = new ArrayList<>();
    
    public CrTJavaNativeHeaderConverter(JavaNativeTypeConverter typeConverter, JavaNativePackageInfo packageInfo, JavaNativeTypeConversionContext typeConversionContext) {
        
        super(typeConverter, packageInfo, typeConversionContext);
    }
    
    
    @Override
    public Expression getDefaultValue(Parameter parameter, TypeID type, FunctionParameter functionParameter) {
        
        final Expression defaultValue = super.getDefaultValue(parameter, type, functionParameter);
        
        //Null even if trying to parse => let's try again later
        if(isInvalid((defaultValue)) && parameter.isAnnotationPresent(ZenCodeType.Optional.class)) {
            lazyDefaultValueValueList.add(new LazyDefaultValueValue(parameter, type, functionParameter));
        }
        
        return defaultValue;
    }
    
    private boolean isInvalid(Expression expression) {
        if(expression instanceof WrapOptionalExpression) {
            return isInvalid(((WrapOptionalExpression) expression).value);
        }
        
        return expression == null || expression instanceof InvalidExpression || expression instanceof InvalidAssignExpression;
    }
    
    
    public void reinitializeAllLazyValues() {
        
        for(LazyDefaultValueValue lazyDefaultValueValue : lazyDefaultValueValueList) {
            reinitializeLazyValue(lazyDefaultValueValue);
        }
        
        lazyDefaultValueValueList.clear();
    }
    
    private void reinitializeLazyValue(LazyDefaultValueValue lazyDefaultValueValue) {
        
        final Parameter parameter = lazyDefaultValueValue.parameter;
        final TypeID typeID = lazyDefaultValueValue.typeID;
        final FunctionParameter functionParameter = lazyDefaultValueValue.functionParameter;
        functionParameter.defaultValue = super.getDefaultValue(parameter, typeID, functionParameter);
    }
    
}
