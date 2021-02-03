package com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header;

import com.blamejared.crafttweaker_annotation_processors.processors.document.page.member.header.examples.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.page.type.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class MemberHeader implements Comparable<MemberHeader> {
    
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
    
    public void writeStaticExamples(PrintWriter writer, AbstractTypeInfo ownerType, String name) {
        final String callee = ownerType.getDisplayName();
        writeCallTo(writer, String.format("%s.%s", callee, name));
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
        writer.printf("%s%s;%n", callee, arguments);
    }
    
    private String getExample(int exampleIndex) {
        final String exampleTypeArgument = getExampleTypeArgument(exampleIndex);
        final String exampleArgument = getExampleArgument(exampleIndex);
        return String.format("%s(%s)", exampleTypeArgument, exampleArgument);
    }
    
    @Nonnull
    public String getExampleArgument(int exampleIndex) {
        return parameters.stream().map(parameters -> parameters.getExample(exampleIndex)).collect(Collectors.joining(", "));
    }
    
    @Nonnull
    private String getExampleTypeArgument(int exampleIndex) {
        if(genericParameters.isEmpty()) {
            return "";
        }
        
        return genericParameters.stream().map(parameters -> parameters.getExample(exampleIndex)).collect(Collectors.joining(", ", "<", ">"));
    }
    
    public int getNumberOfUsableExamples() {
        final IntStream parameterExampleCount = parameters.stream().mapToInt(DocumentedParameter::numberOfExamples);
        final IntStream genericParameterExampleCount = genericParameters.stream().mapToInt(DocumentedGenericParameter::numberOfExamples);
        
        return IntStream.concat(parameterExampleCount, genericParameterExampleCount).min().orElse(1);
    }
    
    public void writeParameterDescriptionTable(PrintWriter writer) {
        if(parameters.isEmpty() && genericParameters.isEmpty()) {
            return;
        }
        if(hasOptionalTypes()) {
            writeParameterDescriptionTableWithOptionals(writer);
        } else {
            writeParameterDescriptionTableWithoutOptionals(writer);
        }
        writer.println();
    }
    
    private void writeParameterDescriptionTableWithoutOptionals(PrintWriter writer) {
        writer.println("| Parameter | Type | Description |");
        writer.println("|-----------|------|-------------|");
        for(DocumentedParameter parameter : parameters) {
            parameter.writeParameterInfoExcludeOptionality(writer);
        }
        
        for(DocumentedGenericParameter genericParameter : genericParameters) {
            genericParameter.writeParameterInfoExcludeOptionality(writer);
        }
        writer.println();
    }
    
    private void writeParameterDescriptionTableWithOptionals(PrintWriter writer) {
        writer.println("| Parameter | Type | Description | Optional | DefaultValue |");
        writer.println("|-----------|------|-------------|----------|--------------|");
        for(DocumentedParameter parameter : parameters) {
            parameter.writeParameterInfoIncludeOptionality(writer);
        }
        
        for(DocumentedGenericParameter parameter : genericParameters) {
            parameter.writeParameterInfoIncludeOptionality(writer);
        }
        writer.println();
    }
    
    private boolean hasOptionalTypes() {
        return parameters.stream().anyMatch(DocumentedParameter::isOptional);
    }
    
    @Override
    public int compareTo(@Nonnull MemberHeader other) {
        int temp = this.parameters.size() - other.parameters.size();
        if(temp != 0) {
            return temp;
        }
        temp = this.genericParameters.size() - other.genericParameters.size();
        if(temp != 0) {
            return temp;
        }
    
        for(int i = 0; i < this.parameters.size(); i++) {
            temp = this.parameters.get(i).compareTo(other.parameters.get(i));
            if(temp != 0) {
                return temp;
            }
        }
    
        for(int i = 0; i < this.genericParameters.size(); i++) {
            temp = this.genericParameters.get(i).compareTo(other.genericParameters.get(i));
            if(temp != 0) {
                return temp;
            }
        }
        return 0;
    }
}
