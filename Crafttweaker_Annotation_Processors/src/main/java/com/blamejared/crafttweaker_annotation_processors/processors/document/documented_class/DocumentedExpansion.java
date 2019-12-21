package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import com.blamejared.crafttweaker_annotation_processors.processors.document.CrafttweakerDocumentationPage;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;

public class DocumentedExpansion extends CrafttweakerDocumentationPage {
    public static DocumentedExpansion convertExpansion(TypeElement element, ProcessingEnvironment environment) {
        if (knownTypes.containsKey(element)) {
            final CrafttweakerDocumentationPage documentationPage = knownTypes.get(element);
            if (!(documentationPage instanceof DocumentedExpansion)) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Internal error: " + element + " is not a class", element);
                return null;
            }
            return (DocumentedExpansion) documentationPage;
        }
        return null;
    }

    @Override
    public void write(File docsDirectory) throws IOException {

    }
}
