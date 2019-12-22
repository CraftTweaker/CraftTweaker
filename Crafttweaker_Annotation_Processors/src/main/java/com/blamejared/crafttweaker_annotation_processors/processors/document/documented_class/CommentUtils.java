package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import com.blamejared.crafttweaker_annotation_processors.processors.StringReplaceUtil;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.types.DocumentedType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentUtils {
    private CommentUtils() {
    }

    /**
     * Puts all document annotations starting with the given string into one line.
     *
     * @param parentDoc   The documentation string to search in
     * @param annotation  The Start of the annotation, e.g. {@code @docParam this}
     * @param environment ProcessingEnvironment, used for type lookups and warning reports.
     * @return String with all found docAnnotations in one line, newlines replaced with single spaces.
     */
    public static String joinDocAnnotation(String parentDoc, String annotation, ProcessingEnvironment environment) {
        if (parentDoc == null)
            return "";
        final StringBuilder sb = new StringBuilder();
        final Matcher matcher = Pattern.compile(annotation + " (?<content>(?:\\{@link ([\\w. ]*)}|[^@{]*)*)")
                .matcher(parentDoc);
        while (matcher.find()) {
            final String content = matcher.group("content");
            final String s = StringReplaceUtil.replaceWithMatcher(content, "\\{@link (?<type>[\\w. ]*)}", matcher1 -> {
                final String type = matcher1.group("type");
                final DocumentedType typeElement = DocumentedType.fromElement(environment.getElementUtils()
                        .getTypeElement(type), environment);
                if (typeElement == null) {
                    environment.getMessager()
                            .printMessage(Diagnostic.Kind.WARNING, "Could not resolve link {@link " + type + "}");
                    return type;
                }
                return typeElement.getClickableMarkdown();
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
        if (parentDoc == null) {
            return new String[0];
        }

        final List<String> out = new ArrayList<>();
        final Matcher matcher = Pattern.compile(annotation + " (?<content>[^@]*)").matcher(parentDoc);
        while (matcher.find()) {
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
        if (parentDoc == null)
            return null;

        //Replace links to types with their MD equivalents
        parentDoc = StringReplaceUtil.replaceWithMatcher(parentDoc, "\\{@link (?<type>[^}]*)}", matcher -> {
            String out = matcher.group("type").trim().split("#")[0];
            if (out.contains(".")) {
                final TypeElement typeElement = environment.getElementUtils().getTypeElement(out);
                final DocumentedType type = DocumentedType.fromElement(typeElement, environment);
                if (type != null) {
                    return type.getClickableMarkdown();
                }
            }
            final Name qualifiedName = environment.getElementUtils().getPackageOf(element).getQualifiedName();
            final TypeElement typeElement = environment.getElementUtils().getTypeElement(qualifiedName + "." + out);
            final DocumentedType type = DocumentedType.fromElement(typeElement, environment);
            if (type != null) {
                return type.getClickableMarkdown();
            }

            environment.getMessager()
                    .printMessage(Diagnostic.Kind.WARNING, "Could not resolve javadoc {@link " + out + "}!", element);
            return out;
        });

        //Replace returns call
        parentDoc = StringReplaceUtil.replaceWithMatcher(parentDoc, "@return (?<content>[^@]*)", m -> "Returns: `" + m.group("content")
                .trim() + "`\n");

        //Remove docParam and param
        parentDoc = StringReplaceUtil.replaceAll(parentDoc, "@(docP|p)aram [^@]*", s -> "");


        return parentDoc.trim();
    }
}
