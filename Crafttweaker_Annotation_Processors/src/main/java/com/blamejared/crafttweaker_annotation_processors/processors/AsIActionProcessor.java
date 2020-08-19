package com.blamejared.crafttweaker_annotation_processors.processors;

import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.tools.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

@SupportedAnnotationTypes({"com.blamejared.crafttweaker_annotations.annotations.AsIAction", "com.blamejared.crafttweaker_annotations.annotations.ZenWrapper"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AsIActionProcessor extends AbstractProcessor {

    private final Map<String, Element> annotationsByWrappedElement = new HashMap<>();

    private String getNewMethodDescriptor(VariableElement variableElement) {

        final TypeMirror typeMirror = variableElement.asType();
        final String className = typeMirror.toString();
        if (typeMirror.getKind().isPrimitive())
            return className;

        if (annotationsByWrappedElement.containsKey(className)) {
            return annotationsByWrappedElement.get(className).asType().toString();
        }

        if (className.startsWith("java.lang."))
            return className;

        return null;
    }

    private String getConversionFormat(VariableElement variableElement) {
        final TypeMirror typeMirror = variableElement.asType();
        if (typeMirror.getKind().isPrimitive())
            return "%s";

        final String className = typeMirror.toString();
        if (annotationsByWrappedElement.containsKey(className)) {
            return annotationsByWrappedElement.get(className).getAnnotation(ZenWrapper.class).conversionMethodFormat();
        }

        if (toString().startsWith("java.lang.")) {
            return "%s";
        }

        this.processingEnv.getMessager()
                .printMessage(Diagnostic.Kind.WARNING, "Could not conversion for " + className, variableElement);
        return "%s";
    }

    private String getDisplayStringFormat(VariableElement variableElement) {
        final TypeMirror typeMirror = variableElement.asType();
        if (typeMirror.getKind().isPrimitive())
            return "%s";

        final String className = typeMirror.toString();
        if (annotationsByWrappedElement.containsKey(className)) {
            return annotationsByWrappedElement.get(className).getAnnotation(ZenWrapper.class).displayStringFormat();
        }

        if (toString().startsWith("java.lang.")) {
            return "%s";
        }

        this.processingEnv.getMessager()
                .printMessage(Diagnostic.Kind.WARNING, "No display String possible for " + className, variableElement);
        return "%s";
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(ZenWrapper.class)) {
            this.annotationsByWrappedElement.put(element.getAnnotation(ZenWrapper.class).wrappedClass(), element);
        }
    
        
        for(Class<?> wrapperClass : ReflectionReader.getClassesWithZenWrapper(getClass().getClassLoader())) {
            final String name = wrapperClass.getAnnotation(ZenWrapper.class).wrappedClass();
            final TypeElement typeElement = processingEnv.getElementUtils()
                    .getTypeElement(wrapperClass.getCanonicalName());
            annotationsByWrappedElement.put(name, typeElement);
        }
    
        if (annotations.isEmpty())
            return false;

        if (annotations.size() > 2) {
            this.processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "More than two annotation type found?");
        }

        final Map<String, List<ExecutableElement>> classes = new HashMap<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(AsIAction.class)) {
            //AsIAction is only applicable to methods so the cast is safe
            ExecutableElement method = (ExecutableElement) element;

            if (!method.getModifiers().contains(Modifier.STATIC)) {
                this.processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "@AsIActionAnnotation requires the method to be static", method);
            }

            final Element enclosingElement = method.getEnclosingElement();
            final ZenCodeType.Name annotation = enclosingElement.getAnnotation(ZenCodeType.Name.class);
            if (annotation == null) {
                this.processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "@AsIAction requires a @ZenCodeType.Name on the declaring class " + enclosingElement, method);
                continue;
            }

            classes.computeIfAbsent(annotation.value(), ignored -> new ArrayList<>()).add(method);
        }

        try {
            writeClasses(classes);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }

    private void writeClasses(Map<String, List<ExecutableElement>> classes) throws IOException {
        for (Map.Entry<String, List<ExecutableElement>> stringListEntry : classes.entrySet()) {
            final String name = stringListEntry.getKey();
            final List<ExecutableElement> methods = stringListEntry.getValue();
            final QualifiedNameable nameable = ((QualifiedNameable) methods.get(0).getEnclosingElement());
            final String qualifiedName = nameable.getQualifiedName() + "GeneratedCrt";

            final JavaFileObject sourceFile = this.processingEnv.getFiler()
                    .createSourceFile(qualifiedName);

            final int lastDot = qualifiedName.lastIndexOf('.');

            try (final PrintWriter writer = new PrintWriter(sourceFile.openWriter())) {
                writer.printf("package %s;%n", qualifiedName.substring(0, lastDot));
                writer.println();

                writer.println("@com.blamejared.crafttweaker.api.annotations.ZenRegister");
                writer.printf("@com.blamejared.crafttweaker_annotations.annotations.Document(\"%s\")%n", name.replace(".", File.separatorChar == '\\' ? "\\\\" : File.separator));
                writer.printf("@org.openzen.zencode.java.ZenCodeType.Name(\"%s\")%n", name);

                {
                    final String doc = this.processingEnv.getElementUtils().getDocComment(nameable);
                    if (doc != null) {
                        writer.println("/**");
                        writer.println(doc);
                        writer.println("*/");
                    }
                }

                writer.printf("public class %s {%n%n", qualifiedName.substring(lastDot + 1));

                for (ExecutableElement method : methods) {
                    writeMethod(method, writer);
                    writer.println();
                }

                writer.println("}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMethod(ExecutableElement method, PrintWriter writer) {
        final List<? extends VariableElement> parameters = method.getParameters();
        final AsIAction annotation = method.getAnnotation(AsIAction.class);


        StringBuilder sb = new StringBuilder();

        final String docComment = this.processingEnv.getElementUtils().getDocComment(method);
        if (docComment != null) {
            sb.append("/**\n");
            sb.append(docComment);
            sb.append("*/\n");
        }

        sb.append("@org.openzen.zencode.java.ZenCodeType.Method\n");
        sb.append("public static void ");
        sb.append(annotation.methodName().isEmpty() ? method.getSimpleName() : annotation.methodName());
        sb.append("(");

        {
            final StringJoiner sj = new StringJoiner(", ");
            for (VariableElement parameter : parameters) {
                final String newMethodDescriptor = getNewMethodDescriptor(parameter);

                if (newMethodDescriptor == null) {
                    this.processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, "Cannot auto-convert type " + parameter, parameter);
                    return;
                }

                sj.add(newMethodDescriptor + " " + parameter.getSimpleName());
            }
            sb.append(sj.toString());
        }
        sb.append(") {\n");

        sb.append("\tcom.blamejared.crafttweaker.api.CraftTweakerAPI.apply(new com.blamejared.crafttweaker.api.actions.");
        sb.append(annotation.repeatable() ? "IRuntimeAction" : "IAction");
        sb.append("() {\n");

        sb.append("\t\t@java.lang.Override\n");
        sb.append("\t\tpublic void apply() {\n");
        sb.append("\t\t\t");
        sb.append(((QualifiedNameable) method.getEnclosingElement()).getQualifiedName().toString());
        sb.append(".").append(method.getSimpleName()).append("(");

        {
            final StringJoiner sj = new StringJoiner(", ");
            for (VariableElement parameter : parameters) {
                sj.add(String.format(getConversionFormat(parameter), parameter.getSimpleName()));
            }
            sb.append(sj.toString());
        }
        sb.append(");\n");
        sb.append("\t\t}\n\n");


        sb.append("\t\t@java.lang.Override\n");
        sb.append("\t\tpublic String describe() {\n");
        sb.append("\t\t\treturn String.format(\"");
        sb.append(annotation.logFormat()).append("\", ");
        {
            final StringJoiner sj = new StringJoiner(", ");
            for (VariableElement parameter : parameters) {
                if (parameter.asType().getKind().isPrimitive()) {
                    sj.add(parameter.getSimpleName());
                } else {
                    sj.add(String.format("%s == null ? null : String.format(\"%s\", %s)", parameter, getDisplayStringFormat(parameter), parameter
                            .getSimpleName()));
                }
            }
            sb.append(sj.toString());
        }
        sb.append(");\n");
        sb.append("\t\t}\n");


        sb.append("\t});\n}");

        writer.println(sb.toString());
    }
}
