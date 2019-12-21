package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types.DocumentedType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.Comparator;

public class DocumentedCaster implements Writable {

    public static Comparator<DocumentedCaster> byZSName = Comparator.comparing(DocumentedCaster::getResultType, DocumentedType.byZSName);

    private final DocumentedType resultType;
    private final boolean implicit;

    public DocumentedCaster(DocumentedType resultType, boolean implicit) {
        this.resultType = resultType;
        this.implicit = implicit;
    }

    public static DocumentedCaster fromMethod(ExecutableElement element, ProcessingEnvironment environment) {
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

        return new DocumentedCaster(DocumentedType.fromTypeMirror(element.getReturnType(), environment), caster.implicit());
    }

    public DocumentedType getResultType() {
        return resultType;
    }

    public boolean isImplicit() {
        return implicit;
    }

    @Override
    public void write(PrintWriter writer) {

    }
}
