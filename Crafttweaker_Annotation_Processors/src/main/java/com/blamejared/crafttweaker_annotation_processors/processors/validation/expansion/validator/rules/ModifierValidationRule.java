package com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.validator.rules;

import com.blamejared.crafttweaker_annotation_processors.processors.validation.expansion.info.ExpansionInfo;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

public class ModifierValidationRule extends AbstractGeneralValidationRule {
    
    private final Set<Modifier> requiredModifiers = new HashSet<>();
    private final Messager messager;
    
    public ModifierValidationRule(Messager messager) {
        this.messager = messager;
        fillRequiredModifiers();
    }
    
    @Override
    public void validate(Element enclosedElement, ExpansionInfo expansionInfo) {
        if(hasIncorrectModifiers(enclosedElement)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Expansion members need to be public and static", enclosedElement);
        }
    }
    
    private boolean hasIncorrectModifiers(Element enclosedElement) {
        return !enclosedElement.getModifiers().containsAll(requiredModifiers);
    }
    
    private void fillRequiredModifiers() {
        this.requiredModifiers.add(Modifier.PUBLIC);
        this.requiredModifiers.add(Modifier.STATIC);
    }
}
