package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types;

import com.blamejared.crafttweaker_annotation_processors.processors.document.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.tools.*;
import java.util.*;

/**
 * Any type that can be referenced.
 */
public abstract class DocumentedType {
    
    
    public static final Comparator<? super DocumentedType> byZSName = Comparator.comparing(DocumentedType::getZSName);
    
    public static DocumentedType fromElement(Element element, ProcessingEnvironment environment) {
        if(element instanceof TypeElement) {
            final DocumentedClass documentedClass = DocumentedClass.convertClass((TypeElement) element, environment);
            if(documentedClass != null)
                return new DocumentedClassType(documentedClass);
        }
        if(element == null) {
            return null;
        }
        return fromTypeMirror(element.asType(), environment);
    }
    
    public static DocumentedType fromTypeMirror(TypeMirror typeMirror, ProcessingEnvironment environment) {
        if(typeMirror.getKind().isPrimitive()) {
            return new DocumentedPrimitiveType(typeMirror);
        }
        
        if(typeMirror.getKind() == TypeKind.ARRAY) {
            return new DocumentedArrayType(fromTypeMirror(((ArrayType) typeMirror).getComponentType(), environment));
        }
        
        
        if(typeMirror instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if(!typeArguments.isEmpty()) {
                final TypeMirror erasure = environment.getTypeUtils().erasure(typeMirror);
                
                if(erasure.toString().equals("java.util.Map")) {
                    return new DocumentedMapType(fromTypeMirror(typeArguments.get(0), environment), fromTypeMirror(typeArguments
                            .get(1), environment));
                }
                
                final DocumentedType base = fromTypeMirror(erasure, environment);
                
                final List<DocumentedType> arguments = new ArrayList<>();
                for(TypeMirror typeArgument : typeArguments) {
                    arguments.add(fromTypeMirror(typeArgument, environment));
                }
                return new DocumentedGenericType(base, arguments);
            } else {
                final DocumentedClass documentedClass = DocumentedClass.convertClass(typeMirror, environment);
                if(documentedClass != null) {
                    return new DocumentedClassType(documentedClass);
                }
                
                if(typeMirror.toString().startsWith("java.util.") || typeMirror.toString()
                        .startsWith("java.lang.")) {
                    //java.util. and java.lang. both are 10 chars, otherwise I'd put them in different calls
                    return new DocumentedNativeType(typeMirror.toString().substring(10));
                }
            }
        }
        
        
        boolean showWarning = true;
        final Element element = environment.getTypeUtils().asElement(typeMirror);
        
        if(typeMirror instanceof TypeVariable) {
            showWarning = false;
        } else if(DocumentProcessorNew.tree != null) {
            //getTree( returns null if it's not part of the compilation tree, e.g. part of a lib
            showWarning = DocumentProcessorNew.tree.getTree(element) != null;
        }
        
        
        if(showWarning) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.WARNING, "Found a type without a Name Annotation: " + element, element);
        }
        return new DocumentedNativeType(typeMirror.toString());
    }
    
    
    public abstract String getZSName();
    
    public abstract String getClickableMarkdown();
    
    public String getClickableMarkdown(String member) {
        return getClickableMarkdown();
    }
    
    @Override
    public String toString() {
        return getZSName();
    }
    
    public abstract String getZSShortName();
    
    public abstract String getDocParamThis();
}
