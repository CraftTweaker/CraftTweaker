package com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.rules;

import javax.lang.model.element.Element;

public interface VirtualTypeValidationRule {
    
    boolean canValidate(Element enclosedElement);
    
    void validate(Element enclosedElement);
    
}
