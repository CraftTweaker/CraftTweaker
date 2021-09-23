package com.blamejared.crafttweaker_annotation_processors.processors.validation.virtual_type.validator.rules;

import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ParameterCountValidationRule implements VirtualTypeValidationRule {
    
    private final Messager messager;
    private final Map<Class<? extends Annotation>, Integer> methodAnnotationToParameterCount = new HashMap<>();
    
    public ParameterCountValidationRule(Messager messager) {
        
        this.messager = messager;
        fillParameterMap();
    }
    
    private void fillParameterMap() {
        
        methodAnnotationToParameterCount.put(ZenCodeType.Getter.class, 0);
        methodAnnotationToParameterCount.put(ZenCodeType.Setter.class, 1);
        methodAnnotationToParameterCount.put(ZenCodeType.Caster.class, 0);
    }
    
    @Override
    public boolean canValidate(Element enclosedElement) {
        
        return expectedParameterCountsFor(enclosedElement).count() > 0L;
    }
    
    @Override
    public void validate(Element enclosedElement) {
        
        final int parameterCount = getParameterCount(enclosedElement);
        this.expectedParameterCountsFor(enclosedElement)
                .filter(countNotEqualTo(parameterCount))
                .forEach(logErrorMessage(enclosedElement));
    }
    
    private IntStream expectedParameterCountsFor(Element enclosedElement) {
        
        return methodAnnotationToParameterCount.keySet()
                .stream()
                .filter(annotationPresentOn(enclosedElement))
                .mapToInt(methodAnnotationToParameterCount::get);
        
    }
    
    private Predicate<Class<? extends Annotation>> annotationPresentOn(Element enclosedElement) {
        
        return annotation -> enclosedElement.getAnnotation(annotation) != null;
    }
    
    private IntPredicate countNotEqualTo(int parameterCount) {
        
        return count -> count != parameterCount;
    }
    
    private IntConsumer logErrorMessage(Element enclosedElement) {
        
        return expectedCount -> {
            final String format = "Expected %s parameters for this Expansion Method";
            messager.printMessage(Diagnostic.Kind.ERROR, String.format(format, expectedCount), enclosedElement);
        };
    }
    
    private int getParameterCount(Element enclosedElement) {
        
        if(enclosedElement.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException("Invalid type annotated? " + enclosedElement);
        }
        
        return ((ExecutableElement) enclosedElement).getParameters().size();
    }
    
}
