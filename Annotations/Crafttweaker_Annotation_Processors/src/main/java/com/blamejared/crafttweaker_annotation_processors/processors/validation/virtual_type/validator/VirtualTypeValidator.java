package com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator;

import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.util.dependencies.IHasPostCreationCall;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.rules.ModifierValidationRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.rules.OperatorParameterCountValidationRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.rules.ParameterCountValidationRule;
import com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.rules.VirtualTypeValidationRule;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VirtualTypeValidator implements IHasPostCreationCall {
    
    private final List<VirtualTypeValidationRule> rules = new ArrayList<>();
    private final DependencyContainer dependencyContainer;
    
    public VirtualTypeValidator(DependencyContainer dependencyContainer) {
        
        this.dependencyContainer = dependencyContainer;
    }
    
    public void validateAll(Collection<? extends Element> elements) {
        
        for(Element element : elements) {
            if(!isTypeElement(element)) {
                throw new IllegalArgumentException("Invalid typeElement annotated: " + element);
            }
            validate((TypeElement) element);
        }
    }
    
    private boolean isTypeElement(Element element) {
        
        return element.getKind().isClass() || element.getKind().isInterface();
    }
    
    private void validate(TypeElement element) {
        
        for(Element enclosedElement : element.getEnclosedElements()) {
            validateEnclosed(enclosedElement);
        }
    }
    
    private void validateEnclosed(Element enclosedElement) {
        
        for(VirtualTypeValidationRule rule : rules) {
            if(rule.canValidate(enclosedElement)) {
                rule.validate(enclosedElement);
            }
        }
    }
    
    @Override
    public void afterCreation() {
        
        addRule(ModifierValidationRule.class);
        addRule(OperatorParameterCountValidationRule.class);
        addRule(ParameterCountValidationRule.class);
    }
    
    public void addRule(Class<? extends VirtualTypeValidationRule> ruleClass) {
        
        final VirtualTypeValidationRule rule = dependencyContainer.getInstanceOfClass(ruleClass);
        rules.add(rule);
    }
    
}
