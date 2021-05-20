package com.blamejared.crafttweaker.api.zencode.impl.native_types;

import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.type.TypeID;

import java.lang.reflect.Parameter;

public class LazyDefaultValueValue {
    public final Parameter parameter;
    public final TypeID typeID;
    public final FunctionParameter functionParameter;
    
    public LazyDefaultValueValue(Parameter parameter, TypeID typeID, FunctionParameter functionParameter) {
        this.parameter = parameter;
        this.typeID = typeID;
        this.functionParameter = functionParameter;
    }
    
}
