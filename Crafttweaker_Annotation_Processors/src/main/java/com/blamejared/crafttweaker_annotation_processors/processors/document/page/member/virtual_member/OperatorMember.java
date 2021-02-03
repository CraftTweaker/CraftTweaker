package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OperatorMember extends AbstractVirtualMember implements Comparable<OperatorMember> {
    
    private final ZenCodeType.OperatorType type;
    private final AbstractTypeInfo ownerType;
    
    public OperatorMember(MemberHeader header, DocumentationComment comment, ZenCodeType.OperatorType type, AbstractTypeInfo ownerType) {
        super(header, comment);
        this.type = type;
        this.ownerType = ownerType;
    }
    
    @Override
    public int compareTo(@Nonnull OperatorMember other) {
        return this.type.name().compareToIgnoreCase(other.type.name());
    }
    
    public void write(PrintWriter writer) {
        writeTitle(writer);
        writeDescription(writer);
        writeScriptBlock(writer);
        writer.println();
        writer.println();
    }
    
    private void writeTitle(PrintWriter writer) {
        writer.printf("### %s%n%n", type.name());
    }
    
    private void writeDescription(PrintWriter writer) {
        if(getComment().hasDescription()) {
            writer.println(getComment().getDescription());
            writer.println();
        }
    }
    
    private void writeScriptBlock(PrintWriter writer) {
        writer.println("```zenscript");
        writeScriptBlockInner(writer);
        writer.println("```");
        writer.println();
    }
    
    private void writeScriptBlockInner(PrintWriter writer) {
        writeHeader(writer);
        writeExamples(writer);
    }
    
    private void writeExamples(PrintWriter writer) {
        final int numberOfUsableExamples = header.getNumberOfUsableExamples();
        if(numberOfUsableExamples < 1) {
            return;
        }
        
        for(int exampleIndex = 0; exampleIndex < numberOfUsableExamples; exampleIndex++) {
            writeExample(writer, exampleIndex);
        }
    }
    
    private void writeExample(PrintWriter writer, int exampleIndex) {
        final List<String> exampleArguments = getExampleArguments(exampleIndex);
        
        exampleArguments.add(0, getExampleCallee());
        
        final String operatorFormat = OperatorFormatProvider.getOperatorFormat(type);
        final Object[] arguments = exampleArguments.toArray();
        writer.printf(operatorFormat, arguments);
        writer.println();
    }
    
    @Nonnull
    private ArrayList<String> getExampleArguments(int exampleIndex) {
        final String exampleArgument = header.getExampleArgument(exampleIndex);
        final String[] split = exampleArgument.split(", ");
        return new ArrayList<>(Arrays.asList(split));
    }
    
    private void writeHeader(PrintWriter writer) {
        final String operatorFormat = OperatorFormatProvider.getOperatorFormat(type);
        final Object[] parameters = getParameters();
        
        writer.printf(operatorFormat, parameters);
        writer.println();
    }
    
    @Nonnull
    private Object[] getParameters() {
        final List<Object> parametersFromHeader = getParametersFromHeader();
        final Object callee = getHeaderCallee();
        
        parametersFromHeader.add(0, callee);
        
        return parametersFromHeader.toArray();
    }
    
    @Nonnull
    private List<Object> getParametersFromHeader() {
        return header.parameters.stream()
                .map(DocumentedParameter::formatForSignatureExample)
                .collect(Collectors.toList());
    }
    
    private String getExampleCallee() {
        return getComment().getExamples().getExampleFor("this").getAnyTextValue();
    }
    
    private String getHeaderCallee() {
        return "my" + ownerType.getDisplayName();
    }
}
