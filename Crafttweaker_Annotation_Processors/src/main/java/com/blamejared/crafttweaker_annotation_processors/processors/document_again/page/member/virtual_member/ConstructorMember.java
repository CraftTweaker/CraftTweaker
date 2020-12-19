package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;

public class ConstructorMember extends AbstractVirtualMember implements Comparable<ConstructorMember> {
    
    public ConstructorMember(MemberHeader header, Example aThis, @Nullable DocumentationComment description) {
        super(header, description);
    }
    
    @Override
    public int compareTo(@Nonnull ConstructorMember other) {
        return this.header.parameters.size() - other.header.parameters.size();
    }
    
    public void write(PrintWriter writer) {
        writeDescription(writer);
        writeCodeBlock(writer);
    }
    
    private void writeDescription(PrintWriter writer) {
        final DocumentationComment comment = getComment();
        writer.println(comment.getDescription());
    }
    
    private AbstractTypeInfo getConstructedType() {
        return header.returnType;
    }
    
    private void writeCodeBlock(PrintWriter writer) {
        writer.println("```zenscript");
        writeSignature(writer);
        header.writeConstructorExamples(writer, getConstructedType());
        writer.println("```");
    }
    
    private void writeSignature(PrintWriter writer) {
        final String ownerName = getConstructedType().getDisplayName();
        final String signature = header.formatForSignatureExample();
        writer.printf("new %s%s%n", ownerName, signature);
    }
}
