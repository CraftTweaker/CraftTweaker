package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedExpansion;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class CrafttweakerDocumentationPage {
    public static Map<TypeElement, CrafttweakerDocumentationPage> knownTypes = new HashMap<>();

    public static CrafttweakerDocumentationPage convertType(TypeElement element, ProcessingEnvironment environment) {
        if(knownTypes.containsKey(element)) {
            return knownTypes.get(element);
        }

        final CrafttweakerDocumentationPage documentationPage;
        if (element.getAnnotation(ZenCodeType.Name.class) != null) {
            documentationPage = DocumentedClass.convertClass(element, environment);
        } else if (element.getAnnotation(ZenCodeType.Expansion.class) != null) {
            documentationPage = DocumentedExpansion.convertExpansion(element, environment);
        } else {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "@Documented requires either @Expansion or @Name to be set as well!", element);
            return null;
        }

        knownTypes.put(element, documentationPage);
        return documentationPage;
    }

    public abstract void write(File docsDirectory) throws IOException;
}
