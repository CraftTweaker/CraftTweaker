package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.type.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.*;
import org.jetbrains.annotations.*;

import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;

public class JavaFunctionConversionRule implements TypeConversionRule {
    
    private final Types typeUtils;
    
    public JavaFunctionConversionRule(Types typeUtils) {
        this.typeUtils = typeUtils;
    }
    
    @Override
    public boolean canConvert(TypeMirror mirror) {
        return mirror.toString().startsWith("java.util.function");
    }
    
    @Nullable
    @Override
    public AbstractTypeInfo convert(TypeMirror mirror) {
        final Element element = typeUtils.asElement(mirror);
        return new ExistingTypeInfo(element.getSimpleName().toString());
    }
}
