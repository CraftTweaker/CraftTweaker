package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types;

import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.DocumentedClass;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Any type that can be referenced.
 */
public abstract class DocumentedType {


    public static final Comparator<? super DocumentedType> byZSName = Comparator.comparing(DocumentedType::getZSName);

    public static DocumentedType fromElement(Element element, ProcessingEnvironment environment) {
        if (element instanceof TypeElement) {
            final DocumentedClass documentedClass = DocumentedClass.convertClass((TypeElement) element, environment);
            if (documentedClass != null)
                return new DocumentedClassType(documentedClass);
        }
        if(element == null) {
            return null;
        }
        return fromTypeMirror(element.asType(), environment);
    }

    public static DocumentedType fromTypeMirror(TypeMirror typeMirror, ProcessingEnvironment environment) {
        if (typeMirror.getKind().isPrimitive()) {
            return new DocumentedNativeType(typeMirror.toString());
        }

        if (typeMirror.getKind() == TypeKind.ARRAY) {
            return new DocumentedArrayType(fromTypeMirror(((ArrayType) typeMirror).getComponentType(), environment));
        }


        if (typeMirror instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if (!typeArguments.isEmpty()) {
                final TypeMirror erasure = environment.getTypeUtils().erasure(typeMirror);

                if(erasure.toString().equals("java.util.Map")) {
                    return new DocumentedMapType(fromTypeMirror(typeArguments.get(0), environment), fromTypeMirror(typeArguments.get(1), environment));
                }

                final DocumentedType base = fromTypeMirror(erasure, environment);

                final List<DocumentedType> arguments = new ArrayList<>();
                for (TypeMirror typeArgument : typeArguments) {
                    arguments.add(fromTypeMirror(typeArgument, environment));
                }
                return new DocumentedGenericType(base, arguments);
            } else {
                final DocumentedClass documentedClass = DocumentedClass.convertClass(typeMirror, environment);
                if (documentedClass != null) {
                    return new DocumentedClassType(documentedClass);
                }

                if (typeMirror.toString().startsWith("java.util.")
                        || typeMirror.toString().startsWith("java.lang.")) {
                    //java.util. and java.lang. both are 10 chars, otherwise I'd put them in different calls
                    return new DocumentedNativeType(typeMirror.toString().substring(10));
                }
            }
        }


        environment.getMessager().printMessage(Diagnostic.Kind.WARNING, "Found a type without a Name Annotation: ", environment.getTypeUtils().asElement(typeMirror));
        return new DocumentedNativeType(typeMirror.toString());
    }


    public abstract String getZSName();

    public abstract String getClickableMarkdown();

    @Override
    public String toString() {
        return getZSName();
    }
}
