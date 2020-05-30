package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.*;
import com.sun.tools.classfile.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class DocumentedTypeParameter {
    
    private final List<DocumentedType> bounds;
    private final String name;
    private final String[] examples;
    
    private DocumentedTypeParameter(List<DocumentedType> bounds, String name, String[] examples) {
        this.bounds = bounds;
        this.name = name;
        this.examples = examples;
    }
    
    public static List<DocumentedTypeParameter> getMethodTypeParameters(ExecutableElement method, ProcessingEnvironment environment, boolean isExpansion) {
        List<? extends TypeParameterElement> typeParameters = method.getTypeParameters();
        if(isExpansion) {
            final VariableElement variableElement = method.getParameters().get(0);
            final TypeMirror typeMirror = variableElement.asType();
            if(typeMirror instanceof Type.ClassType) {
                typeParameters = typeParameters.subList(((Type.ClassType) typeMirror).typeArgs.size(), typeParameters
                        .size());
            }
        }
        
        final List<DocumentedTypeParameter> result = new ArrayList<>();
        for(TypeParameterElement typeParameter : typeParameters) {
            result.add(getTypeParameter(typeParameter, method, environment));
        }
        return result;
    }
    
    private static DocumentedTypeParameter getTypeParameter(TypeParameterElement typeParameter, ExecutableElement method, ProcessingEnvironment environment) {
        final String name = typeParameter.getSimpleName().toString();
        final List<DocumentedType> documentedBounds = new ArrayList<>();
        final String[] examples = CommentUtils.findAllAnnotation(environment.getElementUtils()
                .getDocComment(method), "@docParam " + name);
        for(TypeMirror bound : typeParameter.getBounds()) {
            documentedBounds.add(DocumentedType.fromTypeMirror(bound, environment));
        }
        return new DocumentedTypeParameter(documentedBounds, name, examples);
    }
    
    public static void printTable(List<DocumentedTypeParameter> typeParameters, PrintWriter writer) {
        writer.println("| ParameterName | Bounds |");
        writer.println("|---------------|--------|");
        for(DocumentedTypeParameter typeParameter : typeParameters) {
            final String bounds = typeParameter.bounds.stream()
                    .map(DocumentedType::getClickableMarkdown)
                    .collect(Collectors.joining(" <br> "));
            writer.printf("| %s | %s |%n", typeParameter.name, bounds);
        }
    }
    
    public static String formatForDescriptionCall(List<DocumentedTypeParameter> typeParameterList) {
        if(typeParameterList.isEmpty()) {
            return "";
        }
        return typeParameterList.stream()
                .map(d -> d.name)
                .collect(Collectors.joining(", ", "<", ">"));
    }
    
    public static String[] getExampleTypeArguments(List<DocumentedTypeParameter> typeParameterList) {
        if(typeParameterList.isEmpty()) {
            return new String[]{""};
        }
        final boolean anyWithoutExample = typeParameterList.stream().anyMatch(d -> d.examples.length == 0);
        if(anyWithoutExample) {
            return new String[]{""};
        }
        final String example = typeParameterList.stream()
                .map(d -> d.examples[0])
                .collect(Collectors.joining(", ", "<", ">"));
        return new String[]{example};
    }
}
