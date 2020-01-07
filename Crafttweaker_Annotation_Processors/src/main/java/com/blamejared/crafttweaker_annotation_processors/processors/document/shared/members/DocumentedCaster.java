package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.DocumentedType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Comparator;

public class DocumentedCaster {

    public static Comparator<DocumentedCaster> byZSName = Comparator.comparing(DocumentedCaster::getResultType, DocumentedType.byZSName);

    private final DocumentedType resultType;
    private final boolean implicit;

    public DocumentedCaster(DocumentedType resultType, boolean implicit) {
        this.resultType = resultType;
        this.implicit = implicit;
    }

    public static DocumentedCaster fromMethod(ExecutableElement element, ProcessingEnvironment environment, boolean isExpansion) {
        if (element.getKind() != ElementKind.METHOD) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be a method!", element);
            return null;
        }

        final ZenCodeType.Caster caster = element.getAnnotation(ZenCodeType.Caster.class);
        if (caster == null) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to have a Caster annotation!", element);
            return null;
        }

        if (element.getParameters().size() != (isExpansion ? 1 : 0)) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Caster methods may not have any parameters (or exactly one when an expansion)!", element);
            return null;
        }

        if (!element.getModifiers().contains(Modifier.PUBLIC)) {
            environment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Caster methods need to be public!", element);
            return null;
        }

        if (isExpansion != element.getModifiers().contains(Modifier.STATIC)) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Caster methods need to be nonstatic if not in an expansion and static when in an expansion!", element);
            return null;
        }

        return new DocumentedCaster(DocumentedType.fromTypeMirror(element.getReturnType(), environment), caster.implicit());
    }

    public static void printCasters(Collection<DocumentedCaster> casters, PrintWriter writer) {
        if (casters.isEmpty()) {
            return;
        }

        writer.println("## Casters");
        writer.println();
        writer.println("| Result type | Is Implicit |");
        writer.println("|-------------|-------------|");
        for (DocumentedCaster caster : casters) {
            caster.writeTable(writer);
        }

        writer.println();
    }

    public DocumentedType getResultType() {
        return resultType;
    }

    private void writeTable(PrintWriter writer) {
        writer.printf("| %s | %s |%n", resultType.getClickableMarkdown(), implicit);
    }
}
