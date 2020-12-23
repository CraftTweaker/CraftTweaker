package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.ElementLinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.ImportedTypeLinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.QualifiedNameLinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.ThisTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.IHasPostCreationCall;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.tools.*;
import java.util.ArrayList;
import java.util.List;

public class LinkConverter implements IHasPostCreationCall {
    private final List<LinkConversionRule> conversionRules = new ArrayList<>();
    private final DependencyContainer dependencyContainer;
    private final Messager messager;
    
    public LinkConverter(DependencyContainer dependencyContainer, Messager messager) {
        this.dependencyContainer = dependencyContainer;
        this.messager = messager;
    }
    
    public String convertLinkToClickableMarkdown(String link, Element element) {
        try {
            final LinkConversionRule rule = findRuleFor(link);
            return rule.convertToClickableMarkdown(link, element);
        }catch(Exception e) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Could not convert link '" + link + "': " + e.getMessage());
            return link;
        }
    }
    
    private LinkConversionRule findRuleFor(String link) {
        return conversionRules.stream()
                .filter(rule -> rule.canConvert(link))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot convert link: " + link));
    }
    
    @Override
    public void afterCreation() {
        addConversionRule(ElementLinkConversionRule.class);
        addConversionRule(ThisTypeConversionRule.class);
        addConversionRule(ImportedTypeLinkConversionRule.class);
        addConversionRule(QualifiedNameLinkConversionRule.class);
    }
    
    private void addConversionRule(Class<? extends LinkConversionRule> ruleClass) {
        final LinkConversionRule rule = dependencyContainer.getInstanceOfClass(ruleClass);
        conversionRules.add(rule);
    }
}
