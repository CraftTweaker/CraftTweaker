package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.FormattingUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.CommentUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;
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
    public static Comparator<DocumentedOperator> compareByOp = Comparator.comparing(DocumentedOperator::getOperator);
    private final ZenCodeType.OperatorType operator;
    private final List<DocumentedParameter> parameterList;
    private final DocumentedClass containingClass;
    private final String docComment;
    private final String callee;

    private DocumentedOperator(DocumentedClass containingClass, ZenCodeType.OperatorType operator, List<DocumentedParameter> parameterList, String docComment, String callee) {
        this.containingClass = containingClass;
        this.operator = operator;
        this.parameterList = parameterList;
        this.docComment = docComment;
        this.callee = callee;
    }

    public static DocumentedOperator fromMethod(DocumentedClass containingClass, ExecutableElement method, ProcessingEnvironment environment) {
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

        //-1 because 'this' is the 1st operand
        final int requiredOperands = FormattingUtils.getOperandCountFor(operator.value()) - 1;
        if(requiredOperands != method.getParameters().size()){
            environment.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Operator %s requires %d parameters in the method, received %d.", operator.value(), requiredOperands, method.getParameters().size()));
        }


        final ArrayList<DocumentedParameter> parameters = new ArrayList<>(method.getParameters().size());
        for (VariableElement parameter : method.getParameters()) {
            parameters.add(DocumentedParameter.fromElement(parameter, environment));
        }
        final String docComment = environment.getElementUtils().getDocComment(method);

        final String s = CommentUtils.joinDocAnnotation(docComment, "@docComment this", environment);

        return new DocumentedOperator(containingClass,
                operator.value(),
                parameters,
                CommentUtils.formatDocCommentForDisplay(method, environment),
                s.isEmpty() ? containingClass.getDocParamThis() : s
        );
    }

    public ZenCodeType.OperatorType getOperator() {
        return operator;
    }

    @Override
    public void write(PrintWriter writer) {
        writer.printf("### %s%n", this.operator);
        writer.println();
        if (docComment != null) {
            writer.println(docComment);
            writer.println();
        }

        final int operandCount = FormattingUtils.getOperandCountFor(operator);
        final String operatorFormat = FormattingUtils.getOperatorFormat(operator) + "%n";

        writer.println("```zenscript");
        {
            final List<String> generalDesc = new ArrayList<>(operandCount);
            generalDesc.add(callee);
            for (DocumentedParameter documentedParameter : parameterList) {
                generalDesc.add(documentedParameter.getZSDescription());
            }
            writer.printf(operatorFormat, generalDesc.toArray());
        }
        {
            final int i = parameterList.stream()
                    .mapToInt(s -> s.getExamples().length)
                    .reduce((a, b) -> a * b)
                    .orElse(0);
            if (i > 0) {
                final List<String> exampleDesc = new ArrayList<>(operandCount);
                exampleDesc.add(callee);
                for (DocumentedParameter documentedParameter : parameterList) {
                    exampleDesc.add(documentedParameter.getExamples()[0]);
                }
                writer.printf(operatorFormat, exampleDesc.toArray());
            }
        }

        writer.println("```");
        writer.println();
        writer.println("| Parameter | Type | Description |");
        writer.println("|-----------|------|-------------|");
        for (DocumentedParameter documentedParameter : parameterList) {
            documentedParameter.writeTable(writer, false);
        }
    }
}
