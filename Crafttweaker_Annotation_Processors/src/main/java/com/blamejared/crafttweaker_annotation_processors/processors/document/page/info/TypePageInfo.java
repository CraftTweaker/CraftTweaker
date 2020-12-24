package com.blamejared.crafttweaker_annotation_processors.processors.document.page.info;

import javax.annotation.*;

public class TypePageInfo extends DocumentationPageInfo implements Comparable<TypePageInfo> {
    
    public final TypeName zenCodeName;
    
    public TypePageInfo(String declaringModId, String outputPath, TypeName zenCodeName) {
        super(declaringModId, outputPath);
        this.zenCodeName = zenCodeName;
    }
    
    @Override
    public int compareTo(@Nonnull TypePageInfo o) {
        return this.zenCodeName.compareTo(o.zenCodeName);
    }
    
    @Override
    public String getSimpleName() {
        return zenCodeName.getSimpleName();
    }
}
