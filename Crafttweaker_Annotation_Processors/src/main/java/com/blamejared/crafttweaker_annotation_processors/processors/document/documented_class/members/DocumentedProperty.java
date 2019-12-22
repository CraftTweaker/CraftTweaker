package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types.DocumentedType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.Objects;

public class DocumentedProperty implements Writable {
    private final String name;
    private final boolean hasGetter, hasSetter;
    private final DocumentedType type;
    private final DocumentedClass containingClass;

    public DocumentedProperty(DocumentedClass containingClass, String name, DocumentedType type) {
        this(containingClass, name, false, false, type);
    }

    public DocumentedProperty(DocumentedClass containingClass, String name, boolean hasGetter, boolean hasSetter, DocumentedType type) {
        this.containingClass = containingClass;
        this.name = name;
        this.hasGetter = hasGetter;
        this.hasSetter = hasSetter;
        this.type = type;
    }

    public static DocumentedProperty merge(DocumentedProperty propertyA, DocumentedProperty propertyB, ProcessingEnvironment environment) {
        if (propertyB.name != null && propertyA.name != null && !propertyB.name.equals(propertyA.name)) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not merge properties " + propertyA.name + " and " + propertyB.name);
        }

        if (!Objects.equals(propertyA.containingClass, propertyB.containingClass)) {
            if (propertyA.containingClass != null && !propertyA.containingClass.extendsOrImplements(propertyB.containingClass)) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not merge properties from classes " + propertyA.containingClass
                                .getZSName() + " and " + propertyB.containingClass.getZSName());
            }
        }

        return new DocumentedProperty(propertyA.containingClass != null ? propertyA.containingClass : propertyB.containingClass, propertyA.name != null ? propertyA.name : propertyB.name, propertyA.hasGetter || propertyB.hasGetter, propertyA.hasSetter || propertyB.hasSetter, propertyA.type == null ? propertyB.type : propertyA.type);

    }

    public static DocumentedProperty fromField(DocumentedClass containingClass, Element field, ProcessingEnvironment environment) {
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

        final DocumentedType type = DocumentedType.fromTypeMirror(field.asType(), environment);
        return new DocumentedProperty(containingClass, name, true, true, type);
    }

    public static DocumentedProperty fromMethod(DocumentedClass containingClass, Element method, ProcessingEnvironment environment) {
        if (method.getKind() != ElementKind.METHOD) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be a getter or setter method!", method);
            return null;
        }

        final ZenCodeType.Getter getter = method.getAnnotation(ZenCodeType.Getter.class);
        if (getter != null) {
            return fromGetter(containingClass, (ExecutableElement) method, getter, environment);
        }

        final ZenCodeType.Setter setter = method.getAnnotation(ZenCodeType.Setter.class);
        if (setter != null) {
            return fromSetter(containingClass, (ExecutableElement) method, setter, environment);
        }

        environment.getMessager()
                .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be either have a setter or getter annotation!", method);
        return null;
    }

    private static DocumentedProperty fromGetter(DocumentedClass containingClass, ExecutableElement method, ZenCodeType.Getter annotation, ProcessingEnvironment environment) {
        final String name;
        if (annotation != null && !annotation.value().isEmpty()) {
            name = annotation.value();
        } else {
            name = method.getSimpleName().toString();
        }

        if (method.getParameters().size() != 0) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Getter methods need to have no parameters!", method);
        }

        if (method.getReturnType().getKind() == TypeKind.VOID) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Getter methods may not return void!", method);
        }

        DocumentedType type = DocumentedType.fromTypeMirror(method.getReturnType(), environment);
        return new DocumentedProperty(containingClass, name, true, false, type);
    }

    private static DocumentedProperty fromSetter(DocumentedClass containingClass, ExecutableElement method, ZenCodeType.Setter annotation, ProcessingEnvironment environment) {
        final String name;
        if (annotation != null && !annotation.value().isEmpty()) {
            name = annotation.value();
        } else {
            name = method.getSimpleName().toString();
        }

        if (method.getParameters().size() != 1) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Setter method needs to have exactly one parameter!", method);
        }

        if (method.getReturnType().getKind() != TypeKind.VOID) {
            environment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Setter method need to return void!", method);
        }

        DocumentedType type = DocumentedType.fromElement(method.getParameters().get(0), environment);
        return new DocumentedProperty(containingClass, name, false, true, type);
    }

    public String getName() {
        return name;
    }

    @Override
    public void write(PrintWriter writer) {
        writer.printf("| %s | %s | %s | %s |%n", name, type.getClickableMarkdown(), hasGetter, hasSetter);
    }
}
