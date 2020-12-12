package com.blamejared.crafttweaker_annotation_processors.processors.validation;

import com.blamejared.crafttweaker_annotation_processors.processors.*;
import org.openzen.zencode.java.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.*;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker.api.annotations.BracketResolver"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BracketHandlerCheckValidationProcessor extends AbstractProcessor {
    //private static final String BEPReturnTypeCanonicalName = "com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                
                final AnnotationMirror annotationMirror = AnnotationMirrorUtil.getMirror(element, annotation);
                
                if(annotationMirror == null) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "\nInternal Error: Element annotated with @BracketHandler does not have an annotationMirror for it", element);
                    continue;
                }
                
                if (!(element instanceof ExecutableElement)) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "How is this annotated?", element, annotationMirror);
                    continue;
                }
                
                

                ExecutableElement executableElement = (ExecutableElement) element;
                if(element.getAnnotation(ZenCodeType.Method.class) == null) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "\nElements annotated with @BracketHandler should also be annotated with @ZenCodeType.Method", element, annotationMirror);
                }
                
                final List<? extends VariableElement> parameters = executableElement.getParameters();
                if (parameters.size() != 1 || !this.processingEnv.getTypeUtils()
                        .isSameType(parameters.get(0).asType(), this.processingEnv.getElementUtils()
                                .getTypeElement(String.class.getCanonicalName())
                                .asType())) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "\nElement is annotated as BEP but does not accept a String as its only parameter.", element, annotationMirror);
                }

                /*
                TODO: Allow non-commandstringDisplayables (vanilla types!)
                if (!this.processingEnv.getTypeUtils()
                        .isAssignable(executableElement.getReturnType(), this.processingEnv.getElementUtils()
                                .getTypeElement(BEPReturnTypeCanonicalName)
                                .asType())) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "\nElement is annotated as BEP but does not return " + BEPReturnTypeCanonicalName + " or any subtype.", element, annotationMirror);
                }
                 */
            }
        }

        return false;
    }
}
