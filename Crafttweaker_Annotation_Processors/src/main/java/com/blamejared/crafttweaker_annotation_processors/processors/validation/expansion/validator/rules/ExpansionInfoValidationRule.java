package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.validator.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;

import javax.lang.model.element.Element;

public interface ExpansionInfoValidationRule {
    
    boolean canValidate(Element enclosedElement);
    
    void validate(Element enclosedElement, ExpansionInfo expansionInfo);
    
}
