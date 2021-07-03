package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VirtualMethodMember extends AbstractVirtualMember implements Comparable<VirtualMethodMember> {
    
    private final String name;
    private final String returnTypeInfo;
    
    public VirtualMethodMember(MemberHeader header, @Nullable DocumentationComment description, String name, @Nullable final String returnTypeInfo) {
        super(header, description);
        this.name = name;
        this.returnTypeInfo = returnTypeInfo;
    }
    
    @Override
    public int compareTo(@Nonnull VirtualMethodMember other) {
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
    
    private void writeComment(PageOutputWriter writer) {
        this.writeDescription(writer);
        this.writeReturnTypeInfo(writer);
    }
    
    private void writeDescription(final PageOutputWriter writer) {
        this.getComment().getOptionalDescription().ifPresent(it -> {
            writer.println(it);
            writer.println();
        });
    }
    
    private void writeReturnTypeInfo(final PageOutputWriter writer) {
        if(this.returnTypeInfoPresent()) {
            writer.printf("Returns: %s  %n", this.returnTypeInfo);
        }
    }
    
    private boolean returnTypeInfoPresent() {
        return this.returnTypeInfo != null;
    }
    
    private void writeReturnType(PageOutputWriter writer) {
        writer.printf("Return Type: %s%n%n", header.returnType.getClickableMarkdown());
    }
    
    private void writeCodeBlockWithExamples(PageOutputWriter writer, AbstractTypeInfo ownerType) {
        writer.zenBlock(() -> {
            writeSignatureExample(writer, ownerType, header.getNumberOfUsableExamples() > 0);
            header.writeVirtualExamples(writer, getComment().getExamples(), name);
        });
        writer.println();
    }
    
    private void writeDeprecation(final PageOutputWriter writer) {
        writer.deprecationMessage(this.getComment().getDeprecationMessage());
    }
    
    private void writeSignatureExample(PageOutputWriter writer, AbstractTypeInfo ownerType, boolean onlySignature) {
        final String callee = ownerType.getDisplayName();
        String template = "%s%s.%s%s%n";
        if(onlySignature) {
            template = template + "%n";
        }
        writer.printf(template, onlySignature ? "// " : "", callee, name, header.formatForSignatureExample());
    }
    
    private void writeParameterDescriptionTable(PageOutputWriter writer) {
        header.writeParameterDescriptionTable(writer);
    }
    
    public String getName() {
        return name;
    }
    
    public String getSince() {
        return this.getComment().getSinceVersion();
    }
}
