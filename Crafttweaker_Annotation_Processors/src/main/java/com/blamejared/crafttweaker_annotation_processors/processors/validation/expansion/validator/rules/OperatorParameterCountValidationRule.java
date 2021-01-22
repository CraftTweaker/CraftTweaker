package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.validator.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.util.OperatorTypeParameterCountProvider;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;

public class OperatorParameterCountValidationRule implements ExpansionInfoValidationRule {
    
    private final Messager messager;
    
    public OperatorParameterCountValidationRule(Messager messager) {
        this.messager = messager;
    }
    
    @Override
    public boolean canValidate(Element enclosedElement) {
        return enclosedElement.getAnnotation(ZenCodeType.Operator.class) != null;
    }
    
    @Override
    public void validate(Element enclosedElement, ExpansionInfo expansionInfo) {
        if(parameterCountInvalid(enclosedElement)) {
            final int expectedNumberOfParameters = getExpectedNumberOfParameters(enclosedElement);
            final String msg = String.format("This operator requires %s parameters", expectedNumberOfParameters);
            messager.printMessage(Diagnostic.Kind.ERROR, msg, enclosedElement);
        }
    }
    
    private boolean parameterCountInvalid(Element enclosedElement) {
        final int expectedNumberOfParameters = getExpectedNumberOfParameters(enclosedElement);
        final int actualNumberOfParameters = getActualNumberOfParameters(enclosedElement);
        return actualNumberOfParameters != expectedNumberOfParameters;
    }
    
    private int getExpectedNumberOfParameters(Element enclosedElement) {
        final ZenCodeType.Operator annotation = enclosedElement.getAnnotation(ZenCodeType.Operator.class);
        final ZenCodeType.OperatorType operatorType = annotation.value();
        return 1 + OperatorTypeParameterCountProvider.getParameterCountFor(operatorType);
    }
    
    private int getActualNumberOfParameters(Element enclosedElement) {
        if(enclosedElement.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException("Invalid type annotated");
        }
        return ((ExecutableElement) enclosedElement).getParameters().size();
    }
}
