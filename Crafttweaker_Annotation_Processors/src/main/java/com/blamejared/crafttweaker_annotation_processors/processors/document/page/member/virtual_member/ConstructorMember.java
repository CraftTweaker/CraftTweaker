package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConstructorMember extends AbstractVirtualMember implements Comparable<ConstructorMember> {
    
    public ConstructorMember(MemberHeader header, @Nullable DocumentationComment description) {
        super(header, description);
    }
    
    @Override
    public int compareTo(@Nonnull ConstructorMember other) {
        return this.header.parameters.size() - other.header.parameters.size();
    }
    
    public void write(PageOutputWriter writer) {
        writeDeprecation(writer);
        writeDescription(writer);
        writeCodeBlock(writer);
        writeParameterDescriptionTable(writer);
    }
    
    private void writeParameterDescriptionTable(PageOutputWriter writer) {
        header.writeParameterDescriptionTable(writer);
    }
    
    private void writeDescription(PageOutputWriter writer) {
        final DocumentationComment comment = getComment();
        writer.println(comment.getMarkdownDescription());
    }
    
    private AbstractTypeInfo getConstructedType() {
        return header.returnType;
    }
    
    private void writeCodeBlock(PageOutputWriter writer) {
        writer.zenBlock(() -> {
            writeSignature(writer);
            header.writeConstructorExamples(writer, getConstructedType());
        });
    }
    
    private void writeSignature(PageOutputWriter writer) {
        final String ownerName = getConstructedType().getDisplayName();
        final String signature = header.formatForSignatureExample();
        writer.printf("new %s%s%n", ownerName, signature);
    }
    
    private void writeDeprecation(final PageOutputWriter writer) {
        writer.deprecationMessage(this.getComment().getDeprecationMessage());
    }
}
