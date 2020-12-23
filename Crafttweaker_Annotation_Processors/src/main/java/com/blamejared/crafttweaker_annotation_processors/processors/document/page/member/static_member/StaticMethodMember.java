package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;

public class StaticMethodMember implements Comparable<StaticMethodMember> {
    
    private final String name;
    protected final MemberHeader header;
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
        
        return this.header.parameters.size() - other.header.parameters.size();
    }
    
    public void write(PrintWriter writer, AbstractTypeInfo ownerType) {
        writeComment(writer);
        writeReturnType(writer);
        writeCodeBlockWithExamples(writer, ownerType);
        writeParameterDescriptionTable(writer);
    }
    
    private void writeParameterDescriptionTable(PrintWriter writer) {
        header.writeParameterDescriptionTable(writer);
    }
    
    private void writeReturnType(PrintWriter writer) {
        writer.printf("Return Type: %s%n%n", header.returnType.getClickableMarkdown());
    }
    
    private void writeCodeBlockWithExamples(PrintWriter writer, AbstractTypeInfo ownerType) {
        writer.println("```zenscript");
        writeExampleBlockContent(writer, ownerType);
        writer.println("```");
    }
    
    protected void writeExampleBlockContent(PrintWriter writer, AbstractTypeInfo ownerType) {
        writeSignatureExample(writer, ownerType);
        writeExamples(writer, ownerType);
    }
    
    private void writeSignatureExample(PrintWriter writer, AbstractTypeInfo ownerType) {
        final String ownerName = ownerType.getDisplayName();
        final String signature = header.formatForSignatureExample();
        writer.printf("%s.%s%s%n", ownerName, name, signature);
    }
    
    private void writeExamples(PrintWriter writer, AbstractTypeInfo ownerType) {
        header.writeStaticExamples(writer, ownerType, name);
    }
    
    private void writeComment(PrintWriter writer) {
        writeDescription(writer);
        writeReturnTypeInfo(writer);
    }
    
    private void writeReturnTypeInfo(PrintWriter writer) {
        if(returnTypeInfoPresent()) {
            writer.printf("Returns: %s%n", returnTypeInfo);
        }
    }
    
    private boolean returnTypeInfoPresent() {
        return returnTypeInfo != null;
    }
    
    private void writeDescription(PrintWriter writer) {
        if(methodComment.hasDescription()) {
            writer.println(methodComment.getDescription());
            writer.println();
        }
    }
    
    public String getName() {
        return name;
    }
}
