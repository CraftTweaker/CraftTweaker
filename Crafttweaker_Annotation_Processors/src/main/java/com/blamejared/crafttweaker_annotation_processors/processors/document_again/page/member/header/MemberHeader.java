package com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.member.header.examples.ExampleData;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.page.type.AbstractTypeInfo;

import javax.annotation.Nonnull;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MemberHeader {
    
    public final AbstractTypeInfo returnType;
    public final List<DocumentedParameter> parameters;
    public final List<DocumentedGenericParameter> genericParameters;
    
    public MemberHeader(AbstractTypeInfo returnType, List<DocumentedParameter> parameters, List<DocumentedGenericParameter> genericParameters) {
        this.returnType = returnType;
        this.parameters = parameters;
        this.genericParameters = genericParameters;
    }
    
    public String formatForSignatureExample() {
        return formatGenericParametersForSignatureExample() + formatParametersForSignatureExample();
    }
    
    private String formatGenericParametersForSignatureExample() {
        if(genericParameters.isEmpty()) {
            return "";
        }
        
        final StringJoiner stringJoiner = new StringJoiner(", ", "<", ">");
        for(DocumentedGenericParameter genericParameter : genericParameters) {
            stringJoiner.add(genericParameter.formatForSignatureExample());
        }
        return stringJoiner.toString();
    }
    
    private String formatParametersForSignatureExample() {
        final String displayName = returnType.getDisplayName();
        final StringJoiner stringJoiner = new StringJoiner(", ", "(", ") as " + displayName);
        for(DocumentedParameter parameter : parameters) {
            stringJoiner.add(parameter.formatForSignatureExample());
        }
        return stringJoiner.toString();
    }
    
    public void writeStaticExamples(PrintWriter writer, AbstractTypeInfo ownerType) {
        final String callee = ownerType.getDisplayName();
        writeCallTo(writer, callee);
    }
    
    public void writeVirtualExamples(PrintWriter writer, ExampleData aThis, String name) {
        final String example = aThis.getExampleFor("this").getAnyTextValue();
        writeCallTo(writer, String.format("%s.%s", example, name));
    }
    
    public void writeConstructorExamples(PrintWriter writer, AbstractTypeInfo constructedType) {
        writeCallTo(writer, String.format("new %s", constructedType.getDisplayName()));
    }
    
    private void writeCallTo(PrintWriter writer, String callee) {
        int numberOfUsableExamples = getNumberOfUsableExamples();
        if(numberOfUsableExamples == 0) {
            return;
        }
        for(int i = 0; i < numberOfUsableExamples; i++) {
            writeExample(writer, callee, i);
        }
    }
    
    private void writeExample(PrintWriter writer, String callee, int i) {
        final String arguments = getExample(i);
        writer.printf("%s%s%n", callee, arguments);
    }
    
    private String getExample(int exampleIndex) {
        final String exampleTypeArgument = getExampleTypeArgument(exampleIndex);
        final String exampleArgument = getExampleArgument(exampleIndex);
        return exampleTypeArgument + exampleArgument;
    }
    
    @Nonnull
    private String getExampleArgument(int exampleIndex) {
        return parameters.stream()
                .map(parameters -> parameters.getExample(exampleIndex))
                .collect(Collectors.joining(", ", "(", ")"));
    }
    
    @Nonnull
    private String getExampleTypeArgument(int exampleIndex) {
        if(genericParameters.isEmpty()) {
            return "";
        }
        
        return genericParameters.stream()
                .map(parameters -> parameters.getExample(exampleIndex))
                .collect(Collectors.joining(", ", "<", ">"));
    }
    
    private int getNumberOfUsableExamples() {
        final IntStream parameterExampleCount = parameters.stream()
                .mapToInt(DocumentedParameter::numberOfExamples);
        final IntStream genericParameterExampleCount = genericParameters.stream()
                .mapToInt(DocumentedGenericParameter::numberOfExamples);
        
        return IntStream.concat(parameterExampleCount, genericParameterExampleCount)
                .min()
                .orElse(1);
    }
    
}
