package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.FormattingUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document.CrafttweakerDocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.CommentUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.Writable;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
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
    private final String docComment;
    private final String callee;

    private DocumentedOperator(ZenCodeType.OperatorType operator, List<DocumentedParameter> parameterList, String docComment, String callee) {
        this.operator = operator;
        this.parameterList = parameterList;
        this.docComment = docComment;
        this.callee = callee;
    }

    public static DocumentedOperator fromMethod(CrafttweakerDocumentationPage containingPage, ExecutableElement method, ProcessingEnvironment environment, boolean isExpansion) {
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

        if (isExpansion != method.getModifiers().contains(Modifier.STATIC)) {
            if (isExpansion) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Expansion Operator methods must be static!", method);
            } else {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Operator methods must not be static!", method);
            }
            return null;
        }

        //-1 because 'this' is the 1st operand
        final int requiredOperands = FormattingUtils.getOperandCountFor(operator.value()) - (isExpansion ? 0 : 1);
        final List<? extends VariableElement> methodParameters = method.getParameters();
        if (requiredOperands != methodParameters.size()) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, String.format("Operator %s requires %d parameters in the method, received %d.", operator
                            .value(), requiredOperands, methodParameters.size()));
        }

        final String docComment = environment.getElementUtils().getDocComment(method);
        final List<DocumentedParameter> parameters;
        String callee = null;
        {
            final String s = CommentUtils.joinDocAnnotation(docComment, "@docComment this", environment).trim();
            if (!s.isEmpty()) {
                callee = s;
            }
        }


        if (isExpansion) {
            parameters = new ArrayList<>(methodParameters.size() - 1);
            for (VariableElement parameter : methodParameters.subList(1, methodParameters.size())) {
                parameters.add(DocumentedParameter.fromElement(parameter, environment));
            }

            if (callee == null) {
                final String docParam_this = CommentUtils.joinDocAnnotation(docComment, "docParam " + methodParameters.get(0)
                        .getSimpleName(), environment).trim();
                if (!docParam_this.isEmpty()) {
                    callee = docParam_this;
                }
            }
        } else {
            parameters = new ArrayList<>(methodParameters.size());
            for (VariableElement parameter : methodParameters) {
                parameters.add(DocumentedParameter.fromElement(parameter, environment));
            }
        }

        if (callee == null) {
            callee = containingPage.getDocParamThis();
        }


        return new DocumentedOperator(
                operator.value(),
                parameters,
                CommentUtils.formatDocCommentForDisplay(method, environment),
                callee
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
