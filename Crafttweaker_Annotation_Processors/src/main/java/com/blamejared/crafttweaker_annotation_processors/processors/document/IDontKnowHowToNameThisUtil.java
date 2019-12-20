package com.blamejared.crafttweaker_annotation_processors.processors.document;

import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.TypeElement;

public class IDontKnowHowToNameThisUtil {
    private IDontKnowHowToNameThisUtil() {
    }


    public static String getDocPath(TypeElement element, ZenCodeType.Name nameAnnotation) {
        final Document document = element.getAnnotation(Document.class);

        if (document == null) {
            if (nameAnnotation == null) {
                //Found a super interface that has neither annotation, so probably unrelated.
                return null;
            }
        } else if (!document.value().isEmpty()) {
            return document.value();
        }

        return nameAnnotation.value().replace('.', '/');
    }
}
