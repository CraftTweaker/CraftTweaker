package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import java.io.PrintWriter;

public class PropertyMember {
    
    private final AbstractTypeInfo type;
    private final String name;
    private final boolean hasGetter;
    private final boolean hasSetter;
    
    public PropertyMember(String name, AbstractTypeInfo type, boolean hasGetter, boolean hasSetter) {
        this.hasGetter = hasGetter;
        this.hasSetter = hasSetter;
        this.type = type;
        this.name = name;
    }
    
    public static PropertyMember merge(PropertyMember left, PropertyMember right) {
        if(!left.name.equals(right.name)) {
            throw new IllegalArgumentException("Trying to merge different names!");
        }
        
        final boolean hasGetter = left.hasGetter || right.hasGetter;
        final boolean hasSetter = left.hasSetter || right.hasSetter;
        
        return new PropertyMember(left.name, left.type, hasGetter, hasSetter);
    }
    
    public void writeTableRow(PrintWriter writer) {
        writer.printf("| %s | %s | %s | %s |%n", name, type.getClickableMarkdown(), hasGetter, hasSetter);
    }
    
    public String getName() {
        return name;
    }
}
