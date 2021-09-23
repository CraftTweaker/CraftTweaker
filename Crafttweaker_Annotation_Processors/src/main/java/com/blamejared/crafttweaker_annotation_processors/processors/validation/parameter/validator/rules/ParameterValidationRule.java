package com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator.rules;

import javax.lang.model.element.VariableElement;

public interface ParameterValidationRule {
    
    boolean canValidate(VariableElement parameter);
    
    void validate(VariableElement parameter);
    
}
