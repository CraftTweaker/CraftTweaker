package com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.ElementLinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.ImportedTypeLinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.QualifiedNameLinkConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.converter.comment.linktag.rules.ThisTypeConversionRule;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.DependencyContainer;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.dependencies.IHasPostCreationCall;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

public class LinkConverter implements IHasPostCreationCall {
    private final List<LinkConversionRule> conversionRules = new ArrayList<>();
    private final DependencyContainer dependencyContainer;
    
    public LinkConverter(DependencyContainer dependencyContainer) {
        this.dependencyContainer = dependencyContainer;
    }
    
    public String convertLinkToClickableMarkdown(String link, Element element) {
        final LinkConversionRule rule = findRuleFor(link);
        return rule.convertToClickableMarkdown(link, element);
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
