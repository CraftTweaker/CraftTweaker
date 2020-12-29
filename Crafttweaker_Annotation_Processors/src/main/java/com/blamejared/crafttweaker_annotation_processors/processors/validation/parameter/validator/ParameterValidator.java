package com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.IHasPostCreationCall;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator.rules.OptionalTypeValidationRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator.rules.OptionalsNeedToGoLastRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.parameter.validator.rules.ParameterValidationRule;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;

public class ParameterValidator implements IHasPostCreationCall {
    
    private final List<ParameterValidationRule> rules = new ArrayList<>();
    private final DependencyContainer dependencyContainer;
    
    public ParameterValidator(DependencyContainer dependencyContainer) {
        this.dependencyContainer = dependencyContainer;
    }
    
    public void validate(Element element) {
        assertIsParameter(element);
        validate((VariableElement) element);
    }
    
    private void validate(VariableElement parameter) {
        this.rules.stream()
                .filter(rule -> rule.canValidate(parameter))
                .forEach(rule -> rule.validate(parameter));
    }
    
    private void assertIsParameter(Element element) {
        assertIsVariableElement(element);
        assertParentIsExecutable(element);
    }
    
    private void assertIsVariableElement(Element element) {
        if(element.getKind() != ElementKind.PARAMETER) {
            throw new IllegalArgumentException("Element not a method parameter: " + element);
        }
    }
    
    private void assertParentIsExecutable(Element element) {
        final ElementKind kind = element.getEnclosingElement().getKind();
        if(kind != ElementKind.METHOD && kind != ElementKind.CONSTRUCTOR) {
            throw new IllegalArgumentException("Element not a method parameter: " + element);
        }
    }
    
    @Override
    public void afterCreation() {
        addRule(OptionalsNeedToGoLastRule.class);
        addRule(OptionalTypeValidationRule.class);
    }
    
    private void addRule(Class<? extends ParameterValidationRule> ruleClass) {
        final ParameterValidationRule rule = dependencyContainer.getInstanceOfClass(ruleClass);
        rules.add(rule);
    }
}
