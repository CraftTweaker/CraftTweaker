package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import java.util.Comparator;

public class DocumentedMethod {

    public static final Comparator<DocumentedMethod> compareByName = Comparator.comparing(DocumentedMethod::getName);
    private final String name;

    public DocumentedMethod(String name) {
        this.name = name;
    }

    public static DocumentedMethod convertMethod(ExecutableElement method, ProcessingEnvironment environment) {
        final ZenCodeType.Method methodAnnotation = method.getAnnotation(ZenCodeType.Method.class);
        if (methodAnnotation == null) {
            //Nani?
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: method has not Method Annotation", method);
            return null;
        }
        final DocumentedMethod out = new DocumentedMethod(methodAnnotation.value()
                .isEmpty() ? method.getSimpleName()
                .toString() : methodAnnotation.value());

        //TODO
        return out;
    }

    public String getName() {
        return name;
    }
}
