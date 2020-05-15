package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types;

import java.util.List;
import java.util.stream.Collectors;

public class DocumentedGenericType extends DocumentedType {

    private final DocumentedType baseType;
    private final List<DocumentedType> genericTypes;

    public DocumentedGenericType(DocumentedType baseType, List<DocumentedType> genericTypes) {
        this.baseType = baseType;
        this.genericTypes = genericTypes;
    }


    @Override
    public String getZSName() {
        return genericTypes.stream()
                .map(DocumentedType::getZSName)
                .collect(Collectors.joining(", ", baseType.getZSName() + "<", ">"));
    }

    @Override
    public String getClickableMarkdown() {
        return genericTypes.stream()
                .map(DocumentedType::getClickableMarkdown)
                .collect(Collectors.joining(", ", baseType.getClickableMarkdown() + "&lt;", "&gt;"));
    }

    @Override
    public String getZSShortName() {
        return baseType.getZSShortName();
    }
    
    @Override
    public String getDocParamThis() {
        return "my" + baseType.getZSShortName();
    }
}
