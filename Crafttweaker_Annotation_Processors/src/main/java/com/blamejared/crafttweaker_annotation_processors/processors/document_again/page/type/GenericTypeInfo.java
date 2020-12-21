package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type;

import java.util.List;
import java.util.stream.Collectors;

public class GenericTypeInfo extends AbstractTypeInfo {
    
    private final AbstractTypeInfo baseClass;
    private final List<AbstractTypeInfo> typeArguments;
    
    public GenericTypeInfo(AbstractTypeInfo baseClass, List<AbstractTypeInfo> typeArguments) {
        this.baseClass = baseClass;
        this.typeArguments = typeArguments;
    }
    
    @Override
    public String getDisplayName() {
        final String arguments = typeArguments.stream()
                .map(AbstractTypeInfo::getDisplayName)
                .collect(Collectors.joining(",", "<", ">"));
        return baseClass.getDisplayName() + arguments;
    }
    
    @Override
    public String getClickableMarkdown() {
        final String arguments = typeArguments.stream()
                .map(AbstractTypeInfo::getClickableMarkdown)
                .collect(Collectors.joining(",", "&lt;", "&gt;"));
        return baseClass.getClickableMarkdown() + arguments;
    }
}
