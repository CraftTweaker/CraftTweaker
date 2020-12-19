package com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class;

import com.blamejared.crafttweaker_annotation_processors.processors.document.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.documented_class.members.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.members.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.types.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util.*;
import com.blamejared.crafttweaker_annotation_processors.processors.document_again.conversion.mods.KnownModList;
import org.openzen.zencode.java.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.tools.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

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
    
    //TODO: Example files
    private final Set<DocumentedScriptingExample> examples = new HashSet<>();
    private final String ZSName;
    private final String docPath;
    private final DocumentedClass superClass;
    private final String declaringModId;
    private final boolean isFunctionalInterface;
    private String docComment;
    private String docParamThis;
    
    public DocumentedClass(String ZSName, String docPath, DocumentedClass superClass, String docParamThis, String declaringModId, boolean isFunctionalInterface) {
        this.ZSName = ZSName;
        this.docPath = docPath;
        this.superClass = superClass;
        this.docParamThis = docParamThis;
        this.declaringModId = declaringModId;
        this.isFunctionalInterface = isFunctionalInterface;
    }
    
    public static DocumentedClass convertClass(TypeMirror type, ProcessingEnvironment environment, boolean forComment) {
        return convertClass((TypeElement) environment.getTypeUtils().asElement(type), environment, forComment);
    }
    
    public static DocumentedClass convertClass(TypeElement element, ProcessingEnvironment environment, boolean forComment) {
        if(element == null || element.equals(environment.getElementUtils()
                .getTypeElement("java.lang.Object"))) {
            return null;
        }
        if(knownTypes.containsKey(element.toString())) {
            final CrafttweakerDocumentationPage documentationPage = knownTypes.get(element.toString());
            if(!(documentationPage instanceof DocumentedClass)) {
                environment.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Internal error: " + element + " is not a class", element);
                return null;
            }
            return (DocumentedClass) documentationPage;
        }
        
        final ZenCodeType.Name nameAnnotation = element.getAnnotation(ZenCodeType.Name.class);
        final String zsName = nameAnnotation != null ? nameAnnotation.value() : element.getQualifiedName()
                .toString();
        final String docPath = IDontKnowHowToNameThisUtil.getDocPath(element, environment, forComment);
        if(docPath == null) {
            return null;
        }
        
        final DocumentedClass superClass = convertClass(element.getSuperclass(), environment, forComment);
        //final String docComment = environment.getElementUtils().getDocComment(element);
        
        final String declaringModId = KnownModList.getInstance().getModIdForPackage(element);
        String docParamThis = null;
        
        {
            final String s = CommentUtils.joinDocAnnotation(element, "@docParam this", environment)
                    .trim();
            if(!s.isEmpty()) {
                docParamThis = s;
            }
        }
        final boolean isFunctionalInterface = element.getAnnotation(FunctionalInterface.class) != null;
        
        final DocumentedClass out = new DocumentedClass(zsName, docPath, superClass, docParamThis, declaringModId, isFunctionalInterface);
        knownTypes.put(element.toString(), out);
        final String docComment = CommentUtils.formatDocCommentForDisplay(element, environment);
        out.setDocComment(docComment);
        typesByZSName.put(zsName, element);
        
        if(!(element.getSuperclass() instanceof NoType)) {
            final DocumentedClass documentedClass = convertClass(element.getSuperclass(), environment, forComment);
            if(documentedClass != null) {
                out.implementedInterfaces.add(documentedClass);
                out.implementedInterfaces.addAll(documentedClass.implementedInterfaces);
            }
        }
        
        for(final TypeMirror anInterface : element.getInterfaces()) {
            final DocumentedClass documentedClass = convertClass(anInterface, environment, forComment);
            if(documentedClass != null) {
                out.implementedInterfaces.add(documentedClass);
                out.implementedInterfaces.addAll(documentedClass.implementedInterfaces);
            }
        }
        
        handleEnclosedElements(out, element, environment);
        
        return out;
    }
    
    private static void handleEnclosedElements(DocumentedClass out, TypeElement element, ProcessingEnvironment environment) {
        for(Element enclosedElement : element.getEnclosedElements()) {
            //Method
            {
                final ZenCodeType.Method method = enclosedElement.getAnnotation(ZenCodeType.Method.class);
                if(method != null) {
                    final DocumentedMethod documentedMethod = DocumentedMethod.convertMethod(out, (ExecutableElement) enclosedElement, environment, false);
                    if(documentedMethod != null) {
                        out.methods.computeIfAbsent(documentedMethod.getName(), name -> new DocumentedMethodGroup(name, out))
                                .add(documentedMethod);
                    }
                }
            }
            //Field
            {
                final ZenCodeType.Field method = enclosedElement.getAnnotation(ZenCodeType.Field.class);
                if(method != null) {
                    final DocumentedProperty documentedProperty = DocumentedProperty.fromField(out, enclosedElement, environment);
                    if(documentedProperty != null) {
                        out.properties.merge(documentedProperty.getName(), documentedProperty, ((p1, p2) -> DocumentedProperty
                                .merge(p1, p2, environment)));
                    }
                }
            }
            //Getter
            //Setter
            {
                if(enclosedElement.getAnnotation(ZenCodeType.Getter.class) != null || enclosedElement
                        .getAnnotation(ZenCodeType.Setter.class) != null) {
                    final DocumentedProperty documentedProperty = DocumentedProperty.fromMethod(out, enclosedElement, environment, false);
                    if(documentedProperty != null) {
                        out.properties.merge(documentedProperty.getName(), documentedProperty, ((p1, p2) -> DocumentedProperty
                                .merge(p1, p2, environment)));
                    }
                }
            }
            //Constructor
            {
                if(enclosedElement.getAnnotation(ZenCodeType.Constructor.class) != null) {
                    final DocumentedConstructor documentedConstructor = DocumentedConstructor.fromConstructor(out, ((ExecutableElement) enclosedElement), environment);
                    if(documentedConstructor != null) {
                        out.constructors.add(documentedConstructor);
                    }
                }
            }
            //Operator
            {
                if(enclosedElement.getAnnotation(ZenCodeType.Operator.class) != null) {
                    final DocumentedOperator documentedOperator = DocumentedOperator.fromMethod(out, (ExecutableElement) enclosedElement, environment, false);
                    if(documentedOperator != null) {
                        out.operators.add(documentedOperator);
                    }
                }
            }
            
            //Caster
            {
                if(enclosedElement.getAnnotation(ZenCodeType.Caster.class) != null) {
                    final DocumentedCaster documentedCaster = DocumentedCaster.fromMethod((ExecutableElement) enclosedElement, environment, false);
                    if(documentedCaster != null) {
                        out.casters.add(documentedCaster);
                    }
                }
            }
        }
    }
    
    private static void printSection(String sectionName, Collection<? extends Writable> writables, PrintWriter writer) {
        if(!writables.isEmpty()) {
            writer.printf("## %s%n", sectionName);
            for(Writable writable : writables) {
                writable.write(writer);
            }
            writer.println();
        }
    }
    
    public void setDocComment(String docComment) {
        this.docComment = docComment;
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
    
    @Override
    public String getDocPath() {
        return docPath;
    }
    
    public String getDocParamThis() {
        return docParamThis == null ? "my" + getZSShortName() : docParamThis;
    }
    
    @Override
    public String getDocumentTitle() {
        return getZSShortName();
    }
    
    @Override
    public void write(File docsDirectory, ProcessingEnvironment environment) throws IOException {
        final File file = new File(docsDirectory, getDocPath() + ".md");
        if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("Could not create folder " + file.getAbsolutePath());
        }
        
        try(final PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.printf("# %s%n", getDocumentTitle());
            writer.println();
            
            
            if(docComment != null) {
                writer.println(docComment);
                writer.println();
            }
            
            if(declaringModId != null) {
                writer.printf("This class was added by a mod with mod-id `%s`. So you need to have this mod installed if you want to use this feature.%n", declaringModId);
                writer.println();
            }
            
            writer.println("## Importing the class");
            //TODO: Create Arrays pages and re-add the '[Array](link)'
            writer.println("It might be required for you to import the package if you encounter any issues (like casting an Array), so better be safe than sorry and add the import.  ");
            writer.println("```zenscript");
            writer.println(this.getZSName());
            writer.println("```");
            writer.println();
            
            if(!implementedInterfaces.isEmpty()) {
                
                writer.println("## Implemented Interfaces");
                writer.printf("%s implements the following interfaces. That means any method available to them can also be used on this class.  %n", this
                        .getZSShortName());
                for(DocumentedClass implementedInterface : implementedInterfaces) {
                    writer.printf("- %s%n", new DocumentedClassType(implementedInterface).getClickableMarkdown());
                    
                    this.constructors.addAll(implementedInterface.constructors);
                    this.casters.addAll(implementedInterface.casters);
                    this.operators.addAll(implementedInterface.operators);
                    implementedInterface.properties.forEach((s, documentedProperty) -> this.properties
                            .merge(s, documentedProperty, (a, b) -> DocumentedProperty.merge(a, b, environment)));
                    for(Map.Entry<String, DocumentedMethodGroup> entry : implementedInterface.methods
                            .entrySet()) {
                        String s = entry.getKey();
                        DocumentedMethodGroup documentedMethodGroup = entry.getValue();
                        this.methods.computeIfAbsent(s, s1 -> new DocumentedMethodGroup(s1, this));
                        this.methods.merge(s, documentedMethodGroup, DocumentedMethodGroup::merge);
                    }
                    
                    if(this.docParamThis == null) {
                        //TODO: Do we want this
                        this.docParamThis = implementedInterface.docParamThis;
                    }
                }
                writer.println();
            }
            
            if(isFunctionalInterface) {
                writer.println("## Functional Interface");
                writer.println();
                writer.println("This class is a functional interface. This means that you can use the lambda notation to create an instance of it.");
                
                if(methods.size() == 1) {
                    final DocumentedMethodGroup group = methods.values().iterator().next();
                    if(group.getMethods().size() == 1) {
                        final DocumentedMethod method = group.getMethods().iterator().next();
                        writer.println("The lambda notation looks like: ");
                        writer.println("```zenscript");
                        final String collect = method.getParameterList()
                                .stream()
                                .map(DocumentedParameter::getName)
                                .collect(Collectors.joining(", ", "(", ") => "));
                        writer.print(collect);
                        final DocumentedType returnType = method.getReturnType();
                        writer.println(returnType == null ? "{}" : returnType.getDocParamThis());
                        writer.println("```");
                    }
                }
            }
            
            
            printSection("Constructors", this.constructors, writer);
            printSection("Methods", this.methods.values(), writer);
            DocumentedProperty.printProperties(this.properties.values(), writer);
            printSection("Operators", this.operators, writer);
            //printSection("Casters", this.casters, writer);
            DocumentedCaster.printCasters(this.casters, writer);
        }
    }
    
    
    public boolean extendsOrImplements(DocumentedClass containingClass) {
        return this.equals(containingClass) || this.superClass != null && this.superClass.equals(containingClass) || this.implementedInterfaces
                .contains(containingClass);
    }
}
