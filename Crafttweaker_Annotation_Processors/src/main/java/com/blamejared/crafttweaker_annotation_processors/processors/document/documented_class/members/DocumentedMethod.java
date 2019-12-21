package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentedMethod implements Writable {

    private final String name;
    private final List<DocumentedParameter> parameterList;

    public DocumentedMethod(String name, List<DocumentedParameter> parameterList) {
        this.name = name;
        this.parameterList = parameterList;
    }

    public static DocumentedMethod convertMethod(ExecutableElement method, ProcessingEnvironment environment) {
        final ZenCodeType.Method methodAnnotation = method.getAnnotation(ZenCodeType.Method.class);
        if (methodAnnotation == null) {
            //Nani?
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: method has not Method Annotation", method);
            return null;
        }
        final String name = methodAnnotation.value()
                .isEmpty() ? method.getSimpleName()
                .toString() : methodAnnotation.value();

        final List<DocumentedParameter> parameterList = new ArrayList<>();
        for (VariableElement parameter : method.getParameters()) {
            parameterList.add(DocumentedParameter.fromElement(parameter, environment));
        }
        return new DocumentedMethod(name, parameterList);

    }

    public String getName() {
        return name;
    }

    @Override
    public void write(PrintWriter writer) {
        writer.println();

        final String collect = parameterList.stream()
                .map(DocumentedParameter::getZSDescription)
                .collect(Collectors.joining(", ", getName() + "(", ");"));
        writer.println(collect);
        writer.println();
    }

    public List<DocumentedParameter> getParameterList() {
        return parameterList;
    }
}
