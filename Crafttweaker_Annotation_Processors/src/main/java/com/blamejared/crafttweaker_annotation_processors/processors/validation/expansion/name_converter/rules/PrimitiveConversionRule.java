package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConversionRule;

import javax.annotation.Nullable;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.HashMap;
import java.util.Map;

public class PrimitiveConversionRule implements NameConversionRule {
    
    private final Map<String, TypeKind> primitiveTypes = new HashMap<>();
    private final Types typeUtils;
    private final Elements elementUtils;
    
    public PrimitiveConversionRule(Types typeUtils, Elements elementUtils) {
        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        fillPrimitiveTypeMap();
    }
    
    private void fillPrimitiveTypeMap() {
        fillSigned();
        fillUnsigned();
    }
    
    private void fillSigned() {
        primitiveTypes.put("bool", TypeKind.BOOLEAN);
        primitiveTypes.put("byte", TypeKind.BYTE);
        primitiveTypes.put("char", TypeKind.CHAR);
        primitiveTypes.put("short", TypeKind.SHORT);
        primitiveTypes.put("int", TypeKind.INT);
        primitiveTypes.put("long", TypeKind.LONG);
        primitiveTypes.put("float", TypeKind.FLOAT);
        primitiveTypes.put("double", TypeKind.DOUBLE);
    }
    
    private void fillUnsigned() {
        primitiveTypes.put("ushort", TypeKind.SHORT);
        primitiveTypes.put("uint", TypeKind.INT);
        primitiveTypes.put("ulong", TypeKind.LONG);
    }
    
    @Nullable
    @Override
    public TypeMirror convertZenCodeName(String zenCodeName) {
        if(primitiveTypes.containsKey(zenCodeName)) {
            return getMirrorFromClass(primitiveTypes.get(zenCodeName));
        } else if(zenCodeName.equals("string")) {
            return getString();
        }
        return null;
    }
    
    private TypeMirror getString() {
        final String canonicalName = String.class.getCanonicalName();
        return elementUtils.getTypeElement(canonicalName).asType();
    }
    
    private TypeMirror getMirrorFromClass(TypeKind kind) {
        return typeUtils.getPrimitiveType(kind);
    }
}
