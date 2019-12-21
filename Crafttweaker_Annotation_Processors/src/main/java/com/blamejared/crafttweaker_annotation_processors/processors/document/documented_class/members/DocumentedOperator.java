package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DocumentedOperator implements Writable {
    public static Comparator<DocumentedOperator> compareByOp = Comparator.comparing(DocumentedOperator::getType);
    private final ZenCodeType.OperatorType type;
    private final List<DocumentedParameter> parameterList;

    private DocumentedOperator(ZenCodeType.OperatorType type, List<DocumentedParameter> parameterList) {
        this.type = type;
        this.parameterList = parameterList;
    }

    public static DocumentedOperator fromMethod(ExecutableElement method, ProcessingEnvironment environment) {
        if (method.getKind() != ElementKind.METHOD) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to be a method!", method);
            return null;
        }

        final ZenCodeType.Operator operator = method.getAnnotation(ZenCodeType.Operator.class);
        if (operator == null) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this method to have an Operator annotation1", method);
            return null;
        }


        final ArrayList<DocumentedParameter> parameters = new ArrayList<>(method.getParameters().size());
        for (VariableElement parameter : method.getParameters()) {
            parameters.add(DocumentedParameter.fromElement(parameter, environment));
        }
        return new DocumentedOperator(operator.value(), parameters);
    }

    public ZenCodeType.OperatorType getType() {
        return type;
    }

    @Override
    public void write(PrintWriter writer) {

    }
}
