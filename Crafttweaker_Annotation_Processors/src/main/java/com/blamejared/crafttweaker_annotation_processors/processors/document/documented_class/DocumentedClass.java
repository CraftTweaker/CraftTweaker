package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import com.blamejared.crafttweaker_annotation_processors.processors.document.CrafttweakerDocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.DocumentedScriptingExample;
import com.blamejared.crafttweaker_annotation_processors.processors.document.IDontKnowHowToNameThisUtil;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members.*;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.*;
import java.util.*;

/**
 * The main part of this.
 * Most pages in the wiki will be pages that explain a certain ZS class.
 */
public class DocumentedClass extends CrafttweakerDocumentationPage {

    public static final Comparator<DocumentedClass> byZSName = Comparator.comparing(c -> c.ZSName);

    private final Set<DocumentedClass> implementedInterfaces = new TreeSet<>(DocumentedClass.byZSName);
    private final Set<DocumentedConstructor> constructors = new TreeSet<>(DocumentedConstructor.compareByParameterCount);
    private final Set<DocumentedCaster> casters = new TreeSet<>(DocumentedCaster.byZSName);
    private final Map<String, DocumentedMethodGroup> methods = new TreeMap<>();
    private final Map<String, DocumentedProperty> properties = new TreeMap<>();
    private final Set<DocumentedOperator> operators = new TreeSet<>(DocumentedOperator.compareByOp);
    private final Set<DocumentedScriptingExample> examples = new HashSet<>();
    private final String ZSName;
    private final String docPath;
    private final DocumentedClass superClass;

    public DocumentedClass(String ZSName, String docPath, DocumentedClass superClass) {
        this.ZSName = ZSName;
        this.docPath = docPath;
        this.superClass = superClass;
    }

    public static DocumentedClass convertClass(TypeMirror type, ProcessingEnvironment environment) {
        return convertClass((TypeElement) environment.getTypeUtils().asElement(type), environment);
    }

    public static DocumentedClass convertClass(TypeElement element, ProcessingEnvironment environment) {
        if (element == null || element.equals(environment.getElementUtils().getTypeElement("java.lang.Object"))) {
            return null;
        }
        if (knownTypes.containsKey(element)) {
            final CrafttweakerDocumentationPage documentationPage = knownTypes.get(element);
            if (!(documentationPage instanceof DocumentedClass)) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Internal error: " + element + " is not a class", element);
                return null;
            }
            return (DocumentedClass) documentationPage;
        }

        final ZenCodeType.Name nameAnnotation = element.getAnnotation(ZenCodeType.Name.class);
        final String zsName = nameAnnotation != null ? nameAnnotation.value() : element.getQualifiedName().toString();
        final String docPath = IDontKnowHowToNameThisUtil.getDocPath(element, nameAnnotation);
        if (docPath == null) {
            return null;
        }

        final DocumentedClass superClass = convertClass(element.getSuperclass(), environment);
        final DocumentedClass out = new DocumentedClass(zsName, docPath, superClass);
        knownTypes.put(element, out);

        for (final TypeMirror anInterface : element.getInterfaces()) {
            final DocumentedClass documentedClass = convertClass(anInterface, environment);
            if (documentedClass != null) {
                out.implementedInterfaces.add(documentedClass);
            }
        }

        handleEnclosedElements(out, element, environment);

        return out;
    }

    private static void handleEnclosedElements(DocumentedClass out, TypeElement element, ProcessingEnvironment environment) {
        for (Element enclosedElement : element.getEnclosedElements()) {
            //Method
            {
                final ZenCodeType.Method method = enclosedElement.getAnnotation(ZenCodeType.Method.class);
                if (method != null) {
                    final DocumentedMethod documentedMethod = DocumentedMethod.convertMethod((ExecutableElement) enclosedElement, environment);
                    if (documentedMethod != null) {
                        out.methods.computeIfAbsent(documentedMethod.getName(), DocumentedMethodGroup::new).add(documentedMethod);
                    }
                }
            }
            //Field
            {
                final ZenCodeType.Field method = enclosedElement.getAnnotation(ZenCodeType.Field.class);
                if (method != null) {
                    final DocumentedProperty documentedProperty = DocumentedProperty.fromField(enclosedElement, environment);
                    if (documentedProperty != null) {
                        out.properties.merge(documentedProperty.getName(), documentedProperty, ((p1, p2) -> DocumentedProperty
                                .merge(p1, p2, environment)));
                    }
                }
            }
            //Getter
            //Setter
            {
                if (enclosedElement.getAnnotation(ZenCodeType.Getter.class) != null || enclosedElement.getAnnotation(ZenCodeType.Setter.class) != null) {
                    final DocumentedProperty documentedProperty = DocumentedProperty.fromMethod(enclosedElement, environment);
                    if (documentedProperty != null) {
                        out.properties.merge(documentedProperty.getName(), documentedProperty, ((p1, p2) -> DocumentedProperty
                                .merge(p1, p2, environment)));
                    }
                }
            }
            //Constructor
            {
                if (enclosedElement.getAnnotation(ZenCodeType.Constructor.class) != null) {
                    final DocumentedConstructor documentedConstructor = DocumentedConstructor.fromConstructor(((ExecutableElement) enclosedElement), environment);
                    if (documentedConstructor != null) {
                        out.constructors.add(documentedConstructor);
                    }
                }
            }
            //Operator
            {
                if (enclosedElement.getAnnotation(ZenCodeType.Operator.class) != null) {
                    final DocumentedOperator documentedOperator = DocumentedOperator.fromMethod((ExecutableElement) enclosedElement, environment);
                    if (documentedOperator != null) {
                        out.operators.add(documentedOperator);
                    }
                }
            }

            //Caster
            {
                if (enclosedElement.getAnnotation(ZenCodeType.Caster.class) != null) {
                    final DocumentedCaster documentedCaster = DocumentedCaster.fromMethod((ExecutableElement) enclosedElement, environment);
                    if (documentedCaster != null) {
                        out.casters.add(documentedCaster);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return ZSName;
    }

    public String getZSName() {
        return ZSName;
    }

    public String getZSShortName() {
        return ZSName.substring(ZSName.lastIndexOf('.') + 1);
    }

    public String getDocPath() {
        return docPath;
    }

    @Override
    public void write(File docsDirectory) throws IOException {
        final File file = new File(docsDirectory, getDocPath() + ".md");
        if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()){
            throw new IOException("Could not create folder " + file.getAbsolutePath());
        }

        try(final PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            printWriter.printf("# %s%n", getZSShortName());
            printWriter.println();

            //TODO comment

            if(!methods.isEmpty()) {
                printWriter.println("## Methods");
                for (DocumentedMethodGroup value : methods.values()) {
                    value.write(printWriter);
                }
            }


        }

    }
}
