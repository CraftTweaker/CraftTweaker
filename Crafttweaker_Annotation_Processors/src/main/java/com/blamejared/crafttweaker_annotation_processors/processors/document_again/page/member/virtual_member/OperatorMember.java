package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.virtual_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.FormattingUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.DocumentedParameter;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.Example;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class OperatorMember extends AbstractVirtualMember implements Comparable<OperatorMember> {
    
    private final ZenCodeType.OperatorType type;
    
    public OperatorMember(MemberHeader header, @Nullable DocumentationComment description, ZenCodeType.OperatorType type) {
        super(header, description);
        this.type = type;
    }
    
    @Override
    public int compareTo(@Nonnull OperatorMember other) {
        return this.type.name().compareToIgnoreCase(other.type.name());
    }
    
    public void write(PrintWriter writer) {
        final String operatorFormat = FormattingUtils.getOperatorFormat(type);
        final Object[] parameters = getParameters();
        
        writer.printf(operatorFormat, parameters);
        writer.println();
    }
    
    @Nonnull
    private Object[] getParameters() {
        final List<Object> parametersFromHeader = getParametersFromHeader();
        final Object callee = getCallee();
        
        parametersFromHeader.add(0, callee);
        
        return parametersFromHeader.toArray();
    }
    
    @Nonnull
    private List<Object> getParametersFromHeader() {
        return header.parameters.stream()
                .map(DocumentedParameter::formatForSignatureExample)
                .collect(Collectors.toList());
    }
    
    private String getCallee() {
        return "TODO_THIS";
    }
}
