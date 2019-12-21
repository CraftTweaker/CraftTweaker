package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DocumentedConstructor implements Writable {

    public static final Comparator<? super DocumentedConstructor> compareByParameterCount = Comparator.comparingInt(e -> e.parameterList
            .size());


    private final List<DocumentedParameter> parameterList;

    public DocumentedConstructor(List<DocumentedParameter> parameterList) {
        this.parameterList = parameterList;
    }

    public static DocumentedConstructor fromConstructor(ExecutableElement method, ProcessingEnvironment environment) {
        if (method.getKind() != ElementKind.CONSTRUCTOR) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal Error: Expected this to be a construcor", method);
            return null;
        }

        final List<DocumentedParameter> parameters = new ArrayList<>();
        for (VariableElement parameter : method.getParameters()) {
            parameters.add(DocumentedParameter.fromElement(parameter, environment));
        }
        return new DocumentedConstructor(parameters);
    }

    @Override
    public void write(PrintWriter writer) {

    }
}
