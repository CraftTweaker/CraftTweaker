package com.blamejared.crafttweaker_annotation_processors.processors.document.page.type;

import javax.annotation.Nonnull;

public abstract class AbstractTypeInfo implements Comparable<AbstractTypeInfo> {
    
    public abstract String getDisplayName();
    
    public abstract String getClickableMarkdown();
    
    public int compareTo(@Nonnull AbstractTypeInfo other) {
        return this.getDisplayName().compareToIgnoreCase(other.getDisplayName());
    }
}
