package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_expansion.DocumentedExpansion;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Anything that can be written to file as a MD page.
 * Can be a {@link DocumentedClass} or a {@link DocumentedExpansion}
 */
public abstract class CrafttweakerDocumentationPage {
    public static Map<String, CrafttweakerDocumentationPage> knownTypes = new HashMap<>();
    public static Map<String, TypeElement> typesByZSName = new HashMap<>();

    public static CrafttweakerDocumentationPage convertType(TypeElement element, ProcessingEnvironment environment) {
        if (knownTypes.containsKey(element.toString())) {
            return knownTypes.get(element.toString());
        }

        final CrafttweakerDocumentationPage documentationPage;
        if (element.getAnnotation(ZenCodeType.Name.class) != null) {
            documentationPage = DocumentedClass.convertClass(element, environment, false);
        } else if (element.getAnnotation(ZenCodeType.Expansion.class) != null) {
            documentationPage = DocumentedExpansion.convertExpansion(element, environment, false);
        } else {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "@Documented requires either @Expansion or @Name to be set as well!", element);
            return null;
        }

        if (!knownTypes.containsKey(element.toString())) {
            knownTypes.put(element.toString(), documentationPage);
        }

        return documentationPage;
    }

    public abstract String getDocPath();

    public abstract void write(File docsDirectory, ProcessingEnvironment environment) throws IOException;

    public abstract String getZSName();

    public abstract String getDocParamThis();

    public abstract String getDocumentTitle();
}
