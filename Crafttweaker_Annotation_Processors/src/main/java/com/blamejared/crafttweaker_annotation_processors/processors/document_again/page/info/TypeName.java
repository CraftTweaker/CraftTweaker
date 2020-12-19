package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.info;

import javax.annotation.Nonnull;

public class TypeName implements Comparable<TypeName> {
    
    private final String zenCodeName;
    
    public TypeName(String zenCodeName) {
        this.zenCodeName = zenCodeName;
    }
    
    public String getZenCodeName() {
        return zenCodeName;
    }
    
    public String getSimpleName() {
        final int lastDotIndex = zenCodeName.lastIndexOf('.');
        return zenCodeName.substring(lastDotIndex + 1);
    }
    
    public int compareTo(@Nonnull TypeName other) {
        return this.zenCodeName.compareTo(other.zenCodeName);
    }
    
    //<editor-fold desc="Object implementation">
    @Override
    public String toString() {
        return getZenCodeName();
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        TypeName typeName = (TypeName) o;
        
        return zenCodeName.equals(typeName.zenCodeName);
    }
    
    @Override
    public int hashCode() {
        return zenCodeName.hashCode();
    }
    //</editor-fold>
}
