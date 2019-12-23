package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.CommentUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.DocumentedType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
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

        final DocumentedType type = DocumentedType.fromElement(element, environment);
        final String aDefault = getDefault(element);
        final boolean optional = aDefault != null;
        final String name = element.getSimpleName().toString();


        final String methodDocComment = environment.getElementUtils()
                .getDocComment(element.getEnclosingElement());
        final String[] examples = CommentUtils.findAllAnnotation(methodDocComment, "@docParam " + name);

        final String description = CommentUtils.joinDocAnnotation(methodDocComment, "@param " + name, environment)
                .trim();


        return new DocumentedParameter(name, type, optional, aDefault, examples, description.isEmpty() ? null : description);
    }


    private static String getDefault(VariableElement element) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final String typeName = annotationMirror.getAnnotationType().toString();
            if (typeName.matches("org\\.openzen\\.zencode\\.java\\.ZenCodeType\\.Optional(Double|String|Int|Float|Long)?")) {
                return annotationMirror.getElementValues()
                        .values()
                        .stream()
                        .map(Object::toString)
                        .findAny()
                        //Not the best way since it does not distinct between floats and int numbers but whatever
                        .orElse(typeName.endsWith("Optional") ? "null" : "0");
            }
        }
        return null;
    }

    public static void printAllCalls(String callee, List<DocumentedParameter> parameterList, PrintWriter writer) {
        final String collect = parameterList.stream()
                .map(DocumentedParameter::getZSDescription)
                .collect(Collectors.joining(", "));
        writer.printf("%s(%s);%n", callee, collect);

        final int reduce = parameterList.stream()
                .mapToInt(d -> d.getExamples().length)
                .reduce((left, right) -> left * right).orElse(0);
        if (reduce > 0) {
            //TODO: WTF was I thinking? These generated calls are nonsense
            // They generate calls with middle optionals left out -.-
            // Back to the drawing board I guess..?

            List<List<String>> withOptionals = new ArrayList<>();
            {
                if (parameterList.get(0).isOptional()) {
                    withOptionals.add(new ArrayList<>());
                }
                final ArrayList<String> t = new ArrayList<>();
                t.add(parameterList.get(0).getExamples()[0]);
                withOptionals.add(t);
            }

            for (DocumentedParameter documentedParameter : parameterList.subList(1, parameterList.size())) {
                if (documentedParameter.isOptional()) {
                    final List<List<String>> newWithOptionals = new ArrayList<>();
                    for (List<String> withOptional : withOptionals) {
                        newWithOptionals.add(withOptional);
                        final ArrayList<String> t = new ArrayList<>(withOptional);
                        t.add(documentedParameter.getExamples()[0]);
                        newWithOptionals.add(t);
                    }
                    withOptionals = newWithOptionals;
                } else {
                    for (List<String> withOptional : withOptionals) {
                        withOptional.add(documentedParameter.getExamples()[0]);
                    }
                }
            }

            for (List<String> withOptional : withOptionals) {
                final String exampleFit = String.join(", ", withOptional);
                writer.printf("%s(%s);%n", callee, exampleFit);
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
            writer.printf("| %s | %s | %s | %s | %s |%n", getName(), type.getClickableMarkdown(), desc, isOptional(), defaultValue);
        } else {
            writer.printf("| %s | %s | %s |%n", getName(), type.getClickableMarkdown(), desc);
        }
    }
}
