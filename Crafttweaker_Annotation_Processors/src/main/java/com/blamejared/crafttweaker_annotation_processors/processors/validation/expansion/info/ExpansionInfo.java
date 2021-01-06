package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class ExpansionInfo {
    public final TypeMirror expandedType;
    public final TypeElement expansionType;
    
    public ExpansionInfo(TypeMirror expandedType, TypeElement expansionType) {
        this.expandedType = expandedType;
        this.expansionType = expansionType;
    }
}
