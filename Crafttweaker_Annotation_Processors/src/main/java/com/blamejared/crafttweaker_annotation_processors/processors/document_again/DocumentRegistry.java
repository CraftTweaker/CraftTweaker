package com.blamejared.crafttweaker_annotation_processors.processors.document_again;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.DocumentationPageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypeName;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info.TypePageInfo;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.page.DocumentationPage;

import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DocumentRegistry {
    
    private final Map<DocumentationPageInfo, DocumentationPage> documentationPages = new HashMap<>();
    private final Map<TypeElement, DocumentationPageInfo> elementsToInfo = new HashMap<>();
    
    public void addDocumentationPage(DocumentationPage page) {
        this.documentationPages.put(page.pageInfo, page);
    }
    
    public void addInfo(TypeElement typeElement, DocumentationPageInfo pageInfo) {
        elementsToInfo.put(typeElement, pageInfo);
    }
    
    public DocumentationPageInfo getPageInfoFor(TypeElement typeElement) {
        if(elementsToInfo.containsKey(typeElement)) {
            return elementsToInfo.get(typeElement);
        }
        
        throw new IllegalArgumentException("Invalid typeElement: " + typeElement);
    }
    
    public Optional<TypePageInfo> getPageInfoByName(TypeName name) {
        return elementsToInfo.values()
                .stream()
                .filter(pageInfo -> pageInfo instanceof TypePageInfo)
                .map(pageInfo -> (TypePageInfo) pageInfo)
                .filter(pageInfo -> pageInfo.zenCodeName.equals(name))
                .findFirst();
    }
    
    public DocumentationPage getPage(DocumentationPageInfo info) {
        if(documentationPages.containsKey(info)) {
            return documentationPages.get(info);
        }
        
        throw new IllegalArgumentException("No such pageInfo registered yet: " + info);
    }
    
    public Collection<DocumentationPage> getAllPages() {
        return documentationPages.values();
    }
}
