package com.blamejared.crafttweaker_annotation_processors.processors.document_again.native_types;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface NativeTypeProvider {
    
    Map<TypeElement, AbstractTypeInfo> getTypeInfos();
}
