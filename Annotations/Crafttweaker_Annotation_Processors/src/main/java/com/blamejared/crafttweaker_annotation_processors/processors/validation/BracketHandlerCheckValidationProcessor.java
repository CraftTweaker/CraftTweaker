package com.blamejared.crafttweaker_annotation_processors.processors.validation;

import com.blamejared.crafttweaker_annotation_processors.processors.util.annotations.AnnotationMirrorUtil;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker.api.annotations.BracketResolver"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BracketHandlerCheckValidationProcessor extends AbstractProcessor {
    
    private final AnnotationMirrorUtil annotationMirrorUtil = new AnnotationMirrorUtil();
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        
        for(TypeElement annotation : annotations) {
            for(Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                
                final AnnotationMirror annotationMirror = annotationMirrorUtil.getMirror(element, annotation);
                
                if(annotationMirror == null) {
                    processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "\nInternal Error: Element annotated with @BracketHandler does not have an annotationMirror for it", element);
                    continue;
                }
                
                if(!(element instanceof ExecutableElement)) {
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
                if(parameters.size() != 1 || !this.processingEnv.getTypeUtils()
                        .isSameType(parameters.get(0).asType(), this.processingEnv.getElementUtils()
                                .getTypeElement(String.class.getCanonicalName())
                                .asType())) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "\nElement is annotated as BEP but does not accept a String as its only parameter.", element, annotationMirror);
                }
            }
        }
        
        return false;
    }
    
}
