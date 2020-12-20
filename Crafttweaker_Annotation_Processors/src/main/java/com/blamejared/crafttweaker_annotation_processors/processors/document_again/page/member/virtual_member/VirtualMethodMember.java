package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;

public class VirtualMethodMember extends AbstractVirtualMember implements Comparable<VirtualMethodMember> {
    
    private final String name;
    
    public VirtualMethodMember(MemberHeader header, @Nullable DocumentationComment description, String name) {
        super(header, description);
        this.name = name;
    }
    
    @Override
    public int compareTo(@Nonnull VirtualMethodMember other) {
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
    
    private void writeComment(PrintWriter writer) {
        getComment().getOptionalDescription().ifPresent(writer::println);
    }
    
    private void writeReturnType(PrintWriter writer) {
        writer.printf("Returns %s%n%n", header.returnType.getClickableMarkdown());
    }
    
    private void writeCodeBlockWithExamples(PrintWriter writer, AbstractTypeInfo ownerType) {
        writer.println("```zenscript");
        writeSignatureExample(writer, ownerType);
        header.writeVirtualExamples(writer, getComment().getExamples());
        writer.println("```");
    }
    
    private void writeSignatureExample(PrintWriter writer, AbstractTypeInfo ownerType) {
        final String callee = ownerType.getDisplayName();
        writer.printf("%s.%s%s%n", callee, name, header.formatForSignatureExample());
    }
    
    public String getName() {
        return name;
    }
}
