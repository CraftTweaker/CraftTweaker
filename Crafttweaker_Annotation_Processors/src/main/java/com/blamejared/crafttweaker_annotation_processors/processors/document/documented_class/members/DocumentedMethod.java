package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members;

import com.blamejared.crafttweaker_annotation_processors.processors.document.Writable;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.CommentUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types.DocumentedType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DocumentedMethod implements Writable {

    private final String name;
    private final List<DocumentedParameter> parameterList;
    private final DocumentedClass containingClass;
    private final String callee; //full class name if static, else my[Type] or content of docParam this
    private final String docComment;
    private final DocumentedType returnType;

    public DocumentedMethod(DocumentedClass containingClass, String name, List<DocumentedParameter> parameterList, String callee, String docComment, DocumentedType returnType) {
        this.containingClass = containingClass;
        this.name = name;
        this.parameterList = parameterList;
        this.callee = callee;
        this.docComment = docComment;
        this.returnType = returnType;
    }

    public static DocumentedMethod convertMethod(DocumentedClass containingClass, ExecutableElement method, ProcessingEnvironment environment) {
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
        boolean optionalsHit = false;
        for (VariableElement parameter : method.getParameters()) {
            final DocumentedParameter e = DocumentedParameter.fromElement(parameter, environment);
            if (e == null) {
                continue;
            }

            if (optionalsHit && !e.isOptional()) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Non-Optional parameter after an optional one!", parameter);
            }
            optionalsHit = optionalsHit || e.isOptional();
            parameterList.add(e);
        }
        final boolean isStatic = method.getModifiers().contains(Modifier.STATIC);

        final String docComment = environment.getElementUtils().getDocComment(method);


        String callee = null;
        if (isStatic) {
            callee = containingClass.getZSName();
        } else if (docComment != null) {
            final String docParam_this = CommentUtils.joinDocAnnotation(docComment, "docParam this", environment)
                    .trim();
            if (!docParam_this.isEmpty()) {
                callee = docParam_this;
            }
        }
        if (callee == null) {
            callee = containingClass.getDocParamThis();
        }

        final DocumentedType returnType = method.getReturnType()
                .getKind() == TypeKind.VOID ? null : DocumentedType.fromTypeMirror(method.getReturnType(), environment);


        return new DocumentedMethod(containingClass, name, parameterList, callee, CommentUtils.formatDocCommentForDisplay(method, environment), returnType);

    }

    public String getName() {
        return name;
    }

    public DocumentedMethod withCallee(String c) {
        if(c != null) {
            return new DocumentedMethod(containingClass, name, parameterList, c, docComment, returnType);
        }
        return this;
    }

    @Override
    public void write(PrintWriter writer) {
        writer.println();

        writer.println(docComment);
        writer.println();
        if (returnType != null) {
            writer.println("Returns " + returnType.getClickableMarkdown());
            writer.println();
        }
        writer.println("```zenscript");
        DocumentedParameter.printAllCalls(callee + "." + name, parameterList, writer);
        writer.println("```");
        writer.println();

        if (!parameterList.isEmpty()) {
            DocumentedParameter.printTable(parameterList, writer);
            writer.println();
        }
    }

    public DocumentedClass getContainingClass() {
        return containingClass;
    }

    public String getCallee() {
        return callee;
    }

    public List<DocumentedParameter> getParameterList() {
        return parameterList;
    }
}
