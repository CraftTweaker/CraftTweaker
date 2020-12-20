package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;

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
    
    public void write(PrintWriter writer) {
        writeComment(writer);
        writeReturnType(writer);
        writeCodeBlockWithExamples(writer);
    }
    
    private void writeComment(PrintWriter writer) {
        getComment().getOptionalDescription().ifPresent(writer::println);
    }
    
    private void writeReturnType(PrintWriter writer) {
        writer.printf("Returns %s%n%n", header.returnType.getClickableMarkdown());
    }
    
    private void writeCodeBlockWithExamples(PrintWriter writer) {
        writer.println("```zenscript");
        writeSignatureExample(writer);
        header.writeVirtualExamples(writer, getComment().getExamples());
        writer.println("```");
    }
    
    private void writeSignatureExample(PrintWriter writer) {
        final String callee = "";
        writer.printf("%s.%s%s%n", callee, name, header.formatForSignatureExample());
    }
    
    public String getName() {
        return name;
    }
}
