package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.NativeConversionRegistry;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.TypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class NativeTypeConversionRule implements TypeConversionRule {
    
    private final NativeConversionRegistry nativeConversionRegistry;
    private final Types typeUtils;
    
    public NativeTypeConversionRule(NativeConversionRegistry nativeConversionRegistry, Types typeUtils) {
        this.nativeConversionRegistry = nativeConversionRegistry;
        this.typeUtils = typeUtils;
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return isNativeType(mirror);
    }
    
    private boolean isNativeType(TypeMirror mirror) {
        return isTypeElement(mirror) && isTypeElementNative(mirror);
    }
    
    private boolean isTypeElementNative(TypeMirror mirror) {
        final TypeElement element = getTypeElement(mirror);
        return nativeConversionRegistry.hasNativeTypeInfo(element);
    }
    
    private TypeElement getTypeElement(TypeMirror mirror) {
        return (TypeElement) typeUtils.asElement(mirror);
    }
    
    private boolean isTypeElement(TypeMirror mirror) {
        final Element element = typeUtils.asElement(mirror);
        return element instanceof TypeElement;
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        final TypeElement nativeType = getTypeElement(mirror);
        return nativeConversionRegistry.getNativeTypeInfo(nativeType);
    }
}
