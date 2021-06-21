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
        writeDeprecation(writer);
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
        writeSignatureExample(writer, ownerType, header.getNumberOfUsableExamples() > 0);
        header.writeVirtualExamples(writer, getComment().getExamples(), name);
        writer.println("```");
        writer.println();
    }
    
    private void writeDeprecation(final PrintWriter writer) {
        if (!this.getComment().isDeprecated()) return;
        writer.printf(":::warnBox%n%n");
        writer.write(this.getComment().getDeprecationMessage());
        writer.printf("%n:::%n%n");
    }
    
    private void writeSignatureExample(PrintWriter writer, AbstractTypeInfo ownerType, boolean onlySignature) {
        final String callee = ownerType.getDisplayName();
        String template = "%s%s.%s%s%n";
        if(onlySignature) {
            template = template + "%n";
        }
        writer.printf(template, onlySignature ? "// " : "", callee, name, header.formatForSignatureExample());
    }
    
    private void writeParameterDescriptionTable(PrintWriter writer) {
        header.writeParameterDescriptionTable(writer);
    }
    
    public String getName() {
        return name;
    }
    
    public Optional<String> getSince() {
        return this.getComment().getOptionalSince();
    }
}
