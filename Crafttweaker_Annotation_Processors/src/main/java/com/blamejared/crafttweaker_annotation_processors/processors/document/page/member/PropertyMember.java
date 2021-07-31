package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.conversion.converter.comment.CommentMerger;
import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.PropertyMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

public class PropertyMember implements IFillMeta<PropertyMemberMeta> {
    
    private final AbstractTypeInfo type;
    private final String name;
    private final boolean hasGetter;
    private final boolean hasSetter;
    private final DocumentationComment comment;
    
    public PropertyMember(String name, AbstractTypeInfo type, boolean hasGetter, boolean hasSetter, DocumentationComment comment) {
        
        this.hasGetter = hasGetter;
        this.hasSetter = hasSetter;
        this.type = type;
        this.name = name;
        this.comment = comment;
    }
    
    public static PropertyMember merge(PropertyMember left, PropertyMember right) {
        
        if(!left.name.equals(right.name)) {
            throw new IllegalArgumentException("Trying to merge different names!");
        }
        
        final boolean hasGetter = left.hasGetter || right.hasGetter;
        final boolean hasSetter = left.hasSetter || right.hasSetter;
        
        CommentMerger commentMerger = new CommentMerger();
        DocumentationComment merged = commentMerger.merge(left.comment, right.comment);
        
        return new PropertyMember(left.name, left.type, hasGetter, hasSetter, merged);
    }
    
    public void writeTableRow(PageOutputWriter writer) {
        
        writer.printf("| %s | %s | %s | %s | %s |%n", name, type.getClickableMarkdown(), hasGetter, hasSetter, comment.getMarkdownDescription());
    }
    
    public String getName() {
        
        return name;
    }
    
    @Override
    public void fillMeta(PropertyMemberMeta meta) {
        
        meta.setHasGetter(hasGetter);
        meta.setHasSetter(hasSetter);
        meta.setType(new TypePageMeta(type));
        meta.setName(name);
        meta.setDescription(comment.getDescription());
    }
    
}
