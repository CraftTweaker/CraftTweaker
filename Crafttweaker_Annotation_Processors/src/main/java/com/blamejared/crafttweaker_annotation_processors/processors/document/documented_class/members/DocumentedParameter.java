package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types.DocumentedType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.io.PrintWriter;

/**
 * Anything that is gettable or assignable using {@code val = X.name} or {@code X.name = val}
 */
public class DocumentedParameter implements Writable {
    private final String name;
    private final DocumentedType type;
    private final boolean optional;
    private final String defaultValue;
    private final String[] examples;

    private DocumentedParameter(String name, DocumentedType type, boolean optional, String defaultValue, String[] examples) {
        this.name = name;
        this.type = type;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.examples = examples;
    }

    public static DocumentedParameter fromElement(VariableElement element, ProcessingEnvironment environment) {
        if (element.getKind() != ElementKind.PARAMETER) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal Error: Expected this to be a parameter!", element);
            return null;
        }

        final DocumentedType type = DocumentedType.fromElement(element, environment);
        final String aDefault = getDefault(element);
        final boolean optional = aDefault == null;
        final String name = element.getSimpleName().toString();
        final String[] examples = new String[0];//TODO
        return new DocumentedParameter(name, type, optional, aDefault, examples);
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

    public String getName() {
        return name;
    }

    public DocumentedType getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String[] getExamples() {
        return examples;
    }

    public String getZSDescription() {
        return String.format("%s as %s", getName(), getType().getZSName());
    }

    @Override
    public void write(PrintWriter writer) {

    }
}
