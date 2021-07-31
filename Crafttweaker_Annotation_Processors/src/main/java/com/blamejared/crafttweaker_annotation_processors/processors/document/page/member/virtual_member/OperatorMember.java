package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.file.PageOutputWriter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.IFillMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.base.TypePageMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.CasterMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.meta.members.OperatorMemberMeta;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.DocumentedParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.AbstractTypeInfo;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OperatorMember extends AbstractVirtualMember implements Comparable<OperatorMember>, IFillMeta<OperatorMemberMeta> {
    
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
    
    public void write(PageOutputWriter writer) {
        writer.group(this.type.name(), this.getComment().getSinceVersion(), () -> {
            writeDeprecation(writer);
            writeDescription(writer);
            writeScriptBlock(writer);
        });
    }
    
    private void writeDescription(PageOutputWriter writer) {
        if(getComment().hasDescription()) {
            writer.println(getComment().getMarkdownDescription());
            writer.println();
        }
    }
    
    private void writeDeprecation(final PageOutputWriter writer) {
        writer.deprecationMessage(this.getComment().getDeprecationMessage());
    }
    
    
    private void writeScriptBlock(PageOutputWriter writer) {
        writer.zenBlock(() -> this.writeScriptBlockInner(writer));
        writer.println();
    }
    
    private void writeScriptBlockInner(PageOutputWriter writer) {
        writeHeader(writer);
        writeExamples(writer);
    }
    
    private void writeExamples(PageOutputWriter writer) {
        final int numberOfUsableExamples = header.getNumberOfUsableExamples();
        if(numberOfUsableExamples < 1) {
            return;
        }
        
        for(int exampleIndex = 0; exampleIndex < numberOfUsableExamples; exampleIndex++) {
            writeExample(writer, exampleIndex);
        }
    }
    
    private void writeExample(PageOutputWriter writer, int exampleIndex) {
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
    
    private void writeHeader(PageOutputWriter writer) {
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
    
    @Override
    public void fillMeta(OperatorMemberMeta meta) {
        meta.setOperatorType(type.name());
        meta.setReturnType(new TypePageMeta(ownerType));
        //TODO param type
    }
    
}
