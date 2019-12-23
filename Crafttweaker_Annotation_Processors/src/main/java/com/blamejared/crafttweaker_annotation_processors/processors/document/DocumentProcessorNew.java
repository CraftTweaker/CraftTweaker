package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotations.annotations.Document;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.Document", "net.minecraftforge.fml.common.Mod"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DocumentProcessorNew extends AbstractProcessor {
    public static Map<String, String> modIdByPackage = new HashMap<>();

    public static String getModIdForPackage(Element element, ProcessingEnvironment environment) {
        final String packageName = environment.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        for (String knownPackName : modIdByPackage.keySet()) {
            if (packageName.startsWith(knownPackName)) {
                return modIdByPackage.get(knownPackName);
            }
        }
        return null;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        {
            final TypeElement typeElement = processingEnv.getElementUtils()
                    .getTypeElement("net.minecraftforge.fml.common.Mod");
            outer:
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                    if (annotationMirror.getAnnotationType().asElement().equals(typeElement)) {
                        final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror
                                .getElementValues();

                        for (ExecutableElement executableElement : elementValues.keySet()) {
                            if (executableElement.getSimpleName().toString().equals("value")) {
                                final String packageName = processingEnv.getElementUtils()
                                        .getPackageOf(element)
                                        .getQualifiedName()
                                        .toString();
                                modIdByPackage.put(packageName, elementValues.get(executableElement)
                                        .getValue()
                                        .toString());
                                continue outer;
                            }
                        }
                    }
                }
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not find mod-id for this element!", element);
            }
        }


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
                    if (!docsOut.exists() && !docsOut.mkdirs()) {
                        processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.ERROR, "Could not create folder " + docsOut.getAbsolutePath(), element);
                    }
                    documentationPage.write(docsOut, processingEnv);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return false;
    }
}
