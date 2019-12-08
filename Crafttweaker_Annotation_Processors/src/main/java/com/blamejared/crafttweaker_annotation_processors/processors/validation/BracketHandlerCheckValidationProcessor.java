package com.blamejared.crafttweaker_annotation_processors.processors.validation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker.api.annotations.BracketResolver"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BracketHandlerCheckValidationProcessor extends AbstractProcessor {
    private static final String BEPReturnTypeCanonicalName = "com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if(!(element instanceof ExecutableElement)) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "How is this annotated?", element);
                    continue;
                }

                ExecutableElement executableElement = (ExecutableElement) element;
                final List<? extends VariableElement> parameters = executableElement.getParameters();
                if(parameters.size() != 1 || !this.processingEnv.getTypeUtils().isSameType(parameters.get(0).asType(), this.processingEnv.getElementUtils().getTypeElement(String.class.getCanonicalName()).asType())) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Element is annotated as BEP but does not accept a String as its only parameter.", element);
                }

                if(!this.processingEnv.getTypeUtils().isAssignable(executableElement.getReturnType(), this.processingEnv.getElementUtils().getTypeElement(BEPReturnTypeCanonicalName).asType())){
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Element is annotated as BEP but does not return " + BEPReturnTypeCanonicalName + " or any subtype.", element);
                }
            }
        }

        return false;
    }
}
