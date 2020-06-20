package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members;

import com.blamejared.crafttweaker_annotation_processors.processors.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.*;
import org.openzen.zencode.java.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.tools.*;
import java.io.*;
import java.util.*;

public class DocumentedMethod implements Writable {
    
    private static final String bracketHandlerAnnotation = "com.blamejared.crafttweaker.api.annotations.BracketResolver";
    
    private final String name;
    private final List<DocumentedParameter> parameterList;
    private final CrafttweakerDocumentationPage containingPage;
    private final String callee; //full class name if static, else my[Type] or content of docParam this
    private final String docComment;
    private final DocumentedType returnType;
    private final String bracketHandlerName;
    private final List<DocumentedTypeParameter> typeParameters;
    
    public DocumentedMethod(CrafttweakerDocumentationPage containingPage, String name, List<DocumentedParameter> parameterList, List<DocumentedTypeParameter> documentedTypeParameters, String callee, String docComment, DocumentedType returnType, String bracketHandlerName) {
        this.containingPage = containingPage;
        this.name = name;
        this.parameterList = parameterList;
        this.callee = callee;
        this.docComment = docComment;
        this.returnType = returnType;
        this.bracketHandlerName = bracketHandlerName;
        typeParameters = documentedTypeParameters;
    }
    
    public DocumentedType getReturnType() {
        return returnType;
    }
    
    public static DocumentedMethod convertMethod(CrafttweakerDocumentationPage containingPage, ExecutableElement method, ProcessingEnvironment environment, boolean isExpansion) {
        final ZenCodeType.Method methodAnnotation = method.getAnnotation(ZenCodeType.Method.class);
        if(methodAnnotation == null) {
            //Nani?
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: method has not Method Annotation", method);
            return null;
        }
        
        if(!method.getModifiers().contains(Modifier.PUBLIC)) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Methods need to be public!", method);
            return null;
        }
        
        final String name = methodAnnotation.value().isEmpty() ? method.getSimpleName()
                .toString() : methodAnnotation.value();
        final List<DocumentedParameter> parameterList = DocumentedParameter.getMethodParameters(method, environment, isExpansion);
        final List<DocumentedTypeParameter> documentedTypeParameters = DocumentedTypeParameter.getMethodTypeParameters(method, environment, isExpansion);
        final boolean isStatic = method.getModifiers().contains(Modifier.STATIC);
        if(isExpansion && !isStatic) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Expansion methods must be static!", method);
        }
        
        String callee = null;
        if(!isExpansion && isStatic) {
            callee = containingPage.getZSName();
        } else if(isExpansion) {
            final String docParam_this = CommentUtils.joinDocAnnotation(method, "docParam " + method
                    .getParameters()
                    .get(0)
                    .getSimpleName(), environment).trim();
            if(!docParam_this.isEmpty()) {
                callee = docParam_this;
            }
        }
        if(callee == null) {
            final String docParam_this = CommentUtils.joinDocAnnotation(method, "docParam this", environment)
                    .trim();
            if(!docParam_this.isEmpty()) {
                callee = docParam_this;
            }
        }
        if(callee == null) {
            callee = containingPage.getDocParamThis();
        }
        
        final DocumentedType returnType = method.getReturnType()
                .getKind() == TypeKind.VOID ? null : DocumentedType.fromTypeMirror(method.getReturnType(), environment);
    
        final AnnotationMirror mirror = AnnotationMirrorUtil.getMirror(method, bracketHandlerAnnotation);
        final String bracketHandlerName = mirror == null ? null : AnnotationMirrorUtil.getAnnotationValue(mirror, "value");
    
        return new DocumentedMethod(containingPage, name, parameterList, documentedTypeParameters, callee, CommentUtils.formatDocCommentForDisplay(method, environment), returnType, bracketHandlerName);
        
    }
    
    public String getName() {
        return name;
    }
    
    public DocumentedMethod withCallee(String c) {
        if(c != null) {
            return new DocumentedMethod(containingPage, name, parameterList, typeParameters, c, docComment, returnType, bracketHandlerName);
        }
        return this;
    }
    
    @Override
    public void write(PrintWriter writer) {
        writer.println();
        
        if(docComment != null) {
            writer.println(docComment);
            writer.println();
        }
        if(returnType != null) {
            writer.println("Return type: " + returnType.getClickableMarkdown());
            writer.println();
        }
        writer.println("```zenscript");
        if(bracketHandlerName != null) {
            final DocumentedParameter documentedParameter = parameterList.get(0);
            final String[] examples = documentedParameter.getExamples();
            if(examples.length == 0) {
                writer.printf("<%s:%s>%n", bracketHandlerName, documentedParameter.getName());
            } else {
                for(String example : examples) {
                    writer.printf("<%s:%s>%n", bracketHandlerName, example.replaceAll("\"", ""));
                }
                writer.println();
            }
        }
        
        DocumentedParameter.printAllCalls(callee + "." + name, parameterList, typeParameters, writer);
        writer.println("```");
        writer.println();
        
        if(!typeParameters.isEmpty()){
            DocumentedTypeParameter.printTable(typeParameters, writer);
            writer.println();
        }
        
        if(!parameterList.isEmpty()) {
            DocumentedParameter.printTable(parameterList, writer);
            writer.println();
        }
    }
    
    public CrafttweakerDocumentationPage getContainingPage() {
        return containingPage;
    }
    
    public List<DocumentedParameter> getParameterList() {
        return parameterList;
    }
}
