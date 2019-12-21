package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.tools.Diagnostic;
import java.io.PrintWriter;

public class DocumentedProperty implements Writable {
    private final String name;
    private boolean hasGetter, hasSetter;

    public DocumentedProperty(String name) {
        this.name = name;
    }

    public DocumentedProperty(String name, boolean hasGetter, boolean hasSetter) {
        this.name = name;
        this.hasGetter = hasGetter;
        this.hasSetter = hasSetter;
    }

    public static DocumentedProperty merge(DocumentedProperty propertyA, DocumentedProperty propertyB, ProcessingEnvironment environment) {
        if (propertyB.name != null && propertyA.name != null && !propertyB.name.equals(propertyA.name)) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not merge properties " + propertyA.name + " and " + propertyB.name);
        }

        return new DocumentedProperty(propertyA.name != null ? propertyA.name : propertyB.name, propertyA.hasGetter || propertyB.hasGetter, propertyA.hasSetter || propertyB.hasSetter);

    }

    public static DocumentedProperty fromField(Element field, ProcessingEnvironment environment) {
        if (!field.getKind().isField()) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be a field!", field);
            return null;
        }

        final ZenCodeType.Field annotation = field.getAnnotation(ZenCodeType.Field.class);
        final String name;
        if (annotation != null && !annotation.value().isEmpty()) {
            name = annotation.value();
        } else {
            name = field.getSimpleName().toString();
        }
        return new DocumentedProperty(name, true, true);
    }

    public static DocumentedProperty fromMethod(Element method, ProcessingEnvironment environment) {
        if (method.getKind() != ElementKind.METHOD) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be a getter or setter method!", method);
        }

        final ZenCodeType.Getter getter = method.getAnnotation(ZenCodeType.Getter.class);
        if (getter != null) {
            return fromGetter(method, getter, environment);
        }

        final ZenCodeType.Setter setter = method.getAnnotation(ZenCodeType.Setter.class);
        if (setter != null) {
            return fromSetter(method, setter, environment);
        }

        environment.getMessager()
                .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be either have a setter or getter annotation!", method);
        return null;
    }

    private static DocumentedProperty fromGetter(Element method, ZenCodeType.Getter annotation, ProcessingEnvironment environment) {
        final String name;
        if (annotation != null && !annotation.value().isEmpty()) {
            name = annotation.value();
        } else {
            name = method.getSimpleName().toString();
        }
        return new DocumentedProperty(name, true, false);
    }

    private static DocumentedProperty fromSetter(Element method, ZenCodeType.Setter annotation, ProcessingEnvironment environment) {
        final String name;
        if (annotation != null && !annotation.value().isEmpty()) {
            name = annotation.value();
        } else {
            name = method.getSimpleName().toString();
        }
        return new DocumentedProperty(name, false, true);
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(PrintWriter writer) {

    }
}
