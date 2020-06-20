package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util;

import com.blamejared.crafttweaker_annotation_processors.processors.document.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class CommentUtils {
    
    private CommentUtils() {
    }
    
    /**
     * Puts all document annotations starting with the given string into one line.
     *
     * @param element     The element in whose documentation string to search in
     * @param annotation  The Start of the annotation, e.g. {@code @docParam this}
     * @param environment ProcessingEnvironment, used for type lookups and warning reports.
     * @return String with all found docAnnotations in one line, newlines replaced with single spaces.
     */
    public static String joinDocAnnotation(Element element, String annotation, ProcessingEnvironment environment) {
        final String parentDoc = environment.getElementUtils().getDocComment(element);
        
        if(parentDoc == null)
            return "";
        final StringBuilder sb = new StringBuilder();
        final Matcher matcher = Pattern.compile(annotation + " (?<content>(?:\\{@link ([\\w. ]*)}|[^@{]*)*)")
                .matcher(parentDoc);
        while(matcher.find()) {
            final String content = matcher.group("content");
            final String s = StringReplaceUtil.replaceWithMatcher(content, "\\{@link (?<type>[\\w. ]*)}", matcher1 -> {
                final String type = matcher1.group("type");
                return getMarkDownFor(type, environment, element);
            });
            sb.append(s);
        }
        
        return sb.toString().replaceAll("[\\n\\r]+", " ").trim();
    }
    
    /**
     * Puts all document annotations starting with the given string into one line.
     *
     * @param parentDoc  The documentation string to search in
     * @param annotation The Start of the annotation, e.g. {@code @docParam this}
     * @return String with all found docAnnotations in one line, newlines replaced with single spaces.
     */
    public static String[] findAllAnnotation(String parentDoc, String annotation) {
        if(parentDoc == null) {
            return new String[0];
        }
        
        final List<String> out = new ArrayList<>();
        final Matcher matcher = Pattern.compile(annotation + " (?<content>[^@]*)")
                .matcher(parentDoc);
        while(matcher.find()) {
            out.add(matcher.group("content").replaceAll("[\\n\\r]+", " ").trim());
        }
        
        return out.toArray(new String[0]);
    }
    
    /**
     * Changes a JavaDoc comment to match the md format
     *
     * @return The formatted Javadoc comment, or null
     */
    public static String formatDocCommentForDisplay(Element element, ProcessingEnvironment environment) {
        String parentDoc = environment.getElementUtils().getDocComment(element);
        if(parentDoc == null)
            return null;
        
        //Replace links to types with their MD equivalents
        parentDoc = StringReplaceUtil.replaceWithMatcher(parentDoc, "\\{@link (?<type>[^}]*)}", matcher -> getMarkDownFor(matcher
                .group("type")
                .trim(), environment, element));
        
        //Replace returns call
        //Remove docParam and param
        parentDoc = StringReplaceUtil.replaceWithMatcher(parentDoc, "@(?<type>return|docParam|param) (?<content>[^@]*)", m -> m
                .group("type")
                .equals("return") ? "Returns: `" + m.group("content").trim() + "`\n" : "");
        
        
        return parentDoc.trim();
    }
    
    private static DocumentedType getDocumentedType(String type, ProcessingEnvironment environment, Element documentedElement) {
        if(type.contains("#")) {
            if(documentedElement.getEnclosingElement().getKind() == ElementKind.PACKAGE) {
                return getDocumentedType(documentedElement.asType().toString(), environment, documentedElement);
            } else {
                return getDocumentedType(type.split("#", 2)[0], environment, documentedElement);
            }
        }
        
        if(type.isEmpty()) {
            return DocumentedType.fromElement(documentedElement.getEnclosingElement(), environment);
        }
        
        {
            TypeElement typeElement = environment.getElementUtils().getTypeElement(type);
            if(typeElement != null) {
                final DocumentedType documentedType = DocumentedType.fromElement(typeElement, environment);
                if(documentedType != null) {
                    return documentedType;
                }
            }
        }
        
        final PackageElement packageOf = environment.getElementUtils()
                .getPackageOf(documentedElement);
        for(Element enclosedElement : packageOf.getEnclosedElements()) {
            if(enclosedElement.getSimpleName().contentEquals(type)) {
                final DocumentedType documentedType = DocumentedType.fromElement(enclosedElement, environment);
                if(documentedType != null) {
                    return documentedType;
                }
            }
        }
        
        if(DocumentProcessorNew.tree != null) {
            final TreePath path = DocumentProcessorNew.tree.getPath(documentedElement);
            if(path != null) {
                final Optional<DocumentedType> any = path.getCompilationUnit()
                        .getImports()
                        .stream()
                        .map(ImportTree::getQualifiedIdentifier)
                        .map(Object::toString)
                        .sorted(Comparator.comparing(s -> s.endsWith("*"))) //Wildcard imports last
                        .flatMap(s -> !s.endsWith("*") ? Stream.of(s) : environment.getElementUtils()
                                .getPackageElement(s.substring(0, s.lastIndexOf('.')))
                                .getEnclosedElements()
                                .stream()
                                .map(Objects::toString))
                        .map(environment.getElementUtils()::getTypeElement)
                        .filter(Objects::nonNull) //not sure just in case
                        .filter(e -> e.getSimpleName().contentEquals(type))
                        .map(e -> DocumentedType.fromElement(e, environment))
                        .filter(Objects::nonNull)
                        .findAny();
                if(any.isPresent()) {
                    return any.get();
                }
            }
        }
        
        environment.getMessager()
                .printMessage(Diagnostic.Kind.WARNING, "Could not resolve link to " + type, documentedElement);
        return null;
    }
    
    public static String getMarkDownFor(String type, ProcessingEnvironment environment, Element documentedElement) {
        final DocumentedType documentedType = getDocumentedType(type, environment, documentedElement);
        if(documentedType != null) {
            if(type.contains("#")) {
                return documentedType.getClickableMarkdown(type.split("#", 2)[1]);
            } else {
                return documentedType.getClickableMarkdown();
            }
        }
        
        return "";
    }
}
