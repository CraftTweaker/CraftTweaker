package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.validator.rules;

import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class AbstractGeneralValidationRule implements ExpansionInfoValidationRule {
    
    protected final Set<Class<? extends Annotation>> annotationClasses = new HashSet<>();
    
    protected AbstractGeneralValidationRule() {
        fillAnnotationClasses();
    }
    
    @Override
    public boolean canValidate(Element enclosedElement) {
        return hasZenAnnotation(enclosedElement);
    }
    
    private boolean hasZenAnnotation(Element enclosedElement) {
        return annotationClasses.stream().anyMatch(annotationPresentOn(enclosedElement));
    }
    
    
    private Predicate<Class<? extends Annotation>> annotationPresentOn(Element enclosedElement) {
        return annotationClass -> enclosedElement.getAnnotation(annotationClass) != null;
    }
    
    private void fillAnnotationClasses() {
        this.annotationClasses.add(ZenCodeType.Method.class);
        this.annotationClasses.add(ZenCodeType.Getter.class);
        this.annotationClasses.add(ZenCodeType.Setter.class);
        this.annotationClasses.add(ZenCodeType.Caster.class);
        this.annotationClasses.add(ZenCodeType.Operator.class);
    }
}
