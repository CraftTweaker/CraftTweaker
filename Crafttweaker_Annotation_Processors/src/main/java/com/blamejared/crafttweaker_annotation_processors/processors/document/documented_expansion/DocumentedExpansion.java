package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_expansion;

import com.blamejared.crafttweaker_annotation_processors.processors.document.CrafttweakerDocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.IDontKnowHowToNameThisUtil;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.DocumentedType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DocumentedExpansion extends CrafttweakerDocumentationPage {
    private final String docPath;
    private final DocumentedType expandedType;
    private final Set<DocumentedOperator> operators = new TreeSet<>(DocumentedOperator.compareByOp);
    private String docParamThis;
    private Set<DocumentedCaster> casters = new TreeSet<>(DocumentedCaster.byZSName);
    private Map<String, DocumentedMethodGroup> methods = new TreeMap<>();
    private Map<String, DocumentedProperty> properties = new TreeMap<>();

    public DocumentedExpansion(String docPath, DocumentedType expandedType) {
        this.docPath = docPath;
        this.expandedType = expandedType;
    }

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

        final ZenCodeType.Expansion expansionAnnotation = element.getAnnotation(ZenCodeType.Expansion.class);
        if (expansionAnnotation == null) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Expected this to have an Expansion annotation", element);
            return null;
        }

        final DocumentedType expandedType = findPage(element, expansionAnnotation, environment);
        if (expandedType == null) {
            environment.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Internal error: Could not find expanded type!");
            return null;
        }

        final String docPath = IDontKnowHowToNameThisUtil.getDocPath(element);
        if (docPath == null) {
            return null;
        }
        final DocumentedExpansion out = new DocumentedExpansion(docPath, expandedType);
        handleEnclosedElements(out, element, environment);

        return out;
    }

    private static DocumentedType findPage(TypeElement element, ZenCodeType.Expansion expansionAnnotation, ProcessingEnvironment environment) {
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

    @Override
    public String getDocPath() {
        return docPath;
    }

    @Override
    public void write(File docsDirectory, ProcessingEnvironment environment) throws IOException {

    }

    @Override
    public String getZSName() {
        return expandedType.getZSName();
    }

    @Override
    public String getDocParamThis() {
        return docParamThis == null ? "my" + expandedType.getZSShortName() : docParamThis;
    }
}
