package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types.DocumentedType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.Comparator;

public class DocumentedCaster {

    public static Comparator<DocumentedCaster> byZSName = Comparator.comparing(DocumentedCaster::getResultType, DocumentedType.byZSName);

    private final DocumentedClass containingClass;
    private final DocumentedType resultType;
    private final boolean implicit;

    public DocumentedCaster(DocumentedClass containingClass, DocumentedType resultType, boolean implicit) {
        this.containingClass = containingClass;
        this.resultType = resultType;
        this.implicit = implicit;
    }

    public static DocumentedCaster fromMethod(DocumentedClass containingClass, ExecutableElement element, ProcessingEnvironment environment) {
        if (element.getKind() != ElementKind.METHOD) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be a method!", element);
            return null;
        }

        final ZenCodeType.Caster caster = element.getAnnotation(ZenCodeType.Caster.class);
        if (caster == null) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to have a Caster annotation", element);
            return null;
        }

        return new DocumentedCaster(containingClass, DocumentedType.fromTypeMirror(element.getReturnType(), environment), caster.implicit());
    }

    public DocumentedType getResultType() {
        return resultType;
    }

    public void writeTable(PrintWriter writer) {
        writer.printf("| %s | %s |%n", resultType.getClickableMarkdown(), implicit);
    }
}
