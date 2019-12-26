package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_expansion;

import com.blamejared.crafttweaker_annotation_processors.processors.document.CrafttweakerDocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.DocumentProcessorNew;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.Writable;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.DocumentedType;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.CommentUtils;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.IDontKnowHowToNameThisUtil;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DocumentedExpansion extends CrafttweakerDocumentationPage {
    private final String docPath;
    private final String docComment;
    private final DocumentedType expandedType;
    private final Set<DocumentedOperator> operators = new TreeSet<>(DocumentedOperator.compareByOp);
    private final Set<DocumentedCaster> casters = new TreeSet<>(DocumentedCaster.byZSName);
    private final Map<String, DocumentedMethodGroup> methods = new TreeMap<>();
    private final Map<String, DocumentedProperty> properties = new TreeMap<>();
    private final String docParamThis;
    private final String declaringModId;

    public DocumentedExpansion(String docPath, String docComment, DocumentedType expandedType, String docParamThis, String declaringModId) {
        this.docPath = docPath;
        this.docComment = docComment;
        this.expandedType = expandedType;
        this.docParamThis = docParamThis;
        this.declaringModId = declaringModId;
    }

    public static DocumentedExpansion convertExpansion(TypeElement element, ProcessingEnvironment environment) {
        if (knownTypes.containsKey(element.toString())) {
            final CrafttweakerDocumentationPage documentationPage = knownTypes.get(element.toString());
            if (!(documentationPage instanceof DocumentedExpansion)) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Internal error: " + element + " is not a class", element);
                return null;
            }
            return (DocumentedExpansion) documentationPage;
        }

        final ZenCodeType.Expansion expansionAnnotation = element.getAnnotation(ZenCodeType.Expansion.class);
        if (expansionAnnotation == null) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to have an Expansion annotation", element);
            return null;
        }

        final DocumentedType expandedType = findExpandedType(element, expansionAnnotation, environment);
        if (expandedType == null) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not find expanded type!");
            return null;
        }

        final String docPath = IDontKnowHowToNameThisUtil.getDocPath(element);
        if (docPath == null) {
            return null;
        }
        final String s = CommentUtils.formatDocCommentForDisplay(element, environment);
        final String docComment = environment.getElementUtils().getDocComment(element);
        String docParamThis = CommentUtils.joinDocAnnotation(docComment, "docParam this", environment);

        if (docParamThis.isEmpty()) {
            docParamThis = null;
        }
        final String declaringModId = DocumentProcessorNew.getModIdForPackage(element, environment);
        final DocumentedExpansion out = new DocumentedExpansion(docPath, (s == null || s.isEmpty()) ? null : s, expandedType, docParamThis, declaringModId);
        knownTypes.put(element.toString(), out);


        handleEnclosedElements(out, element, environment);

        return out;
    }

    private static DocumentedType findExpandedType(TypeElement element, ZenCodeType.Expansion expansionAnnotation, ProcessingEnvironment environment) {
        if (typesByZSName.containsKey(expansionAnnotation.value())) {
            return DocumentedType.fromElement(typesByZSName.get(expansionAnnotation.value()), environment);
        }

        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.METHOD && enclosedElement.getAnnotation(ZenCodeType.Method.class) != null) {
                final ExecutableElement executableElement = (ExecutableElement) enclosedElement;
                if (executableElement.getParameters().size() == 0) {
                    continue;
                }

                final VariableElement variableElement = executableElement.getParameters().get(0);
                final DocumentedType type = DocumentedType.fromElement(variableElement, environment);
                if (type != null) {
                    if (!type.getZSName().equals(expansionAnnotation.value())) {
                        environment.getMessager()
                                .printMessage(Diagnostic.Kind.ERROR, "First Parameter of this method is not the expanded element, " + expansionAnnotation
                                        .value() + " but " + type.getZSName(), enclosedElement);

                        //continue; Should we still use the first method?
                    }
                    return type;
                }
            }
        }
        return null;
    }

    private static void handleEnclosedElements(DocumentedExpansion out, TypeElement element, ProcessingEnvironment environment) {
        for (Element enclosedElement : element.getEnclosedElements()) {
            //Method
            {
                final ZenCodeType.Method method = enclosedElement.getAnnotation(ZenCodeType.Method.class);
                if (method != null) {
                    final DocumentedMethod documentedMethod = DocumentedMethod.convertMethod(out, (ExecutableElement) enclosedElement, environment, true);
                    if (documentedMethod != null) {
                        out.methods.computeIfAbsent(documentedMethod.getName(), name -> new DocumentedMethodGroup(name, out))
                                .add(documentedMethod);
                    }
                }
            }
            //Getter
            //Setter
            {
                if (enclosedElement.getAnnotation(ZenCodeType.Getter.class) != null || enclosedElement.getAnnotation(ZenCodeType.Setter.class) != null) {
                    final DocumentedProperty documentedProperty = DocumentedProperty.fromMethod(out, enclosedElement, environment, true);
                    if (documentedProperty != null) {
                        out.properties.merge(documentedProperty.getName(), documentedProperty, ((p1, p2) -> DocumentedProperty
                                .merge(p1, p2, environment)));
                    }
                }
            }
            //Operator
            {
                if (enclosedElement.getAnnotation(ZenCodeType.Operator.class) != null) {
                    final DocumentedOperator documentedOperator = DocumentedOperator.fromMethod(out, (ExecutableElement) enclosedElement, environment, true);
                    if (documentedOperator != null) {
                        out.operators.add(documentedOperator);
                    }
                }
            }

            //Caster
            {
                if (enclosedElement.getAnnotation(ZenCodeType.Caster.class) != null) {
                    final DocumentedCaster documentedCaster = DocumentedCaster.fromMethod((ExecutableElement) enclosedElement, environment, true);
                    if (documentedCaster != null) {
                        out.casters.add(documentedCaster);
                    }
                }
            }
        }
    }

    private static void printSection(String sectionName, Collection<? extends Writable> collectionWritable, PrintWriter writer) {
        if (!collectionWritable.isEmpty()) {
            writer.printf("## %s%n", sectionName);
            for (Writable writable : collectionWritable) {
                writable.write(writer);
            }
            writer.println();
        }
    }

    @Override
    public String getDocPath() {
        return docPath;
    }

    @Override
    public void write(File docsDirectory, ProcessingEnvironment environment) throws IOException {
        final File file = new File(docsDirectory, getDocPath() + ".md");
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("Could not create folder " + file.getAbsolutePath());
        }

        try (final PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.printf("# %s%n", this.getDocumentTitle());
            writer.println();
            if (docComment != null) {
                writer.println(docComment);
                writer.println();
            }

            if (declaringModId != null) {
                writer.printf("This expansion was added by a mod with mod-id `%s`. So you need to have this mod installed if you want to use this feature.%n", declaringModId);
                writer.println();
            }

            writer.printf("This is an expansion class for %s.  %n", this.expandedType.getClickableMarkdown());
            writer.printf("That means that whenever you have an object that is a %s, you can use these methods on it as if they were directly declared in %1$s.%n", this.expandedType
                    .getClickableMarkdown());
            writer.println("Of course, that only works if the mod that declares this expansion class is also loaded!");
            writer.println();

            printSection("Methods", this.methods.values(), writer);
            DocumentedProperty.printProperties(this.properties.values(), writer);
            printSection("Operators", this.operators, writer);
            DocumentedCaster.printCasters(this.casters, writer);
        }
    }

    @Override
    public String getZSName() {
        return expandedType.getZSName();
    }

    @Override
    public String getDocParamThis() {
        return docParamThis == null ? "my" + expandedType.getZSShortName() : docParamThis;
    }

    @Override
    public String getDocumentTitle() {
        return "Expansion for " + expandedType.getZSShortName();
    }
}
