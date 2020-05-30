package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.DocumentedType;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.CommentUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Anything that is gettable or assignable using {@code val = X.name} or {@code X.name = val}
 */
public class DocumentedParameter {
    private final String name;
    private final DocumentedType type;
    private final boolean optional;
    private final String defaultValue;
    private final String[] examples;
    private final String description;

    private DocumentedParameter(String name, DocumentedType type, boolean optional, String defaultValue, String[] examples, String description) {
        this.name = name;
        this.type = type;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.examples = examples;
        this.description = description;
    }

    public static DocumentedParameter fromElement(VariableElement element, ProcessingEnvironment environment) {
        if (element.getKind() != ElementKind.PARAMETER) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal Error: Expected this to be a parameter!", element);
            return null;
        }
    
        if(element.asType().toString().startsWith("java.lang.Class")) {
            return null;
        }

        final DocumentedType type = DocumentedType.fromElement(element, environment);
        final String aDefault = getDefault(element);
        final boolean optional = aDefault != null;
        final String name = element.getSimpleName().toString();


        final String methodDocComment = environment.getElementUtils()
                .getDocComment(element.getEnclosingElement());
        final String[] examples = CommentUtils.findAllAnnotation(methodDocComment, "@docParam " + name);

        final String description = CommentUtils.joinDocAnnotation(element, "@param " + name, environment)
                .trim();


        return new DocumentedParameter(name, type, optional, aDefault, examples, description.isEmpty() ? null : description);
    }

    public static List<DocumentedParameter> getMethodParameters(ExecutableElement method, ProcessingEnvironment environment, boolean isExpansion) {
        final List<DocumentedParameter> parameterList = new ArrayList<>();
        boolean optionalsHit = false;
        final List<? extends VariableElement> parameters = method.getParameters();
        for (VariableElement parameter : isExpansion ? parameters.subList(1, parameters.size()) : parameters) {
            final DocumentedParameter e = DocumentedParameter.fromElement(parameter, environment);
            if (e == null) {
                continue;
            }

            if (optionalsHit && !e.isOptional()) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Non-Optional parameter after an optional one!", parameter);
            }
            optionalsHit = optionalsHit || e.isOptional();
            parameterList.add(e);
        }
        return parameterList;
    }


    private static String getDefault(VariableElement element) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final String typeName = annotationMirror.getAnnotationType().toString();
            if (typeName.matches("org\\.openzen\\.zencode\\.java\\.ZenCodeType\\.Optional(Double|String|Int|Float|Long)?")) {
                return annotationMirror.getElementValues()
                        .values()
                        .stream()
                        .map(av -> av.getValue().toString())
                        .findAny()
                        //Not the best way since it does not distinct between floats and int numbers but whatever
                        .orElse(typeName.endsWith("Optional") ? "null" : "0");
            }
        }
        return null;
    }

    public static void printAllCalls(String callee, List<DocumentedParameter> parameterList, List<DocumentedTypeParameter> typeParameterList, PrintWriter writer) {
        
        final String collect = parameterList.stream()
                .map(DocumentedParameter::getZSDescription)
                .collect(Collectors.joining(", "));
        writer.printf("%s%s(%s);%n", callee, DocumentedTypeParameter.formatForDescriptionCall(typeParameterList), collect);
    
        for(String exampleTypeArgument : DocumentedTypeParameter.getExampleTypeArguments(typeParameterList)) {
            final int reduce = parameterList.stream()
                    .mapToInt(d -> d.getExamples().length)
                    .reduce((left, right) -> left * right).orElse(0);
            if (reduce > 0 || !typeParameterList.isEmpty()) {
                List<String> ss = new ArrayList<>();
                List<String> lastCall = new ArrayList<>();
        
                for (DocumentedParameter documentedParameter : parameterList) {
                    if (documentedParameter.isOptional()) {
                        ss.add(String.join(", ", lastCall));
                    }
                    lastCall.add(documentedParameter.getExamples()[0]);
                }
                ss.add(String.join(", ", lastCall));
        
                for (String s : ss) {
                    writer.printf("%s%s(%s);%n", callee, exampleTypeArgument, s);
                }
            }
        }
    }

    public static void printTable(List<DocumentedParameter> parameterList, PrintWriter writer) {
        final boolean containsOptionals = parameterList.stream().anyMatch(DocumentedParameter::isOptional);
        if (containsOptionals) {
            writer.println("| Parameter | Type | Description | IsOptional | Default Value |");
            writer.println("|-----------|------|-------------|------------|---------------|");
        } else {
            writer.println("| Parameter | Type | Description |");
            writer.println("|-----------|------|-------------|");
        }
        for (DocumentedParameter documentedParameter : parameterList) {
            documentedParameter.writeTable(writer, containsOptionals);
        }
        writer.println();
    }

    public String getName() {
        return name;
    }

    public DocumentedType getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public String[] getExamples() {
        return examples;
    }

    public String getZSDescription() {
        return String.format("%s as %s", getName(), getType().getZSName());
    }

    void writeTable(PrintWriter writer, boolean containsOptionals) {
        final String desc = description == null ? "No description provided" : description;
        if (containsOptionals) {
            writer.printf("| %s | %s | %s | %s | `%s` |%n", getName(), type.getClickableMarkdown(), desc, isOptional(), defaultValue);
        } else {
            writer.printf("| %s | %s | %s |%n", getName(), type.getClickableMarkdown(), desc);
        }
    }
}
