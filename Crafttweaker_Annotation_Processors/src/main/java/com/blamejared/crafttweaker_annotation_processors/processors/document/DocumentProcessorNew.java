package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotations.annotations.Document;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessorNew extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(Document.class)) {
            final Document document = element.getAnnotation(Document.class);
            if (document == null) {
                this.processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Internal error! Document annotation null", element);
                continue;
            }

            if (!element.getKind().isClass() && !element.getKind().isInterface()) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "How is this annotated", element);
                continue;
            }


            final TypeElement typeElement = (TypeElement) element;
            final CrafttweakerDocumentationPage documentationPage = CrafttweakerDocumentationPage.convertType(typeElement, this.processingEnv);

            if (documentationPage != null) {
                try {
                    final File docsOut = new File("docsOut");
                    if(!docsOut.exists() && !docsOut.mkdirs()) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Could not create folder " + docsOut.getAbsolutePath(), element);
                    }
                    documentationPage.write(docsOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return false;
    }
}
