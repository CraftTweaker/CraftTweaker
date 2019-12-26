package com.blamejared.crafttweaker_annotation_processors.processors.validation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker.api.annotations.Preprocessor"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PreprocessorAnnotationValidationProcessor extends AbstractProcessor {
    private static final String preprocessorInterfaceCanonicalName = "com.blamejared.crafttweaker.api.zencode.IPreprocessor";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (!(element instanceof TypeElement)) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "How is this annotated?", element);
                    continue;
                }

                if (!this.processingEnv.getTypeUtils()
                        .isAssignable(element.asType(), this.processingEnv.getElementUtils()
                                .getTypeElement(preprocessorInterfaceCanonicalName)
                                .asType())) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "Element is annotated as Preprocessor but is not assignable to " + preprocessorInterfaceCanonicalName + "!", element);
                }
            }
        }

        return false;
    }
}
