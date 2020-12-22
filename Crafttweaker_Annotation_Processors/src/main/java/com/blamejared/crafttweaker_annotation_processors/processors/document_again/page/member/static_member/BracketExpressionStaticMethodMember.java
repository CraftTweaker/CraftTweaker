package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.static_member;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.comment.DocumentationComment;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.MemberHeader;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.PrintWriter;

public class BracketExpressionStaticMethodMember extends StaticMethodMember {
    
    private final String bepName;
    
    public BracketExpressionStaticMethodMember(String name, MemberHeader header, @Nullable DocumentationComment methodComment, String returntypeInfo, String bepName) {
        super(name, header, methodComment, returntypeInfo);
        this.bepName = bepName;
    }
    
    public String getBepName() {
        return bepName;
    }
    
    @Override
    protected void writeExampleBlockContent(PrintWriter writer, AbstractTypeInfo ownerType) {
        writeBepExamples(writer);
        super.writeExampleBlockContent(writer, ownerType);
    }
    
    private void writeBepExamples(PrintWriter writer) {
        final int numberOfUsableExamples = header.getNumberOfUsableExamples();
        if(numberOfUsableExamples < 1) {
            return;
        }
        
        for(int exampleIndex = 0; exampleIndex < numberOfUsableExamples; exampleIndex++) {
            writeBepExample(writer, exampleIndex);
        }
        writer.println();
    }
    
    private void writeBepExample(PrintWriter writer, int exampleIndex) {
        final String example = getExampleArgument(exampleIndex);
        writer.printf("<%s:%s>%n", bepName, example);
    }
    
    @Nonnull
    private String getExampleArgument(int exampleIndex) {
        final String exampleArgument = header.getExampleArgument(exampleIndex);
        return removeQuotesFrom(exampleArgument);
    }
    
    private String removeQuotesFrom(String exampleArgument) {
        exampleArgument = removeQuoteAtFront(exampleArgument);
        exampleArgument = removeQuoteAtBack(exampleArgument);
        return exampleArgument;
    }
    
    @Nonnull
    private String removeQuoteAtFront(String exampleArgument) {
        if(exampleArgument.startsWith("\"")) {
            return exampleArgument.substring(1);
        }
        return exampleArgument;
    }
    
    @Nonnull
    private String removeQuoteAtBack(String exampleArgument) {
        if(exampleArgument.endsWith("\"")) {
            return exampleArgument.substring(0, exampleArgument.length() - 1);
        }
        return exampleArgument;
    }
}
