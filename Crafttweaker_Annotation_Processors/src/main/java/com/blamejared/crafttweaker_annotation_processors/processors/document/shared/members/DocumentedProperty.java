package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.CrafttweakerDocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.DocumentedType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Objects;

public class DocumentedProperty {
    private final String name;
    private final boolean hasGetter, hasSetter;
    private final DocumentedType type;
    private final CrafttweakerDocumentationPage containingPage;

    private DocumentedProperty(CrafttweakerDocumentationPage containingPage, String name, boolean hasGetter, boolean hasSetter, DocumentedType type) {
        this.containingPage = containingPage;
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

        if (!Objects.equals(propertyA.containingPage, propertyB.containingPage)) {
            if (propertyA.containingPage != null) {
                if (!(propertyA.containingPage instanceof DocumentedClass)
                        || !(propertyB.containingPage instanceof DocumentedClass)
                        || !((DocumentedClass) propertyA.containingPage).extendsOrImplements((DocumentedClass) propertyB.containingPage)) {
                    environment.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not merge properties from classes " + propertyA.containingPage
                                    .getZSName() + " and " + propertyB.containingPage.getZSName());
                }
            }
        }

        return new DocumentedProperty(propertyA.containingPage != null ? propertyA.containingPage : propertyB.containingPage, propertyA.name != null ? propertyA.name : propertyB.name, propertyA.hasGetter || propertyB.hasGetter, propertyA.hasSetter || propertyB.hasSetter, propertyA.type == null ? propertyB.type : propertyA.type);

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

    public static DocumentedProperty fromMethod(CrafttweakerDocumentationPage containingClass, Element method, ProcessingEnvironment environment, boolean isExpansion) {
        if (method.getKind() != ElementKind.METHOD) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be a getter or setter method!", method);
            return null;
        }

        if (isExpansion != method.getModifiers().contains(Modifier.STATIC)) {
            if (isExpansion) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Expansion Getters/Setters must be static!", method);
            } else {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Getters/Setters must not be static!", method);
            }
            return null;
        }

        final ZenCodeType.Getter getter = method.getAnnotation(ZenCodeType.Getter.class);
        if (getter != null) {
            return fromGetter(containingClass, (ExecutableElement) method, getter, environment, isExpansion);
        }

        final ZenCodeType.Setter setter = method.getAnnotation(ZenCodeType.Setter.class);
        if (setter != null) {
            return fromSetter(containingClass, (ExecutableElement) method, setter, environment, isExpansion);
        }

        environment.getMessager()
                .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be either have a setter or getter annotation!", method);
        return null;
    }

    private static DocumentedProperty fromGetter(CrafttweakerDocumentationPage containingClass, ExecutableElement method, ZenCodeType.Getter annotation, ProcessingEnvironment environment, boolean isExpansion) {
        final String name;
        if (annotation != null && !annotation.value().isEmpty()) {
            name = annotation.value();
        } else {
            name = method.getSimpleName().toString();
        }

        if (method.getParameters().size() != (isExpansion ? 1 : 0)) {
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

    private static DocumentedProperty fromSetter(CrafttweakerDocumentationPage containingClass, ExecutableElement method, ZenCodeType.Setter annotation, ProcessingEnvironment environment, boolean isExpansion) {
        final String name;
        if (annotation != null && !annotation.value().isEmpty()) {
            name = annotation.value();
        } else {
            name = method.getSimpleName().toString();
        }

        if (method.getParameters().size() != (isExpansion ? 2 : 1)) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Setter method needs to have exactly one parameter!", method);
        }

        if (method.getReturnType().getKind() != TypeKind.VOID) {
            environment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Setter method need to return void!", method);
        }

        DocumentedType type = DocumentedType.fromElement(method.getParameters().get(isExpansion ? 1 : 0), environment);
        return new DocumentedProperty(containingClass, name, false, true, type);
    }

    public static void printProperties(Collection<DocumentedProperty> properties, PrintWriter writer) {
        if (properties.isEmpty()) {
            return;
        }

        writer.println("## Properties");
        writer.println();
        writer.println("| Name | Type | Has Getter | Has Setter |");
        writer.println("|------|------|------------|------------|");
        for (DocumentedProperty value : properties) {
            value.writeTable(writer);
        }

        writer.println();
    }

    public String getName() {
        return name;
    }

    private void writeTable(PrintWriter writer) {
        writer.printf("| %s | %s | %s | %s |%n", name, type.getClickableMarkdown(), hasGetter, hasSetter);
    }
}
