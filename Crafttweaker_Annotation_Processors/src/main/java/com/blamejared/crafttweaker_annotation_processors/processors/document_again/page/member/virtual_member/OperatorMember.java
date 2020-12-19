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

public class OperatorMember extends AbstractVirtualMember implements Comparable<OperatorMember> {
    
    private final ZenCodeType.OperatorType type;
    
    public OperatorMember(MemberHeader header, Example aThis, @Nullable DocumentationComment description, ZenCodeType.OperatorType type) {
        super(header, description);
        this.type = type;
    }
    
    @Override
    public int compareTo(@Nonnull OperatorMember other) {
        return this.type.name().compareToIgnoreCase(other.type.name());
    }
    
    public void write(PrintWriter writer) {
        final String operatorFormat = FormattingUtils.getOperatorFormat(type);
        final Object[] parameters = header.parameters.stream()
                .map(DocumentedParameter::formatForSignatureExample)
                .toArray(Object[]::new);
        
        writer.printf(operatorFormat, parameters);
        writer.println();
    }
}
