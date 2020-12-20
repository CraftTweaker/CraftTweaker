package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;

public class StaticMethodMember implements Comparable<StaticMethodMember> {
    
    private final String name;
    private final MemberHeader header;
    @Nullable
    private final DocumentationComment methodComment;
    
    public StaticMethodMember(String name, MemberHeader header, @Nullable DocumentationComment methodComment) {
        this.name = name;
        this.header = header;
        this.methodComment = methodComment;
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
    }
    
    private void writeReturnType(PrintWriter writer) {
        writer.printf("Returns %s%n%n", header.returnType.getClickableMarkdown());
    }
    
    private void writeCodeBlockWithExamples(PrintWriter writer, AbstractTypeInfo ownerType) {
        writer.println("```zenscript");
        writeSignatureExample(writer, ownerType);
        writeExamples(writer, ownerType);
        writer.println("```");
    }
    
    private void writeSignatureExample(PrintWriter writer, AbstractTypeInfo ownerType) {
        final String ownerName = ownerType.getDisplayName();
        final String signature = header.formatForSignatureExample();
        writer.printf("%s.%s%s%n", ownerName, name, signature);
    }
    
    private void writeExamples(PrintWriter writer, AbstractTypeInfo ownerType) {
        header.writeStaticExamples(writer, ownerType);
    }
    
    private void writeComment(PrintWriter writer) {
        if(methodComment != null) {
            writer.println(methodComment);
            writer.println();
        }
    }
}
