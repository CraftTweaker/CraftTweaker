package com.blamejared.crafttweaker_annotation_processors.processors.document.shared;

import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import javax.lang.model.element.TypeElement;

public class IDontKnowHowToNameThisUtil {
    private IDontKnowHowToNameThisUtil() {
    }


    public static String getDocPath(TypeElement element) {
        final Document document = element.getAnnotation(Document.class);
        final ZenCodeType.Name nameAnnotation = element.getAnnotation(ZenCodeType.Name.class);
        final ZenCodeType.Expansion expansionAnnotation = element.getAnnotation(ZenCodeType.Expansion.class);


        if (document != null && !document.value().isEmpty()) {
            return document.value();
        }

        if (nameAnnotation != null) {
            return nameAnnotation.value().replace('.', '/');
        }

        if (expansionAnnotation != null) {
            //Expand("x.y.Z") => "x/y/ExpandZ" file
            final String value = expansionAnnotation.value();
            final int lastIndex = value.lastIndexOf('.');
            final String substring = value.substring(lastIndex);
            return (value.substring(0, lastIndex) + "Expand" + substring).replace('.', '/');
        }

        return null;
    }

}
