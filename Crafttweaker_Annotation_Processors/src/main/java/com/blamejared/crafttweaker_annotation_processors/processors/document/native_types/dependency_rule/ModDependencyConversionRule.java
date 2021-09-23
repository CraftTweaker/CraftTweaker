package com.blamejared.crafttweaker_annotation_processors.processors.document.native_types.dependency_rule;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface ModDependencyConversionRule {
    
    Map<TypeElement, AbstractTypeInfo> getAll();
    
}
