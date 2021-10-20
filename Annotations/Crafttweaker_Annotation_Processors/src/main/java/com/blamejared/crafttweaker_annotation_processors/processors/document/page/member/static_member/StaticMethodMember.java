package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.DocumentMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StaticMethodMember implements Comparable<StaticMethodMember>, IFillMeta {
    
    protected final MemberHeader header;
    private final String name;
    private final DocumentationComment methodComment;
    @Nullable
    private final String returnTypeInfo;
    
    public StaticMethodMember(String name, MemberHeader header, DocumentationComment methodComment, @Nullable String returnTypeInfo) {
        
        this.name = name;
        this.header = header;
        this.methodComment = methodComment;
        this.returnTypeInfo = returnTypeInfo;
    }
    
    @Override
    public int compareTo(@Nonnull StaticMethodMember other) {
        
        final int compareName = this.name.compareToIgnoreCase(other.name);
        if(compareName != 0) {
            return compareName;
        }
        
        return this.header.compareTo(other.header);
    }
    
    public void write(PageOutputWriter writer, AbstractTypeInfo ownerType) {
        
        writeDeprecation(writer);
        writeComment(writer);
        writeReturnType(writer);
        writeCodeBlockWithExamples(writer, ownerType);
        writeParameterDescriptionTable(writer);
    }
    
    private void writeParameterDescriptionTable(PageOutputWriter writer) {
        
        header.writeParameterDescriptionTable(writer);
    }
    
    private void writeReturnType(PageOutputWriter writer) {
        
        writer.printf("Return Type: %s%n%n", header.returnType.getClickableMarkdown());
    }
    
    private void writeCodeBlockWithExamples(PageOutputWriter writer, AbstractTypeInfo ownerType) {
        
        writer.zenBlock(() -> writeExampleBlockContent(writer, ownerType));
        writer.println();
    }
    
    protected void writeExampleBlockContent(PageOutputWriter writer, AbstractTypeInfo ownerType) {
        
        writeSignatureExample(writer, ownerType, header.getNumberOfUsableExamples() > 0);
        writeExamples(writer, ownerType);
    }
    
    private void writeSignatureExample(PageOutputWriter writer, AbstractTypeInfo ownerType, boolean onlySignature) {
        
        final String ownerName = ownerType.getDisplayName();
        final String signature = header.formatForSignatureExample();
        String template = "%s%s.%s%s%n";
        if(onlySignature) {
            template = template + "%n";
        }
        writer.printf(template, onlySignature ? "// " : "", ownerName, name, signature);
    }
    
    private void writeExamples(PageOutputWriter writer, AbstractTypeInfo ownerType) {
        
        header.writeStaticExamples(writer, ownerType, name);
    }
    
    private void writeDeprecation(final PageOutputWriter writer) {
        
        writer.deprecationMessage(this.methodComment.getDeprecationMessage());
    }
    
    private void writeComment(PageOutputWriter writer) {
        
        writeDescription(writer);
        writeReturnTypeInfo(writer);
    }
    
    private void writeReturnTypeInfo(PageOutputWriter writer) {
        
        if(returnTypeInfoPresent()) {
            writer.printf("Returns: %s  %n", returnTypeInfo);
        }
    }
    
    private boolean returnTypeInfoPresent() {
        
        return returnTypeInfo != null;
    }
    
    private void writeDescription(PageOutputWriter writer) {
        
        if(methodComment.hasDescription()) {
            writer.println(methodComment.getMarkdownDescription());
            writer.println();
        }
    }
    
    public String getName() {
        
        return name;
    }
    
    public String getSince() {
        
        return this.methodComment.getSinceVersion();
    }
    
    @Override
    public void fillMeta(DocumentMeta meta) {
        
        meta.addSearchTerms(name);
    }
    
}
