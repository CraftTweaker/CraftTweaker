package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import com.blamejared.crafttweaker_annotation_processors.processors.document.CrafttweakerDocumentationPage;
import com.blamejared.crafttweaker_annotation_processors.processors.document.DocumentedScriptingExample;
import com.blamejared.crafttweaker_annotation_processors.processors.document.IDontKnowHowToNameThisUtil;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DocumentedClass extends CrafttweakerDocumentationPage {

    private static final Comparator<DocumentedClass> byZSName = Comparator.comparing(c -> c.ZSName);

    private final Set<DocumentedClass> implementedInterfaces = new TreeSet<>(DocumentedClass.byZSName);
    private final Set<DocumentedConstructor> constructors = new TreeSet<>(DocumentedConstructor.compareByParameterCount);
    private final Map<String, List<DocumentedMethod>> methods = new TreeMap<>();
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
        final String docPath = IDontKnowHowToNameThisUtil.getDocPath(element, nameAnnotation);
        if (docPath == null) {
            return null;
        }

        final DocumentedClass superClass = convertClass((TypeElement) environment
                .getTypeUtils()
                .asElement(element.getSuperclass()), environment);
        final DocumentedClass out = new DocumentedClass(nameAnnotation.value(), docPath, superClass);

        for (final TypeMirror anInterface : element.getInterfaces()) {
            final TypeElement intElement = (TypeElement) environment.getTypeUtils().asElement(anInterface);
            final DocumentedClass documentedClass = convertClass(intElement, environment);
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
                        if(!out.methods.containsKey(documentedMethod.getName())){
                            out.methods.put(documentedMethod.getName(), new ArrayList<>());
                        }
                        out.methods.get(documentedMethod.getName()).add(documentedMethod);
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
                if(enclosedElement.getAnnotation(ZenCodeType.Getter.class) != null || enclosedElement.getAnnotation(ZenCodeType.Setter.class) != null){
                    final DocumentedProperty documentedProperty = DocumentedProperty.fromMethod(enclosedElement, environment);
                    if (documentedProperty != null) {
                        out.properties.merge(documentedProperty.getName(), documentedProperty, ((p1, p2) -> DocumentedProperty
                                .merge(p1, p2, environment)));
                    }
                }
            }
            //Constructor
            //Operator
            //Caster
        }
    }

    @Override
    public void write(File docsDirectory) throws IOException {

    }

    @Override
    public String toString() {
        return "DocumentedClass{" +
                "ZSName='" + ZSName + '\'' +
                ", docPath='" + docPath + '\'' +
                ", superClass=" + superClass +
                '}';
    }
}
