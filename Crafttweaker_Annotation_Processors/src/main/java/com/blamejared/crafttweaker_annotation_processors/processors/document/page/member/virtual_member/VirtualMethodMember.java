package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.util.Optional;

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
        
        return this.header.compareTo(other.header);
    }
    
    public void write(PrintWriter writer, AbstractTypeInfo ownerType) {
        writeComment(writer);
        writeReturnType(writer);
        writeCodeBlockWithExamples(writer, ownerType);
        writeParameterDescriptionTable(writer);
    }
    
    private void writeComment(PrintWriter writer) {
        final Optional<String> optionalDescription = getComment().getOptionalDescription();
        
        if(optionalDescription.isPresent()) {
            writer.println(optionalDescription.get());
            writer.println();
        }
    }
    
    private void writeReturnType(PrintWriter writer) {
        writer.printf("Return Type: %s%n%n", header.returnType.getClickableMarkdown());
    }
    
    private void writeCodeBlockWithExamples(PrintWriter writer, AbstractTypeInfo ownerType) {
        writer.println("```zenscript");
        writeSignatureExample(writer, ownerType);
        header.writeVirtualExamples(writer, getComment().getExamples(), name);
        writer.println("```");
    }
    
    private void writeSignatureExample(PrintWriter writer, AbstractTypeInfo ownerType) {
        final String callee = ownerType.getDisplayName();
        writer.printf("%s.%s%s%n", callee, name, header.formatForSignatureExample());
    }
    
    private void writeParameterDescriptionTable(PrintWriter writer) {
        header.writeParameterDescriptionTable(writer);
    }
    
    public String getName() {
        return name;
    }
}
