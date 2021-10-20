package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.converter;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.name_converter.NameConverter;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class NamedExpansionConverter {
    
    private final NameConverter nameConverter;
    
    public NamedExpansionConverter(NameConverter nameConverter) {
        
        this.nameConverter = nameConverter;
    }
    
    public ExpansionInfo convertNamedExpansion(TypeElement expansionType) {
        
        final TypeMirror expandedType = getExpandedType(expansionType);
        return new ExpansionInfo(expandedType, expansionType);
    }
    
    private TypeMirror getExpandedType(TypeElement expansionType) {
        
        final ZenCodeType.Expansion annotation = expansionType.getAnnotation(ZenCodeType.Expansion.class);
        final String expandedTypeName = annotation.value();
        
        return nameConverter.getTypeMirrorByZenCodeName(expandedTypeName);
    }
    
    
}
