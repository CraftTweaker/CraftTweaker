package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;

public class TypePageTypeInfo extends AbstractTypeInfo {
    
    private final TypePageInfo pageInfo;
    
    public TypePageTypeInfo(TypePageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    
    @Override
    public String getDisplayName() {
        return pageInfo.zenCodeName.getSimpleName();
    }
}
