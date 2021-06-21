package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;

public class ConstructorMember extends AbstractVirtualMember implements Comparable<ConstructorMember> {
    
    public ConstructorMember(MemberHeader header, @Nullable DocumentationComment description) {
        super(header, description);
    }
    
    @Override
    public int compareTo(@Nonnull ConstructorMember other) {
        return this.header.parameters.size() - other.header.parameters.size();
    }
    
    public void write(PrintWriter writer) {
        writeDeprecation(writer);
        writeDescription(writer);
        writeCodeBlock(writer);
        writeParameterDescriptionTable(writer);
    }
    
    private void writeParameterDescriptionTable(PrintWriter writer) {
        header.writeParameterDescriptionTable(writer);
    }
    
    private void writeDescription(PrintWriter writer) {
        final DocumentationComment comment = getComment();
        writer.println(comment.getMarkdownDescription());
    }
    
    private AbstractTypeInfo getConstructedType() {
        return header.returnType;
    }
    
    private void writeCodeBlock(PrintWriter writer) {
        writer.println("```zenscript");
        writeSignature(writer);
        header.writeConstructorExamples(writer, getConstructedType());
        writer.println("```");
        writer.println();
    }
    
    private void writeSignature(PrintWriter writer) {
        final String ownerName = getConstructedType().getDisplayName();
        final String signature = header.formatForSignatureExample();
        writer.printf("new %s%s%n", ownerName, signature);
    }
    
    private void writeDeprecation(final PrintWriter writer) {
        if (!this.getComment().isDeprecated()) return;
        writer.printf(":::warnBox%n%n");
        writer.write(this.getComment().getDeprecationMessage());
        writer.printf("%n:::%n%n");
    }
}
